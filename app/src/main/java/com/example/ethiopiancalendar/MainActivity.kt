package com.example.ethiopiancalendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalendarTheme {
                CalendarScreen()
            }
        }
    }
}

@Composable
fun CalendarTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) {
        darkColorScheme(
            primary = Color(0xFF7BD88F),
            onPrimary = Color(0xFF00390F),
            secondary = Color(0xFFF2B705),
            tertiary = Color(0xFFFF8A80),
            background = Color(0xFF121212),
            onBackground = Color(0xFFE6E6E6),
            surface = Color(0xFF121212),
            onSurface = Color(0xFFE6E6E6),
            surfaceVariant = Color(0xFF2A2A2A),
            onSurfaceVariant = Color(0xFFE6E6E6)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF1B7F3B),
            onPrimary = Color.White,
            secondary = Color(0xFFF2B705),
            tertiary = Color(0xFFC62828),
            background = Color(0xFFFFFBF2),
            onBackground = Color(0xFF1C1B1A),
            surface = Color(0xFFFFFBF2),
            onSurface = Color(0xFF1C1B1A),
            surfaceVariant = Color(0xFFF1EBDD),
            onSurfaceVariant = Color(0xFF1C1B1A)
        )
    }
    MaterialTheme(colorScheme = colors, content = content)
}

@Composable
fun CalendarScreen() {
    val today = remember { EthiopianCalendar.today() }
    val todayJdn = remember { EthiopianCalendar.toJdn(today) }

    var year by rememberSaveable { mutableIntStateOf(today.year) }
    var month by rememberSaveable { mutableIntStateOf(today.month) }
    var selectedJdn by rememberSaveable { mutableIntStateOf(todayJdn) }

    fun goPrevious() {
        if (month == 1) {
            year -= 1
            month = 13
        } else {
            month -= 1
        }
    }

    fun goNext() {
        if (month == 13) {
            year += 1
            month = 1
        } else {
            month += 1
        }
    }

    val firstJdn = EthiopianCalendar.toJdn(year, month, 1)
    val daysInMonth = EthiopianCalendar.daysInMonth(year, month)
    val leadingBlanks = EthiopianCalendar.weekdayIndex(firstJdn)
    val weekCount = (leadingBlanks + daysInMonth + 6) / 7

    val firstGreg = EthiopianCalendar.toLocalDate(firstJdn)
    val lastGreg = EthiopianCalendar.toLocalDate(firstJdn + daysInMonth - 1)
    val shortFormat = DateTimeFormatter.ofPattern("MMM d")
    val fullFormat = DateTimeFormatter.ofPattern("MMM d, yyyy")
    val gregorianRange = if (firstGreg.year == lastGreg.year) {
        "${firstGreg.format(shortFormat)} – ${lastGreg.format(fullFormat)}"
    } else {
        "${firstGreg.format(fullFormat)} – ${lastGreg.format(fullFormat)}"
    }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            // Month header with previous / next buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { goPrevious() }) {
                    Text("‹", fontSize = 32.sp)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${EthiopianCalendar.monthNames[month - 1]} $year",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${EthiopianCalendar.monthNamesAmharic[month - 1]} $year",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = gregorianRange,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
                    )
                }
                TextButton(onClick = { goNext() }) {
                    Text("›", fontSize = 32.sp)
                }
            }

            Spacer(Modifier.height(8.dp))

            // Weekday names
            Row(modifier = Modifier.fillMaxWidth()) {
                for (i in 0 until 7) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = EthiopianCalendar.weekdayShortAmharic[i],
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = EthiopianCalendar.weekdayShortEnglish[i],
                            fontSize = 10.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.65f)
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // Day grid
            for (week in 0 until weekCount) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    for (col in 0 until 7) {
                        val day = week * 7 + col - leadingBlanks + 1
                        if (day in 1..daysInMonth) {
                            val jdn = firstJdn + day - 1
                            val greg = EthiopianCalendar.toLocalDate(jdn)
                            val eth = EthiopianDate(year, month, day)
                            DayCell(
                                day = day,
                                gregorianDay = greg.dayOfMonth,
                                isToday = jdn == todayJdn,
                                isSelected = jdn == selectedJdn,
                                isHoliday = Holidays.forDate(eth, greg).isNotEmpty(),
                                onClick = { selectedJdn = jdn }
                            )
                        } else {
                            Spacer(Modifier.weight(1f).height(56.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Details of the selected day
            val selectedEth = EthiopianCalendar.fromJdn(selectedJdn)
            val selectedGreg = EthiopianCalendar.toLocalDate(selectedJdn)
            val weekday = EthiopianCalendar.weekdayIndex(selectedJdn)
            val holidays = Holidays.forDate(selectedEth, selectedGreg)

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "${EthiopianCalendar.weekdayNames[weekday]}, " +
                            "${EthiopianCalendar.monthNames[selectedEth.month - 1]} " +
                            "${selectedEth.day}, ${selectedEth.year}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${EthiopianCalendar.weekdayNamesAmharic[weekday]}፣ " +
                            "${EthiopianCalendar.monthNamesAmharic[selectedEth.month - 1]} " +
                            "${selectedEth.day} ${selectedEth.year}",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(6.dp))
                    Text(
                        text = "Gregorian: " +
                            selectedGreg.format(DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    for (name in holidays) {
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "★ $name",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    year = today.year
                    month = today.month
                    selectedJdn = todayJdn
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Today  ·  ዛሬ")
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun RowScope.DayCell(
    day: Int,
    gregorianDay: Int,
    isToday: Boolean,
    isSelected: Boolean,
    isHoliday: Boolean,
    onClick: () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val shape = RoundedCornerShape(10.dp)
    val textColor = when {
        isToday -> scheme.onPrimary
        isHoliday -> scheme.tertiary
        else -> scheme.onBackground
    }

    var cellModifier = Modifier
        .weight(1f)
        .height(56.dp)
        .padding(2.dp)
        .clip(shape)
        .background(if (isToday) scheme.primary else Color.Transparent)
    if (isSelected) {
        cellModifier = cellModifier.border(2.dp, scheme.secondary, shape)
    }

    Box(
        modifier = cellModifier.clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.toString(),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = textColor
            )
            Text(
                text = gregorianDay.toString(),
                fontSize = 10.sp,
                color = textColor.copy(alpha = 0.7f)
            )
        }
        if (isHoliday) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(5.dp)
                    .size(6.dp)
                    .background(if (isToday) scheme.secondary else scheme.tertiary, CircleShape)
            )
        }
    }
}
