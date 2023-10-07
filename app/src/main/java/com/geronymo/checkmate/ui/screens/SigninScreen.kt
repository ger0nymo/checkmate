package com.geronymo.checkmate.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geronymo.checkmate.R
import com.geronymo.checkmate.ui.components.CMAIconButton
import com.geronymo.checkmate.ui.components.CMAOutlinedButton
import com.geronymo.checkmate.ui.components.CMATextButton
import com.geronymo.checkmate.ui.components.CMATextField
import com.geronymo.checkmate.ui.theme.CheckMateTheme

@ExperimentalMaterial3Api
@Composable
fun LoginScreen(navController: NavController) {
    var textInputEmail: String by remember { mutableStateOf("") }
    var textInputPassword: String by remember { mutableStateOf("") }

    CheckMateTheme() {
        Scaffold { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painterResource(id = R.drawable.checklist),
                        contentDescription = "Todos",
                        modifier = Modifier
                            .fillMaxWidth(0.6f)
                            .padding(top = 70.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CMATextField(
                        value = textInputEmail,
                        onValueChange = { str -> textInputEmail = str },
                        label = "E-mail address",
                        type = KeyboardType.Email,
                    )
                    CMATextField(
                        value = textInputPassword,
                        onValueChange = { str -> textInputPassword = str },
                        label = "Password",
                        type = KeyboardType.Password,
                        modifier = Modifier.padding(top = 18.dp)
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // If I add padding for the "Sign in" button it will be cut off TODO: fix this
                    CMAOutlinedButton(
                        text = "Sign in",
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .defaultMinSize(minHeight = 44.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CMAIconButton(
                        text = "Sign in via Google",
                        onClick = { /*TODO*/ },
                        drawableIconId = R.drawable.google,
                    )
                    CMATextButton(
                        text = "Don't have an account? Tap here",
                        onClick = { /*TODO*/ },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}