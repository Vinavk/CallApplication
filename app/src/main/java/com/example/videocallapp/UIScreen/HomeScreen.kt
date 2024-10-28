package com.example.videocallapp.UIScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.videocallapp.MainActivity
import com.example.videocallapp.Screens
import com.example.videocallapp.appID
import com.example.videocallapp.appSign
import com.google.firebase.auth.FirebaseAuth
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current as MainActivity
    LaunchedEffect(key1 = Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            context.initZegoInviteService(appID, appSign, it.email!!, it.email!!)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Video Call",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        var targetUser = remember { mutableStateOf("") }
        OutlinedTextField(
            value = targetUser.value,
            onValueChange = { targetUser.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Enter user ID or email") }
        )


        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CallButton(isVideoCall = false) { button ->
                if (targetUser.value.isNotEmpty()) button.setInvitees(
                    mutableListOf(
                        ZegoUIKitUser(targetUser.value, targetUser.value)
                    )
                )
            }


            CallButton(isVideoCall = true) { button ->
                if (targetUser.value.isNotEmpty()) button.setInvitees(
                    mutableListOf(
                        ZegoUIKitUser(targetUser.value, targetUser.value)
                    )
                )
            }


            Button(onClick = {
                navController.navigate(Screens.LoginScreen.value)

            }) {
                Text(text = "Logout")

            }
        }
    }
}

@Composable
fun CallButton(isVideoCall: Boolean, onClick: (ZegoSendCallInvitationButton) -> Unit) {
    AndroidView(
        factory = { context ->
            val button = ZegoSendCallInvitationButton(context)
            button.setIsVideoCall(isVideoCall)
            button.resourceID = "zego_data"
            button
        }, modifier = Modifier
            .size(50.dp)
            .padding(vertical = 8.dp)
    ) { zegoCallButton ->
        zegoCallButton.setOnClickListener { _ -> onClick(zegoCallButton) }
    }
}


@Preview
@Composable
fun HomeScreenPrview() {
    //  HomeScreen(navController = rememberNavController())
}