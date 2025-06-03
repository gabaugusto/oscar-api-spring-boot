package com.sample.oscarinterface.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sample.oscarinterface.R

val textLarge = TextStyle(
    fontFamily = FontFamily(Font(R.font.montserrat)),
    fontWeight = FontWeight.W600,
    fontSize = 36.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
)

// Set of Material typography styles to start with
@Composable
fun ProvideTypography(): Typography{
    return Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat)), // Use Montserrat for body text
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = MaterialTheme.colorScheme.primary,

        letterSpacing = 0.5.sp,
    )
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
}