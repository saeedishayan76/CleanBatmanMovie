package com.shayan.composeBatmanMovie.di

import android.content.Context
import androidx.room.Room
import com.shayan.composeBatmanMovie.data.local.AppDataBase
import com.shayan.composeBatmanMovie.data.remote.ApiService
import com.shayan.composeBatmanMovie.data.repoImpl.MovieRepositoryImpl
import com.shayan.composeBatmanMovie.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {


    @Provides
    @Singleton
    fun provideApiService(): ApiService =
        Retrofit.Builder()
            .baseUrl("http://www.omdbapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)


    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context): AppDataBase =
        Room.databaseBuilder(context, AppDataBase::class.java, "todo.db")
            .fallbackToDestructiveMigration().build()


    @Provides
    fun provideMovieRepository(apiService: ApiService): MovieRepository {
        return MovieRepositoryImpl(apiService = apiService)
    }
}