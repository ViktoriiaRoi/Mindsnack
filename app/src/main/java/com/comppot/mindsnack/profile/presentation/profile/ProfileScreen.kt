package com.comppot.mindsnack.profile.presentation.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.comppot.mindsnack.R
import com.comppot.mindsnack.profile.domain.model.ThemeMode
import com.comppot.mindsnack.profile.domain.model.User
import com.comppot.mindsnack.profile.domain.model.UserPreferences
import com.comppot.mindsnack.core.presentation.components.ThemeModeDialog

enum class ProfileDialogType {
    THEME_MODE
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    val state = viewModel.profileState.collectAsState().value
    val dialogState = remember { mutableStateOf<ProfileDialogType?>(null) }

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(40.dp)
    ) {
        UserImage(imageUrl = state.user.image)
        SettingsCard(
            user = state.user,
            preferences = state.preferences,
            showDialog = { dialogState.value = it }
        )
    }

    when (dialogState.value) {
        ProfileDialogType.THEME_MODE -> ThemeModeDialog(
            initialTheme = state.preferences.themeMode,
            onSave = { viewModel.updateThemeMode(it) },
            onDismiss = { dialogState.value = null },
        )

        else -> {}
    }
}

@Composable
private fun UserImage(imageUrl: String) {
    Box {
        AsyncImage(
            imageUrl,
            error = painterResource(id = R.drawable.profile_placeholder),
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        /*EditIconButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .absoluteOffset(x = 15.dp, y = 15.dp)
        )*/
    }
}

@Composable
private fun EditIconButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Icon(
            Icons.Outlined.Edit,
            contentDescription = stringResource(id = R.string.icon_edit)
        )
    }
}

@Composable
private fun SettingsCard(
    user: User,
    preferences: UserPreferences,
    showDialog: (ProfileDialogType) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        SettingsRow(stringResource(id = R.string.profile_settings_full_name)) {
            NameText(user.fullName)
        }
        CardDivider()
        SettingsRow(stringResource(id = R.string.profile_settings_theme_mode)) {
            ThemeText(
                preferences.themeMode,
                onClick = { showDialog(ProfileDialogType.THEME_MODE) }
            )
        }
    }
}

@Composable
private fun SettingsRow(title: String, content: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )
        content()
    }
}

@Composable
private fun NameText(text: String, onClick: () -> Unit = {}) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.clickable { onClick() },
    )
}

@Composable
private fun ThemeText(theme: ThemeMode, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier.clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            theme.icon,
            contentDescription = stringResource(id = theme.stringId),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = theme.stringId),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun CardDivider() {
    HorizontalDivider(
        color = MaterialTheme.colorScheme.outlineVariant,
        thickness = 1.dp
    )
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    ProfileScreen()
}
