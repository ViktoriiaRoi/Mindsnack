package com.comppot.mindsnack.articles.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.comppot.mindsnack.articles.domain.model.CardData
import com.comppot.mindsnack.core.presentation.components.SaveIcon

@Composable
fun CardItem(
    card: CardData,
    progressText: String,
    modifier: Modifier = Modifier,
    onSaveChanged: (Boolean) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!card.title.isNullOrEmpty()) {
                Text(
                    text = card.title,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Text(text = card.text, style = MaterialTheme.typography.bodyLarge)
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Text(
                    text = progressText,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.End,
                    modifier = Modifier.alpha(0.5f)
                )
                Spacer(Modifier.weight(1f))
                SaveCardIcon(card.isSaved) {
                    onSaveChanged(!card.isSaved)
                }
            }
        }
    }
}

@Composable
fun SavedCardItem(card: CardData, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    val hasTitle = !card.title.isNullOrEmpty()
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (hasTitle) {
                Text(
                    text = card.title!!,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
            Text(
                text = card.text,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = if (hasTitle) 5 else 7,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun SaveCardIcon(isSaved: Boolean, onClick: () -> Unit) {
    IconButton(onClick) {
        SaveIcon(isSaved)
    }
}

@Preview
@Composable
private fun CardWithTitlePreview() {
    val card = getCardExample()
    CardItem(card, "1/20")
}

@Preview
@Composable
private fun SavedCardWithTitlePreview() {
    val card = getCardExample()
    SavedCardItem(card, modifier = Modifier.width(200.dp))
}

private fun getCardExample() = CardData(
    1,
    "Second law",
    "The change of motion of an object is proportional to the force impressed; and is made in the direction of the straight line in which the force is impressed."
)
