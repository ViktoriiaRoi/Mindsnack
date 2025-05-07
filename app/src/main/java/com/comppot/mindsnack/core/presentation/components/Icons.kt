package com.comppot.mindsnack.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.comppot.mindsnack.R

@Composable
fun BackIcon() {
    Icon(
        Icons.AutoMirrored.Default.ArrowBack,
        contentDescription = stringResource(id = R.string.icon_back)
    )
}

@Composable
fun SaveIcon(isSaved: Boolean) {
    Icon(
        if (isSaved) Icons.Default.Bookmark else Icons.Outlined.BookmarkBorder,
        contentDescription = stringResource(id = R.string.icon_save)
    )
}

@Composable
fun ShareIcon() {
    Icon(
        Icons.Outlined.Share,
        contentDescription = stringResource(id = R.string.icon_share),
    )
}

@Composable
fun NotificationIcon() {
    Icon(
        Icons.Outlined.Notifications,
        contentDescription = stringResource(id = R.string.screen_notifications)
    )
}

@Composable
fun MoreIcon() {
    Icon(
        imageVector = Icons.Outlined.MoreVert,
        contentDescription = stringResource(id = R.string.icon_more),
    )
}
