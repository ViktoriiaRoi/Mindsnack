package com.comppot.mindsnack.profile.presentation.login

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.comppot.mindsnack.core.presentation.Screen
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = hiltViewModel()) {
    FirebaseLoginUI(viewModel.getSignInIntent()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.onLoginSuccess()
            navController.navigate(Screen.Tab.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }
}

@Composable
private fun FirebaseLoginUI(
    signInIntent: Intent,
    onLoginResult: (FirebaseAuthUIAuthenticationResult) -> Unit
) {
    val launcher = rememberLauncherForActivityResult(
        contract = FirebaseAuthUIActivityResultContract(),
        onResult = onLoginResult
    )

    LaunchedEffect(true) {
        launcher.launch(signInIntent)
    }
}
