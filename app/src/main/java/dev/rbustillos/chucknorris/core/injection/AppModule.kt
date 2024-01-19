package dev.rbustillos.chucknorris.core.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.rbustillos.chucknorris.core.network.JokesAPIService
import dev.rbustillos.chucknorris.core.network.ServiceProvider
import dev.rbustillos.chucknorris.jokes.data.remote.RemoteJokesDataSource
import dev.rbustillos.chucknorris.jokes.data.repository.JokesRepositoryImpl
import dev.rbustillos.chucknorris.jokes.domain.repository.JokesRepository
import dev.rbustillos.chucknorris.jokes.domain.useCase.DoGetCategoriesUseCase

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    // api
    @Provides
    fun providesJokesAPIService(): JokesAPIService =
        ServiceProvider.createService(JokesAPIService::class.java)

    // remoteDataSource
    @Provides
    fun providesRemoteJokesDataSource(jokesAPIService: JokesAPIService): RemoteJokesDataSource =
        RemoteJokesDataSource(jokesAPIService)

    // jokes repository
    @Provides
    fun providesJokesRepository(remoteJokesDataSource: RemoteJokesDataSource): JokesRepository =
        JokesRepositoryImpl(remoteJokesDataSource)

    // doGetCategoriesUseCase
    @Provides
    fun providesDoGetCategoriesUseCase(repository: JokesRepository): DoGetCategoriesUseCase =
        DoGetCategoriesUseCase(repository)
}