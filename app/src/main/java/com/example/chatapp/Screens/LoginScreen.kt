package com.example.chatapp.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapp.CheckSignedIn
import com.example.chatapp.DestinationScreen
import com.example.chatapp.LCViewModel
import com.example.chatapp.R
import com.example.chatapp.commonProgressBar
import com.example.chatapp.navigateTo

@Composable

fun LoginScreen(vm : LCViewModel , navController: NavController) {
    CheckSignedIn(vm = vm, navController = navController)
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(
                    rememberScrollState()
                ), horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val passwordState = remember {
                mutableStateOf(TextFieldValue())
            }

            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val focus= LocalFocusManager.current
            Image(
                painter = painterResource(id = R.drawable.chat),
                contentDescription = null,
                modifier = Modifier
                    .width(200.dp)
                    .padding(top = 16.dp)
                    .padding(8.dp)
            )
            Text(
                text = "Sign Ip",
                fontFamily = FontFamily.SansSerif,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp)
            )


            OutlinedTextField(value = emailState.value, onValueChange = {
                emailState.value = it
            },
                label = { Text(text = "EMAIL")},
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(value = passwordState.value, onValueChange = {
                passwordState.value = it
            },
                label = { Text(text = "PASSWORD ")},
                modifier = Modifier.padding(8.dp)
            )
            Button(onClick = {
              vm.LoginIn(emailState.value.text,passwordState.value.text)
            },
                modifier = Modifier.padding(8.dp)
            ) {
                Text(text = "Sign In")
            }
            Text(text = "New User ? Go to Sign Up - >",
                color = Color.Blue,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreen.SignUp.route)
                    }
            )

        }
    }
    if(vm.inProcess.value){
        commonProgressBar()
    }
}