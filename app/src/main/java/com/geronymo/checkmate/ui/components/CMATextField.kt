package com.geronymo.checkmate.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geronymo.checkmate.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMATextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    type: KeyboardType,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String = ""
) {
    var masked: Boolean by remember { mutableStateOf(true) }

    OutlinedTextField(
        isError = isError,
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Transparent,
        ),
        keyboardOptions = KeyboardOptions(keyboardType = type),
        trailingIcon = {
            if (type == KeyboardType.Password) {
                IconButton(onClick = { masked = !masked }) {
                    if (masked) {
                        Icon(
                            painterResource(id = R.drawable.icon_invisible),
                            contentDescription = "Clear"
                        )
                    } else {
                        Icon(
                            painterResource(id = R.drawable.icon_visible),
                            contentDescription = "Clear"
                        )
                    }
                }
            } else {
                if (value.isNotEmpty()) {
                    IconButton(onClick = { onValueChange("") }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear")
                    }
                }
            }
        },
        visualTransformation = if (masked && type == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .then(modifier)

    )

    if (isError) {
        Text(
            lineHeight = 12.sp,
            text = errorMessage,
            fontSize = 11.sp,
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth(0.80f)

        )
    }
}