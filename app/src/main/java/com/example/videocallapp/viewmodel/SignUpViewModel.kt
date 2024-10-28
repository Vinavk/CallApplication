package com.example.videocallapp.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {

    var signupstate = MutableStateFlow<SignupState>(SignupState.Nrml)

    var signupstateflow = signupstate.asStateFlow()

    fun signup(name: String, password: String) {
        signupstate.value = SignupState.Loading
        var dbauth = FirebaseAuth.getInstance()

        dbauth.createUserWithEmailAndPassword(name, password).addOnCompleteListener {

            if (it.isSuccessful) {
                if (dbauth.currentUser != null) {
                    signupstate.value = SignupState.Success

                } else {
                    signupstate.value = SignupState.Error

                }

            } else {
                signupstate.value = SignupState.Error
            }

        }

    }
}


sealed class SignupState() {
    object Loading : SignupState()
    object Nrml : SignupState()
    object Success : SignupState()
    object Error : SignupState()
}