@file:OptIn(ExperimentalMaterial3Api::class)

package com.comppot.mindsnack.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.comppot.mindsnack.R
import com.comppot.mindsnack.model.Rating

@Composable
fun RatingCard(modifier: Modifier = Modifier, onSubmit: (Rating) -> Unit = {}) {
    var currentRating by remember { mutableStateOf(Rating.OKAY) }
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.article_rating_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            EmojiSlider(currentRating) { currentRating = it }
            Button(onClick = { onSubmit(currentRating) }) {
                Text(text = stringResource(id = R.string.article_rating_submit))
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun EmojiSlider(rating: Rating, onRatingChange: (Rating) -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Rating.entries.forEach {
                Text(
                    text = it.emoji,
                    style = TextStyle(fontSize = 36.sp),
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { onRatingChange(it) })
            }
        }
        Slider(
            value = rating.value.toFloat(),
            onValueChange = { onRatingChange(Rating.fromValue(it.toInt())) },
            steps = 3,
            valueRange = -2f..2f,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            thumb = {
                SliderDefaults.Thumb(
                    interactionSource = remember { MutableInteractionSource() },
                    thumbSize = DpSize(20.dp, 20.dp)
                )
            },
            track = {
                SliderDefaults.Track(
                    sliderState = it,
                    modifier = Modifier.height(12.dp),
                    thumbTrackGapSize = 0.dp,
                )
            }
        )
    }
}

@Preview
@Composable
fun RatingPreview() {
    RatingCard()
}