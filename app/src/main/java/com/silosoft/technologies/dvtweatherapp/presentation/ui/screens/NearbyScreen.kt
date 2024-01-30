package com.silosoft.technologies.dvtweatherapp.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silosoft.technologies.dvtweatherapp.domain.model.NearbyRestaurantsUiModel

@Suppress("MagicNumber")
@Composable
fun NearbyScreen(nearbyRestaurantsUiModel: NearbyRestaurantsUiModel?) {
    nearbyRestaurantsUiModel?.apply {
        Column {
            Text(
                text = "You hungry? You thirsty? You might want to try these venues in your area:",
                Modifier.padding(8.dp)
            )
            LazyColumn {
                // Header
                item {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            "Name", modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp), fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Rating", modifier = Modifier
                                .weight(0.2f)
                                .padding(8.dp), fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Vicinity", modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp), fontSize = 18.sp, fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                // Rows
                items(restaurants) { restaurant ->
                    Divider(color = Color.White)
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            restaurant.name, modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp)
                        )
                        Text(
                            restaurant.overallRating.toString(), modifier = Modifier
                                .weight(0.2f)
                                .padding(8.dp)
                        )
                        Text(
                            restaurant.vicinity, modifier = Modifier
                                .weight(0.4f)
                                .padding(8.dp)
                        )
                    }
                }
            }
        }

    } ?: Text(
        text = "Couldn't find any nearby restaurants!",
        Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}
