package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserInterface: LocalUserManager
) {

    suspend operator fun invoke(): Flow<Boolean> {
        return localUserInterface.readAppEntry()
    }


}