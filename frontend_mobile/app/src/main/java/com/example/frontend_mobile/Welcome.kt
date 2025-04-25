package com.example.frontend_mobile

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.BorderStroke

@Composable
fun WelcomeScreen() {
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF414162) // Dark blue background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App logo/name
            Text(
                text = "Welcome to QuizWhiz!",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White // White text
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Welcome message
            Text(
                text = "Start Learning now.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color.White // White text
            )

            Text(
                text = "Sign up for free.",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                color = Color.White // White text
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Terms text
            Text(
                text = "By signing up you accept QuizWhiz's\nTerms of service and Privacy Policy",
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f) // Semi-transparent white
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Google sign in button (now light blue)
            Button(
                onClick = { /* Handle Google sign in */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4A93D7), // Light blue color
                    contentColor = Color.White // White text
                )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo), // Reference your PNG
                    contentDescription = "Google logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Continue with Google")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Email sign up button (background color with white outline)
            OutlinedButton(
                onClick = {
                    val intent = Intent(context, SignupActivity::class.java)
                    startActivity(context, intent, null)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(corner = CornerSize(10.dp)),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White // White text
                ),
                border = BorderStroke(1.dp, Color.White) // Correct way to set border color
            ) {
                Image(
                    painter = painterResource(id = R.drawable.email), // Reference your PNG
                    contentDescription = "Email",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.size(8.dp))
                Text(text = "Sign up with email")
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Login option
            TextButton(
                onClick = {
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(context, intent, null)
                }
            ) {
                Text(
                    text = "Have an account? Log in",
                    color = Color(0xFF4A93D7) // Blue text for login link
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    Frontend_mobileTheme {
        WelcomeScreen()
    }
}