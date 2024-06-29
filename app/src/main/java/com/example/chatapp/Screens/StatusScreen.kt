package com.example.chatapp.Screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.chatapp.LCViewModel

@Composable

fun StatusScreen(navController:NavController,vm:LCViewModel) {
    BottomNavigationMenu(selectedItem = BottomNavigationItem.STATUSLIST, navcontroller =navController)
}