package com.example.videocallapp.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private var _loginState = MutableStateFlow<LoginState>(LoginState.Normal)


    val loginStateFlow: StateFlow<LoginState> = _loginState.asStateFlow()

    fun login(name: String, password: String) {
        _loginState.value = LoginState.Loading

        val dbauth = FirebaseAuth.getInstance()
        dbauth.signInWithEmailAndPassword(name, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = dbauth.currentUser
                if (user != null) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error
                }
            } else {
                _loginState.value = LoginState.Error
            }
        }
    }
}


sealed class LoginState() {
    object Normal : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    object Error : LoginState()
}