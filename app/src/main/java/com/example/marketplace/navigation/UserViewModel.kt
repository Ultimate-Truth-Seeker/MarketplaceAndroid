package com.example.marketplace.navigation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.marketplace.ui.model.User
import com.example.marketplace.ui.repository.UserRepository

class UserViewModel : ViewModel() {
    private val _sampleUser = MutableLiveData<User?>()
    val sampleUser: LiveData<User?> get() = _sampleUser

    fun signIn(email: String, password: String) {
        // Your sign-in logic
        UserRepository().signInUser(email, password) { user ->
            _sampleUser.postValue(user)
            Log.d("", user.toString())
        }
    }

    fun signOut() {
        _sampleUser.postValue(null)
    }

    // Other user-related methods...
}