package com.example.chatapp.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chatapp.LCViewModel

@Composable

fun ChatListScreen(navController: NavController,vm :LCViewModel) {
    Text(text = "Chat List Screen")
    BottomNavigationMenu(selectedItem = BottomNavigationItem.CHATLIST, navcontroller = navController)
}