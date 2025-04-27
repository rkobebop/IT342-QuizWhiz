package com.example.frontend_mobile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.frontend_mobile.ui.theme.Frontend_mobileTheme
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Frontend_mobileTheme {
                Surface(color = Color(0xFF414162)) {
                    HomeScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    // Add this to debug current route
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStack?.destination?.route

    LaunchedEffect(Unit) {
        Log.d("NAVIGATION", "Current route: $currentRoute")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("home") { HomeContent(navController) }
                    composable("profile") { ProfileContent(navController) }
                    composable("notifications") { NotificationsScreen { navController.popBackStack() } }
                }
            }
            BottomNavigationBar(navController, currentRoute)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(navController: NavHostController, currentRoute: String?) {
    val context = LocalContext.current
    NavigationBar(
        containerColor = Color(0xFF414162),
        contentColor = Color.White,
        modifier = Modifier.height(80.dp)
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    "Home",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            },
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(0xFF414162)
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "Add",
                    tint = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.size(40.dp)
                )
            },
            selected = false,
            onClick = {
                val intent = Intent(context, CreateCardActivity::class.java)
                context.startActivity(intent)
            },
            label = null,
            colors = NavigationBarItemDefaults.colors(
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(28.dp)
                )
            },
            label = {
                Text(
                    "Profile",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            },
            selected = currentRoute == "profile",
            onClick = { navController.navigate("profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.6f),
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White.copy(alpha = 0.6f),
                indicatorColor = Color(0xFF414162)
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "QuizWhiz",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Free Trial Badge
                        Surface(
                            shape = RoundedCornerShape(50.dp),
                            color = Color(0xFFFFD700)
                        ) {
                            Text(
                                text = "Free Trial",
                                color = Color.Black,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        IconButton(onClick = { navController.navigate("notifications") }) {
                            Icon(
                                painter = painterResource(id = R.drawable.notifications),
                                contentDescription = "Notifications",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    },
                    placeholder = { Text("Look for flashcards...", color = Color.Gray) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Gray,
                        focusedBorderColor = Color(0xFF4A93D7),
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(50.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Achievements
                Text(
                    text = "Achievements",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Streak Card
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                        .height(180.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF414162)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "1-Week Streak",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Study next week to keep the streak!",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp
                        )
                        Spacer(modifier = Modifier.height(24.dp))

                        // Week days
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                                Text(
                                    text = day,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(24.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Dates
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            listOf("23", "24", "25", "26", "27", "28", "29").forEachIndexed { index, date ->
                                if (date == "27") {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(32.dp)
                                            .background(Color(0xFFFFD700), CircleShape)
                                    ) {
                                        Text(
                                            text = date,
                                            color = Color.Black,
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp
                                        )
                                    }
                                } else {
                                    Text(
                                        text = date,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.width(24.dp),
                                        fontSize = 14.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Your Flashcards
                Text(
                    text = "Your Flashcards",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Flashcard List with improved spacing
                val context = LocalContext.current
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                        .height(120.dp)
                        .clickable {
                            context.startActivity(Intent(context, OpenFlashcardsActivity::class.java))
                        },
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF414162)
                )  {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.TopStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "Introduction to Computing",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(Color(0xFF4CAF50), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Raven Pavo",
                                color = Color.White.copy(alpha = 0.8f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ProfileContent(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF414162))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header (same as HomeContent)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "QuizWhiz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Free Trial Badge
                Surface(
                    shape = RoundedCornerShape(50.dp),
                    color = Color(0xFFFFD700)
                ) {
                    Text(
                        text = "Free Trial",
                        color = Color.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = { navController.navigate("notifications") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.notifications),
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Profile picture with edit button
        Box(
            modifier = Modifier.size(120.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFF3D709F), CircleShape)
            )

            // Edit button
            IconButton(
                onClick = { /* Handle edit profile picture */ },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .background(Color(0xFFFFD700), CircleShape)
                    .size(32.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit Profile",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = "Raven Pavo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Settings card - now with outline only
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF414162) // Same as background
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.settings),
                    contentDescription = "Settings",
                    tint = Color.White, // Changed to white
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Your Settings",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Changed to white
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Achievements",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Start) // This makes it left-aligned
                .padding(bottom = 8.dp)
        )

        // Achievements card - now with outline only
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, Color.White, RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = Color(0xFF414162) // Same as background
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {


                Text(
                    text = "1-Week Streak",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Changed to white
                )
                Text(
                    text = "Study next week to keep the streak!",
                    color = Color.White.copy(alpha = 0.8f), // Changed to white
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Week days
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("S", "M", "T", "W", "T", "F", "S").forEach { day ->
                        Text(
                            text = day,
                            color = Color.White, // Changed to white
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Dates
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    listOf("23", "24", "25", "26", "27", "28", "29").forEach { date ->
                        Text(
                            text = date,
                            color = Color.White, // Changed to white
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Frontend_mobileTheme {
        Surface(color = Color(0xFF414162)) {
            HomeScreen()
        }
    }
}