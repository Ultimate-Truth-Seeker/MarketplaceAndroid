package com.example.marketplace.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.marketplace.R

@Composable
fun UserProfileScreen(
    userName: String,
    userEmail: String,
    userProfilePic: Int,
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onManageAddresses: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .padding(16.dp)
    ) {
        // Encabezado de perfil
        ProfileHeader(userName, userEmail, userProfilePic)

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de edición y ajustes
        ProfileOptions(
            onEditProfile = onEditProfile,
            onChangePassword = onChangePassword,
            onManageAddresses = onManageAddresses
        )

        Spacer(modifier = Modifier.weight(1f))

        // Botón de cerrar sesión
        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Red),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Log Out", color = Color.White)
        }
    }
}

@Composable
fun ProfileHeader(userName: String, userEmail: String, userProfilePic: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = userProfilePic),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(100.dp)
                .background(Color.Gray, shape = CircleShape)
                .padding(2.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = userName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = userEmail,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun ProfileOptions(
    onEditProfile: () -> Unit,
    onChangePassword: () -> Unit,
    onManageAddresses: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ProfileOptionItem(text = "Edit Profile", onClick = onEditProfile)
        ProfileOptionItem(text = "Change Password", onClick = onChangePassword)
        ProfileOptionItem(text = "Manage Addresses", onClick = onManageAddresses)
    }
}

@Composable
fun ProfileOptionItem(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)
    ) {
        Text(text = text, color = Color.White, fontWeight = FontWeight.Medium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserProfileScreen() {
    UserProfileScreen(
        userName = "John Doe",
        userEmail = "john.doe@example.com",
        userProfilePic = R.drawable.sample_profile,
        onEditProfile = {},
        onChangePassword = {},
        onManageAddresses = {},
        onLogout = {}
    )
}
