package com.example.frontend_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme

class OpenFlashcardsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Frontend_mobileTheme {
                Surface(color = Color(0xFF414162)) {
                    OpenFlashcardsScreen(
                        onBackPressed = { finish() },
                        flashcardTitle = "Introduction to Computing",
                        authorName = "Raven Pavo",
                        termCount = 20,
                        onDeleteFlashcard = { /* Handle deletion in HomeActivity */ }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OpenFlashcardsScreen(
    onBackPressed: () -> Unit,
    flashcardTitle: String,
    authorName: String,
    termCount: Int,
    onDeleteFlashcard: () -> Unit
) {
    // State for flip animation
    var isFlipped by remember { mutableStateOf(false) }
    // State for current card index
    var currentIndex by remember { mutableStateOf(0) }
    // State for dropdown menu visibility
    var showMenu by remember { mutableStateOf(false) }
    // State for delete confirmation dialog
    var showDeleteDialog by remember { mutableStateOf(false) }
    // Sample flashcards data
    val flashcards = remember { mutableStateListOf(
        Pair("Computer", "An electronic device for storing and processing data"),
        Pair("Algorithm", "A set of rules to solve a problem"),
        Pair("Binary", "A number system that uses 0 and 1")
    )}
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with back button and three dots
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // Three dots menu button
                Box {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.threedots),
                            contentDescription = "Menu",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Dropdown menu
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(Color(0xFF414162))
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.copy),
                                        contentDescription = "Copy",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Make a Copy",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            onClick = {
                                showMenu = false
                                // Handle copy action
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.edit),
                                        contentDescription = "Edit",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Edit",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            onClick = {
                                showMenu = false

                                context.startActivity(
                                    Intent(context, EditCardActivity::class.java).apply {
                                        putExtra("title", flashcardTitle)
                                        putExtra("description", "Sample description") // Replace with actual description
                                        putStringArrayListExtra("terms", ArrayList(flashcards.map { it.first }))
                                        putStringArrayListExtra("definitions", ArrayList(flashcards.map { it.second }))
                                    }
                                )
                            }
                        )

                        DropdownMenuItem(
                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.delete),
                                        contentDescription = "Delete",
                                        tint = Color.Red,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Delete",
                                        color = Color.Red,
                                        fontSize = 16.sp
                                    )
                                }
                            },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            }
                        )
                    }
                }
            }

            // Flashcard content
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF3D709F))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount > 0) {
                                if (currentIndex > 0) currentIndex--
                            } else {
                                if (currentIndex < flashcards.size - 1) currentIndex++
                            }
                        }
                    }
                    .clickable { isFlipped = !isFlipped },
                contentAlignment = Alignment.Center
            ) {
                if (!isFlipped) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {

                        Text(
                            text = flashcards[currentIndex].first,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 36.sp
                        )
                    }
                } else {
                    Text(
                        text = flashcards[currentIndex].second,
                        color = Color.White,
                        fontSize = 24.sp,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(32.dp))
                }

                Icon(
                    painter = painterResource(id = R.drawable.fullscreen),
                    contentDescription = "Fullscreen",
                    tint = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .size(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Page indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(flashcards.size) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(
                                color = if (currentIndex == index) Color.White
                                else Color.White.copy(alpha = 0.5f),
                                shape = CircleShape)
                    )


                    if (index < flashcards.size - 1) {
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))

            // Flashcard info and buttons
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                flashcardTitle.split("\n").forEach { line ->
                    Text(
                        text = line,
                        color = Color.White,
                        fontSize = 32.sp, // Reduced from 32.sp for better fit
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp // Added line height
                    )
                }
                Text(
                    text = "$authorName | $termCount terms",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 8.dp, bottom = 32.dp) // Adjusted spacing
                )

                Button(
                    onClick = { /* Review action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                Text("Review", fontSize = 16.sp)
            }

                Button(
                    onClick = { /* Quiz action */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3D709F)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                Text("Take a Quiz", fontSize = 16.sp)
            }
            }
        }

        // Delete confirmation dialog
        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = {
                    Text(
                        text = "Introduction to Computing",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = "Are you sure you want to delete this flashcard permanently?",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp
                    )
                },
                containerColor = Color(0xFF414162),
            confirmButton = {
                Button(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteFlashcard()
                        onBackPressed()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))
                ) {
                    Text("DELETE", color = Color.White)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("CANCEL", color = Color.White)
                }
            })

        }

        // Dim background when menu or dialog is open
        if (showMenu || showDeleteDialog) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable {
                        showMenu = false
                        showDeleteDialog = false
                    }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF414162)
@Composable
fun OpenFlashcardsPreview() {
    Frontend_mobileTheme {
        OpenFlashcardsScreen(
            onBackPressed = {},
            flashcardTitle = "Introduction to Computing",
            authorName = "Raven Pavo",
            termCount = 20,
            onDeleteFlashcard = {}
        )
    }
}