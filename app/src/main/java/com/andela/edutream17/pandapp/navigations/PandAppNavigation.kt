package com.andela.edutream17.pandapp.navigations

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andela.edutream17.pandapp.ui.screens.ChatBotScreen
import com.andela.edutream17.pandapp.ui.screens.HomeScreen
import com.andela.edutream17.pandapp.ui.screens.VirtualPeerDownloadScreen


@Composable
fun PandAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }

        composable("download") {
            VirtualPeerDownloadScreen(navController)
        }

        composable("chatbot/{modelName}") {
            val modelName = it.arguments?.getString("modelName")!!
            ChatBotScreen(navController, modelName)
        }
    }
}