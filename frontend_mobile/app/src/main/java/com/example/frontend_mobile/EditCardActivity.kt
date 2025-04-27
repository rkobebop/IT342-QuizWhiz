package com.example.frontend_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme

class EditCardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Frontend_mobileTheme {
                Surface(color = Color(0xFF414162)) {
                    EditCardScreen(
                        onBackPressed = { finish() },
                        initialTitle = intent.getStringExtra("title") ?: "",
                        initialDescription = intent.getStringExtra("description") ?: "",
                        initialTerms = intent.getStringArrayListExtra("terms")?.map { term ->
                            Pair(term, intent.getStringArrayListExtra("definitions")?.getOrNull(
                                intent.getStringArrayListExtra("terms")?.indexOf(term) ?: 0
                            ) ?: "")
                        } ?: listOf(Pair("", ""))
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCardScreen(
    onBackPressed: () -> Unit,
    initialTitle: String,
    initialDescription: String,
    initialTerms: List<Pair<String, String>>
) {
    var title by remember { mutableStateOf(initialTitle) }
    var description by remember { mutableStateOf(initialDescription) }
    val termDefinitions = remember { mutableStateListOf<Pair<String, String>>().apply {
        addAll(initialTerms)
    } }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header with back button and save button
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
                    text = "Edit Flashcard",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { /* Save changes and go back */
                        onBackPressed()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.check),
                        contentDescription = "Save",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title input
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
                Spacer(modifier = Modifier.height(16.dp))
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
                Spacer(modifier = Modifier.height(16.dp))
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
                            val index =
                                termDefinitions.indexOfFirst { it.first == term && it.second == definition }
                            if (index != -1) {
                                termDefinitions[index] = Pair(newTerm, definition)
                            }
                        },
                        definition = definition,
                        onDefinitionChange = { newDefinition ->
                            val index =
                                termDefinitions.indexOfFirst { it.first == term && it.second == definition }
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

@Preview(showBackground = true, backgroundColor = 0xFF414162)
@Composable
fun EditCardPreview() {
    Frontend_mobileTheme {
        EditCardScreen(
            onBackPressed = {},
            initialTitle = "Introduction to Computing",
            initialDescription = "Basic computing concepts",
            initialTerms = listOf(
                Pair("Computer", "An electronic device"),
                Pair("Algorithm", "Step-by-step procedure")
            )
        )
    }
}