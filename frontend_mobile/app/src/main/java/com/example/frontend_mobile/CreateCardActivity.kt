package com.example.frontend_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme

class CreateCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Frontend_mobileTheme {
                Surface(color = Color(0xFF414162)) {
                    CreateCardScreen(onBackPressed = { finish() })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCardScreen(onBackPressed: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val termDefinitions = remember { mutableStateListOf(Pair("", "")) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with back button, settings, and check mark
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

                Text(
                    text = "Create card",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                // Settings icon moved to left of check mark
                IconButton(onClick = { /* Settings action */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                // Check mark at rightmost
                IconButton(onClick = { /* Save action */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Save",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title input (single line)
            BasicTextField(
                value = title,
                onValueChange = { title = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                decorationBox = { innerTextField ->
                    if (title.isEmpty()) {
                        Text(
                            "Subject, Chapter, Unit",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            Column {
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "TITLE",
                    color = Color.White,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp)) // Adds space after the text
            }

            // Description input
            BasicTextField(
                value = description,
                onValueChange = { description = it },
                textStyle = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                decorationBox = { innerTextField ->
                    if (description.isEmpty()) {
                        Text(
                            "Add a description",
                            color = Color.White.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    }
                    innerTextField()
                }
            )

            Column {
                Divider(
                    color = Color.White,
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    "DESCRIPTION",
                    color = Color.White,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(16.dp)) // Adds space after the text
            }

            // Term-Definition pairs
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(termDefinitions) { (term, definition) ->
                    TermDefinitionBox(
                        term = term,
                        onTermChange = { newTerm ->
                            val index = termDefinitions.indexOfFirst { it.first == term && it.second == definition }
                            if (index != -1) {
                                termDefinitions[index] = Pair(newTerm, definition)
                            }
                        },
                        definition = definition,
                        onDefinitionChange = { newDefinition ->
                            val index = termDefinitions.indexOfFirst { it.first == term && it.second == definition }
                            if (index != -1) {
                                termDefinitions[index] = Pair(term, newDefinition)
                            }
                        }
                    )
                }
            }
        }

        // Floating add button
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            FloatingActionButton(
                onClick = { termDefinitions.add(Pair("", "")) },
                containerColor = Color(0xFF3D709F),
                modifier = Modifier
                    .size(56.dp)
                    .shadow(8.dp, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.plus),
                    contentDescription = "Add Term & Definition",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

@Composable
fun TermDefinitionBox(
    term: String,
    onTermChange: (String) -> Unit,
    definition: String,
    onDefinitionChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF3D709F), RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {

        // Term input with underline
        BasicTextField(
            value = term,
            onValueChange = onTermChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Underline for term
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "TERM",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 4.dp)
        )


        // Definition input with underline
        BasicTextField(
            value = definition,
            onValueChange = onDefinitionChange,
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        // Underline for definition
        Divider(
            color = Color.White,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp)
        )
        Text(
            text = "DEFINITION",
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF414162)
@Composable
fun CreateCardPreview() {
    Frontend_mobileTheme {
        CreateCardScreen(onBackPressed = {})
    }
}