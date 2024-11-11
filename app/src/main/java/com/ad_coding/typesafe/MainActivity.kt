package com.ad_coding.typesafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.ad_coding.typesafe.ui.theme.TypeSafeTheme
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TypeSafeTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = Home) {

                    composable<Home> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Button(
                                onClick = {
                                    navController.navigate(Profile(name = "Daniel"))
                                }
                            ) {
                                Text("Go to profile")
                            }
                        }
                    }

                    composable<Profile> {
                        val viewModel = viewModel<ProfileViewModel>()

                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "Hello user: ${viewModel.name.value}")
                        }
                    }
                }
            }
        }
    }
}

class ProfileViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    var name = mutableStateOf("")

    init {
        val args = savedStateHandle.toRoute<Profile>()

        name.value = args.name
    }
}

@Serializable
data object Home

@Serializable
data class Profile(val name: String)