package com.example.recipeandroidapp.di

import android.app.Application
import androidx.room.Room
import com.example.recipeandroidapp.data.local.RecipeDao
import com.example.recipeandroidapp.data.local.RecipeDatabase
import com.example.recipeandroidapp.data.local.RecipeTypeConverter
import com.example.recipeandroidapp.data.manager.LocalUserManagerImpl
import com.example.recipeandroidapp.data.remote.RecipeApi
import com.example.recipeandroidapp.data.repository.RecipeRepositoryImpl
import com.example.recipeandroidapp.domain.manager.LocalUserManager
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import com.example.recipeandroidapp.domain.usecases.AppEntryUseCases
import com.example.recipeandroidapp.domain.usecases.DeleteRecipe
import com.example.recipeandroidapp.domain.usecases.GetAllCategories
import com.example.recipeandroidapp.domain.usecases.GetAllTags
import com.example.recipeandroidapp.domain.usecases.GetDailyRecipes
import com.example.recipeandroidapp.domain.usecases.GetIngredients
import com.example.recipeandroidapp.domain.usecases.GetRecipeById
import com.example.recipeandroidapp.domain.usecases.GetRecipes
import com.example.recipeandroidapp.domain.usecases.GetSteps
import com.example.recipeandroidapp.domain.usecases.ReadAppEntry
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import com.example.recipeandroidapp.domain.usecases.SaveAppEntry
import com.example.recipeandroidapp.domain.usecases.SearchRecipes
import com.example.recipeandroidapp.domain.usecases.SelectRecipes
import com.example.recipeandroidapp.domain.usecases.UpsertRecipe
import com.example.recipeandroidapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeApi(client: OkHttpClient): RecipeApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
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
    fun provideRecipeUseCases(
        recipeRepository: RecipeRepository,
        recipeDao: RecipeDao
    ): RecipeUseCases {
        return RecipeUseCases(
            getRecipes = GetRecipes(recipeRepository),
            getDailyRecipesRecipes = GetDailyRecipes(recipeRepository),
            searchRecipes = SearchRecipes(recipeRepository),
            getAllCategories = GetAllCategories(recipeRepository),
            getAllTags = GetAllTags(recipeRepository),
            getIngredients = GetIngredients(recipeRepository),
            getSteps = GetSteps(recipeRepository),


            upsertRecipe = UpsertRecipe(recipeDao),
            deleteRecipe = DeleteRecipe(recipeDao),
            selectRecipes = SelectRecipes(recipeDao),
            getRecipeById = GetRecipeById(recipeDao),

        )
    }

    @Provides
    @Singleton
    fun provideRecipeDatabase(application: Application): RecipeDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = RecipeDatabase::class.java,
            name = "recipeDB"
        ).addTypeConverter(RecipeTypeConverter())
            .fallbackToDestructiveMigration(false)
            .build()
    }

    @Provides
    @Singleton
    fun provideRecipeDao(recipeDatabase: RecipeDatabase): RecipeDao = recipeDatabase.recipeDao
}