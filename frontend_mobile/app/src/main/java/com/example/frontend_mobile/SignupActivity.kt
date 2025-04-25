package com.example.frontend_mobile

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.content.ContextCompat.startActivity
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Frontend_mobileTheme {
                Surface(color = Color(0xFF414162)) {
                    SignupScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    var birthDate by remember { mutableStateOf(TextFieldValue("")) }
    var passwordVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val formatter = remember { SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        // Header with back button and centered title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            IconButton(
                onClick = { (context as Activity).finish() },
                modifier = Modifier.size(32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backarrow),
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }

            Text(
                text = "Create Account",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.width(32.dp))
        }

        // Email field
        Text(
            text = "Email",
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color(0xFF3D709F),
                containerColor = Color(0xFF3D709F).copy(alpha = 0.2f)
            ),
            textStyle = TextStyle(color = Color.White),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password field
        Text(
            text = "Password",
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Gray,
                focusedBorderColor = Color(0xFF3D709F),
                containerColor = Color(0xFF3D709F).copy(alpha = 0.2f)
            ),
            textStyle = TextStyle(color = Color.White),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                        tint = Color.White
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date of birth field with calendar picker
        Text(
            text = "Date of birth",
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true }
        ) {
            OutlinedTextField(
                value = birthDate.text,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color.Gray,
                    focusedBorderColor = Color(0xFF3D709F),
                    containerColor = Color(0xFF3D709F).copy(alpha = 0.2f)
                ),
                textStyle = TextStyle(color = Color.White),
                placeholder = { Text("MM/DD/YYYY", color = Color.Gray) },
                trailingIcon = {
                    Icon(
                        painter = painterResource(android.R.drawable.ic_menu_my_calendar),
                        contentDescription = "Select date",
                        tint = Color.White
                    )
                }
            )
        }

        if (showDatePicker) {
            val datePicker = DatePickerDialog(
                context,
                { _, year, month, day ->
                    val selectedDate = Calendar.getInstance()
                    selectedDate.set(year, month, day)
                    birthDate = TextFieldValue(formatter.format(selectedDate.time))
                    showDatePicker = false
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePicker.datePicker.maxDate = System.currentTimeMillis()
            datePicker.show()
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Terms text
        Text(
            text = "By signing up you accept QuizWhiz's Terms of service and Privacy Policy",
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Create account button
        Button(
            onClick = {
                if (email.text.isNotBlank() && password.text.isNotBlank() && birthDate.text.isNotBlank()) {
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(context, intent, null)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(corner = CornerSize(10.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4A93D7)
            )
        ) {
            Text("Create account", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupScreenPreview() {
    Frontend_mobileTheme {
        Surface(color = Color(0xFF414162)) {
            SignupScreen()
        }
    }
}