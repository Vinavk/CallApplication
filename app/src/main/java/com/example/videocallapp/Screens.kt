package com.example.videocallapp

sealed class Screens(var value : String){
    data object HomeScreen : Screens("HomeScreen")
    data object LoginScreen : Screens("LoginScreen")
    data object SignUpScreen : Screens("SignUpScreen")

}
