package com.comppot.mindsnack.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comppot.mindsnack.R
import com.comppot.mindsnack.profile.domain.model.ThemeMode

@Composable
fun EditNameDialog(
    initialFirstName: String,
    initialLastName: String,
    onDismiss: () -> Unit = {},
    onSave: (String, String) -> Unit = { _, _ -> }
) {
    val firstName = remember { mutableStateOf(initialFirstName) }
    val lastName = remember { mutableStateOf(initialLastName) }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = { DismissButton(onDismiss) },
        confirmButton = {
            ConfirmButton {
                onSave(firstName.value, lastName.value)
                onDismiss()
            }
        },
        title = { TitleText(title = stringResource(id = R.string.dialog_edit_name_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DialogTextField(
                    state = firstName,
                    label = stringResource(id = R.string.dialog_edit_name_first)
                )
                DialogTextField(
                    state = lastName,
                    label = stringResource(id = R.string.dialog_edit_name_last)
                )
            }
        }
    )
}

@Composable
fun SuggestBookDialog(
    onDismiss: () -> Unit = {},
    onSave: (String, String) -> Unit = { _, _ -> }
) {
    val title = remember { mutableStateOf("") }
    val author = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = { DismissButton(onDismiss) },
        confirmButton = {
            ConfirmButton(enabled = title.value.isNotBlank() && author.value.isNotBlank()) {
                onSave(title.value, author.value)
                onDismiss()
            }
        },
        title = { TitleText(title = stringResource(id = R.string.dialog_suggest_book_title)) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                DialogTextField(
                    state = title,
                    label = stringResource(id = R.string.dialog_suggest_book_title_of_book)
                )
                DialogTextField(
                    state = author,
                    label = stringResource(id = R.string.dialog__suggest_book_author)
                )
            }
        }
    )
}

@Composable
fun ThemeModeDialog(
    initialTheme: ThemeMode,
    onSave: (ThemeMode) -> Unit = { _ -> },
    onDismiss: () -> Unit = {}
) {
    var selectedTheme by remember { mutableStateOf(initialTheme) }

    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = { DismissButton(onDismiss) },
        confirmButton = {
            ConfirmButton {
                onSave(selectedTheme)
                onDismiss()
            }
        },
        title = { TitleText(stringResource(id = R.string.dialog_theme_mode_title)) },
        text = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ThemeMode.entries.forEach { theme ->
                    ThemeButton(
                        theme = theme,
                        isSelected = theme == selectedTheme,
                        modifier = Modifier.weight(1f),
                        onClick = { selectedTheme = theme }
                    )
                }
            }
        }
    )
}

@Composable
private fun ThemeButton(
    theme: ThemeMode,
    isSelected: Boolean,
    modifier: Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        enabled = !isSelected,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(8.dp),
        border = ButtonDefaults.outlinedButtonBorder(true),
        colors = ButtonDefaults.outlinedButtonColors().copy(
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                theme.icon,
                contentDescription = stringResource(id = theme.stringId)
            )
            Text(text = stringResource(id = theme.stringId))
        }
    }
}

@Composable
private fun TitleText(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun DismissButton(onClick: () -> Unit) {
    TextButton(onClick = onClick) {
        Text(stringResource(id = R.string.dialog_cancel))
    }
}

@Composable
private fun ConfirmButton(enabled: Boolean = true, onClick: () -> Unit) {
    TextButton(enabled = enabled, onClick = onClick) {
        Text(stringResource(id = R.string.dialog_save))
    }
}

@Composable
private fun DialogTextField(state: MutableState<String>, label: String) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        label = { Text(label) },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
    )
}

@Preview
@Composable
fun EditNameDialogPreview() {
    EditNameDialog(
        initialFirstName = "Isaak",
        initialLastName = "Newton",
    )
}

@Preview
@Composable
fun SuggestBookDialogPreview() {
    SuggestBookDialog()
}

@Preview
@Composable
fun ThemeModeDialogPreview() {
    ThemeModeDialog(
        initialTheme = ThemeMode.LIGHT
    )
}
