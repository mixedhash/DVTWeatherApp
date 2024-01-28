package com.silosoft.technologies.dvtweatherapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.silosoft.technologies.dvtweatherapp.R
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.ForecastDay
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.ForecastUiModel
import com.silosoft.technologies.dvtweatherapp.domain.ui_model.WeatherUiModel
import com.silosoft.technologies.dvtweatherapp.presentation.ui.theme.CloudyGray
import com.silosoft.technologies.dvtweatherapp.presentation.ui.theme.RainyMatte
import com.silosoft.technologies.dvtweatherapp.presentation.ui.theme.SunnyGreen
import java.util.Locale

@Composable
fun HomeScreen(
    weatherUiModel: WeatherUiModel?,
    forecastUiModel: ForecastUiModel?
) {
    weatherUiModel?.apply {
        Column(
            Modifier
                .fillMaxSize()
                .background(getBackgroundColor(weatherType))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
            ) {
                WeatherImage(modifier = Modifier.fillMaxSize(), weatherType = weatherType)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = locationName,
                        color = Color.White,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "${weatherUiModel.currentTemp}°",
                        color = Color.White,
                        fontSize = 60.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = weatherUiModel.weatherType.uppercase(Locale.ROOT),
                        color = Color.White,
                        fontSize = 35.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${weatherUiModel.minTemp}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${weatherUiModel.currentTemp}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${weatherUiModel.maxTemp}°",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "min",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "Current",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "max",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Right
                    )
                }
                Divider(Modifier.padding(top = 8.dp, bottom = 16.dp))
                forecastUiModel?.apply {
                    forecast.forEach { day ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp, horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = day.dayOfWeek,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light,
                                modifier = Modifier.weight(1f)
                            )
                            WeatherIcon(
                                modifier = Modifier
                                    .size(25.dp)
                                    .weight(1f),
                                weatherType = day.weatherType
                            )
                            Text(
                                text = "${day.temp}°",
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Right
                            )
                        }
                    }
                } ?: Text(text = "Couldn't fetch forecast data, something went wrong!")
            }
        }
    } ?: Text(text = "Couldn't fetch weather data, something went wrong!")
}

@Composable
private fun WeatherImage(modifier: Modifier, weatherType: String) {
    when (weatherType) {
        "Clear" -> Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.forest_sunny),
            contentDescription = "Sunny forest image",
            contentScale = ContentScale.FillBounds
        )

        "Clouds" -> Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.forest_cloudy),
            contentDescription = "Cloudy forest image",
            contentScale = ContentScale.FillBounds
        )

        "Rain" -> Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.forest_rainy),
            contentDescription = "Rainy forest image",
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
private fun WeatherIcon(modifier: Modifier, weatherType: String) {
    when (weatherType) {
        "Clear" -> Icon(
            modifier = modifier,
            tint = Color.White,
            painter = painterResource(id = R.drawable.clear),
            contentDescription = "Sunny forest icon"
        )

        "Clouds" -> Icon(
            modifier = modifier,
            tint = Color.White,
            painter = painterResource(id = R.drawable.partlysunny),
            contentDescription = "Cloudy forest icon"
        )

        "Rain" -> Icon(
            modifier = modifier,
            tint = Color.White,
            painter = painterResource(id = R.drawable.rain),
            contentDescription = "Rainy forest icon"
        )
    }
}

private fun getBackgroundColor(weatherType: String) =
    when (weatherType) {
        "Clear" -> SunnyGreen
        "Clouds" -> CloudyGray
        "Rain" -> RainyMatte
        else -> Color.White
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        weatherUiModel = WeatherUiModel(
            weatherType = "Clouds",
            currentTemp = 21,
            minTemp = 19,
            maxTemp = 26,
            locationName = "Cluj-Napoca"
        ),
        forecastUiModel = ForecastUiModel(
            listOf(
                ForecastDay(
                    dayOfWeek = "Tuesday",
                    weatherType = "Clouds",
                    temp = 24
                ),
                ForecastDay(
                    dayOfWeek = "Wednesday",
                    weatherType = "Clear",
                    temp = 24
                ),
                ForecastDay(
                    dayOfWeek = "Thursday",
                    weatherType = "Rain",
                    temp = 24
                ),
                ForecastDay(
                    dayOfWeek = "Friday",
                    weatherType = "Clear",
                    temp = 29
                ),
                ForecastDay(
                    dayOfWeek = "Saturday",
                    weatherType = "Clouds",
                    temp = 24
                )
            )
        )
    )
}
