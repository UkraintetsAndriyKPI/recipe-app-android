package com.example.recipeandroidapp.domain.usecases

import com.example.recipeandroidapp.domain.manager.LocalUserManager

class SaveAppEntry(
    private val localUserInterface: LocalUserManager
) {

    suspend operator fun invoke() {
        localUserInterface.saveAppEntry()
    }


}
