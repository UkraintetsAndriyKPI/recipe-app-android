package com.example.recipeandroidapp.di

import android.app.Application
import com.example.recipeandroidapp.data.manager.LocalUserManagerImpl
import com.example.recipeandroidapp.data.remote.RecipeApi
import com.example.recipeandroidapp.data.repository.RecipeRepositoryImpl
import com.example.recipeandroidapp.domain.manager.LocalUserManager
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import com.example.recipeandroidapp.domain.usecases.AppEntryUseCases
import com.example.recipeandroidapp.domain.usecases.GetRecipes
import com.example.recipeandroidapp.domain.usecases.ReadAppEntry
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import com.example.recipeandroidapp.domain.usecases.SaveAppEntry
import com.example.recipeandroidapp.domain.usecases.SearchRecipes
import com.example.recipeandroidapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager),
    )

    @Provides
    @Singleton
    fun provideRecipeApi(): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRecipeRepository(recipeApi: RecipeApi):
            RecipeRepository = RecipeRepositoryImpl(recipeApi = recipeApi)

    @Provides
    @Singleton
    fun provideRecipeUseCases(recipeRepository: RecipeRepository): RecipeUseCases {
        return RecipeUseCases(
            getRecipes = GetRecipes(recipeRepository),
            searchRecipes = SearchRecipes(recipeRepository)
        )
    }
}