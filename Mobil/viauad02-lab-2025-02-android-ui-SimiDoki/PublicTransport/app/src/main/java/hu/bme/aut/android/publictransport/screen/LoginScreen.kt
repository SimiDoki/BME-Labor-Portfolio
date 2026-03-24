package hu.bme.aut.android.publictransport.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Warning

import hu.bme.aut.android.publictransport.R


@Composable
fun LoginScreen(
    onSuccess: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //Logo
        Image(
            painter = painterResource(id = R.mipmap.ic_transport_round),
            contentDescription = "Logo",
            modifier = Modifier.size(160.dp)
        )

    //Header Text
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Please enter your credentials!"
        )


        //Email Field
        var email by remember { mutableStateOf("") }
        var emailError by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Email") },
            value = email,
            onValueChange =
                {
                    email = it
                    emailError = isEmailValid(email)
                },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError,
            trailingIcon = {
                if (emailError) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = Color.Red)
                }
            },
            supportingText = {
                if (emailError) {
                    Text("Please enter your e-mail address!", color = Color.Red)
                }
            }

        )


        //Password Field
        var password by remember { mutableStateOf("") }
        var passwordError by remember { mutableStateOf(false) }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            label = { Text("Password") },
            value = password,
            onValueChange =
                {
                    password = it
                    passwordError = isPasswordValid(it)
                },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            isError = passwordError,
            trailingIcon = {
                if (passwordError) {
                    Icon(Icons.Filled.Warning, contentDescription = "Error", tint = Color.Red)
                }
            },
            supportingText = {
                if (passwordError) {
                    Text("Please enter your password!", color = Color.Red)
                }
            }
        )


        //Login Button
        Button(
            onClick = {
                if (isEmailValid(email)) {
                    emailError = true
                } else if (isPasswordValid(password)) {
                    passwordError = true
                } else {
                    onSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text("Login")
        }

    }
}


private fun isEmailValid(email: String) = email.isEmpty()

private fun isPasswordValid(password: String) = password.isEmpty()

@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(onSuccess = {})
}