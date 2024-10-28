package com.example.videocallapp.UIScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.videocallapp.Screens
import com.example.videocallapp.viewmodel.LoginState
import com.example.videocallapp.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController) {

    var context = LocalContext.current
    val viewModel: LoginViewModel = hiltViewModel()

    var username = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var loading = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Enter name") }
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Enter password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(10.dp))

            if (loading.value) {

            } else {
                Button(onClick = {
                    if(username.value.isNotEmpty() && password.value.isNotEmpty()){
                        viewModel.login(username.value, password.value)
                    }
                    else{
                        Toast.makeText(context,"Name and Password is NOt Empty",Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text(text = "Login")
                }
                TextButton(onClick = {
                    navController.navigate(Screens.SignUpScreen.value)
                }) {
                    Text(text = "Don't have an account? Signup", color = Color.DarkGray)
                }
            }
        }
    }


    val state = viewModel.loginStateFlow.collectAsState()

    LaunchedEffect(state.value) {
        when (state.value) {
            is LoginState.Loading -> {
                loading.value = true
            }
            is LoginState.Success -> {
                loading.value = false
                username.value = ""
                password.value = ""
                navController.navigate(Screens.HomeScreen.value)
            }
            is LoginState.Error -> {
                loading.value = false

            }
            is LoginState.Normal -> {
                loading.value = false
            }
        }
    }
}
@Preview
@Composable
fun screen(){
    LoginScreen(navController = rememberNavController())
}
