package com.example.mentalhealthaibot.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mentalhealthaibot.roomdb.UserEntity
import com.example.mentalhealthaibot.roomdb.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun insertUser(user: UserEntity) {
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    fun getUserByEmail(email: String, callback: (UserEntity?) -> Unit) {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            callback(user)
        }
    }
}
