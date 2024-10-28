package com.example.videocallapp.UIScreen.signup

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.videocallapp.Screens
import com.example.videocallapp.viewmodel.SignUpViewModel
import com.example.videocallapp.viewmodel.SignupState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreens(navController: NavHostController) {


    var context = LocalContext.current

    val viewModel: SignUpViewModel = hiltViewModel()


    var username = remember {
        mutableStateOf("")
    }
    var password = remember {
        mutableStateOf("")
    }
    var confirmpass = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { "enter name" })

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { "enter password" })

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = confirmpass.value,
                onValueChange = { confirmpass.value = it },
                label = { "confirm  password" })

            Spacer(modifier = Modifier.height(10.dp))


            Button(onClick = {
                if (username.value.isNotEmpty() && password.value.equals(confirmpass.value)) {
                    viewModel.signup(username.value, password.value)
                } else {
                    if (username.value.isEmpty()) {
                        Toast.makeText(context, "User Name Must Not Be NUll", Toast.LENGTH_SHORT)
                            .show()

                    } else {
                        Toast.makeText(
                            context,
                            "Both Passwords are Must be Same",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }) {
                Text(text = "Signup")

            }
        }
    }

    var signup = viewModel.signupstateflow.collectAsState()

    LaunchedEffect(signup.value) {
        when (signup.value) {
            is SignupState.Loading -> {
                // nothing
            }

            is SignupState.Success -> {
                navController.navigate(Screens.LoginScreen.value)
            }

            is SignupState.Error -> {
                Toast.makeText(context, "Failed to Signup", Toast.LENGTH_SHORT).show()
            }

            is SignupState.Nrml -> {
                    //nothing
            }

        }
    }


}

@Preview
@Composable
fun SignupScreenPreview() {
    SignupScreens(rememberNavController())
}