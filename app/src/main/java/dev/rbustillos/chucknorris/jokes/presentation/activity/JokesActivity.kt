package dev.rbustillos.chucknorris.jokes.presentation.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import dev.rbustillos.chucknorris.R
import dev.rbustillos.chucknorris.core.common.ResultState
import dev.rbustillos.chucknorris.jokes.domain.model.Category
import dev.rbustillos.chucknorris.jokes.presentation.adapter.JokeCategoriesAdapter
import dev.rbustillos.chucknorris.jokes.presentation.viewModel.JokesViewModel
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@AndroidEntryPoint
class JokesActivity : AppCompatActivity() {

    // view model
    private val jokesVM: JokesViewModel by viewModels()

    // components
    private lateinit var categoriesRecyclerView: RecyclerView
    private val jokeCategories = arrayListOf<Category>()
    private lateinit var categoriesAdapter: JokeCategoriesAdapter
    // state
    private lateinit var loadingView: LinearLayout
    private lateinit var fabReloadButton: FloatingActionButton
    private var isLoading: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jokes)

        initComponents()
        startObserver()
        jokesVM.doGetCategories()
    }

    private fun initComponents() {
        // for view
        categoriesRecyclerView = findViewById(R.id.jokes_recycler)
        categoriesRecyclerView.layoutManager = LinearLayoutManager(this@JokesActivity)
        categoriesAdapter = JokeCategoriesAdapter(jokeCategories)
        categoriesRecyclerView.adapter = categoriesAdapter

        loadingView = findViewById(R.id.loading_view)
        fabReloadButton = findViewById<FloatingActionButton?>(R.id.fab_reload).also {
            it.setOnClickListener { jokesVM.doGetCategories() }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun startObserver() {
        // to observe the list
        jokesVM.categoriesResult.observe(this@JokesActivity) { categoriesResult ->

            categoriesResult ?: return@observe

            isLoading = false
            when (categoriesResult) {
                is ResultState.Error -> {
                    showMessage(getMessageByException(categoriesResult.exception))
                }

                is ResultState.Success -> {
                    jokeCategories.clear()
                    jokeCategories.addAll(categoriesResult.data)
                    categoriesAdapter.notifyDataSetChanged()
                }

                else -> {
                    isLoading = true
                }
            }
            checkLoadingState()
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this@JokesActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun getMessageByException(e: Exception): String = when (e) {
        is UnknownHostException -> "No connection"
        is SocketTimeoutException -> "Slow connection"
        else -> "Unknown error"
    }

    private fun checkLoadingState() {
        fabReloadButton.isEnabled = !isLoading
        if (isLoading) {
            loadingView.visibility = View.VISIBLE
        } else {
            loadingView.visibility = View.GONE
        }
    }
}