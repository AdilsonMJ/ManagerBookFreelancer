package com.example.managerbookfreelancer.loginAndRegister

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.managerbookfreelancer.R


@Composable
fun LoginPage(context: Context) {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    var passwordVisibility by rememberSaveable { mutableStateOf(false) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Blue, Color.Yellow),
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Text(text = "Email", modifier = Modifier.padding(start = 25.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Password") },
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                    Icon(
                        imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisibility) "Hider password" else "show password"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            singleLine = true
        )

        Text(text = "Password", modifier = Modifier.padding(start = 25.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))


        Button(
            onClick = {
                Toast.makeText(context, "Clickou", Toast.LENGTH_SHORT).show()
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.DarkGray),
        ) {
            Text(text = "Login")
        }

    }


}