@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.articles.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.comppot.mindsnack.R

@Composable
fun CustomSearchBar(
    onSearch: (String) -> Unit,
    expanded: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = SearchBarDefaults.dockedShape,
    colors: SearchBarColors = SearchBarDefaults.colors(),
    tonalElevation: Dp = SearchBarDefaults.TonalElevation,
    shadowElevation: Dp = SearchBarDefaults.ShadowElevation,
    content: @Composable ColumnScope.() -> Unit,
) {
    Surface(
        shape = shape,
        color = colors.containerColor,
        contentColor = contentColorFor(colors.containerColor),
        tonalElevation = tonalElevation,
        shadowElevation = shadowElevation,
        modifier = modifier.zIndex(1f)
    ) {
        Column {
            SearchInputField(onSearch, modifier = Modifier.fillMaxWidth())
            AnimatedVisibility(visible = expanded) {
                Column {
                    HorizontalDivider(color = colors.dividerColor)
                    content()
                }
            }
        }
    }
}

@Composable
private fun SearchInputField(
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf("") }

    SearchBarDefaults.InputField(
        query = text,
        onQueryChange = {
            text = it
            onSearch(text)
        },
        onSearch = { focusManager.clearFocus() },
        expanded = false,
        onExpandedChange = {},
        modifier = modifier,
        placeholder = { Text(stringResource(id = R.string.search_bar_placeholder)) },
        leadingIcon = { SearchIcon() },
        trailingIcon = {
            if (text.isNotEmpty()) {
                ClearIcon {
                    text = ""
                    onSearch(text)
                    focusManager.clearFocus()
                }
            }
        }
    )
}

@Composable
private fun SearchIcon() {
    Icon(
        Icons.Default.Search,
        contentDescription = stringResource(id = R.string.icon_search)
    )
}

@Composable
private fun ClearIcon(onClick: () -> Unit) {
    IconButton(onClick) {
        Icon(
            Icons.Outlined.Close,
            contentDescription = stringResource(id = R.string.icon_clear)
        )
    }
}

@Preview
@Composable
fun CustomSearchBarPreview() {
    CustomSearchBar(onSearch = {}, expanded = false) {}
}

@Preview
@Composable
fun CustomSearchBarExpandedPreview() {
    CustomSearchBar(onSearch = {}, expanded = true) {
        Text("Expanded text", modifier = Modifier.padding(16.dp))
    }
}
