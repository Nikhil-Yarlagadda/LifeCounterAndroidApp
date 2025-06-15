package com.example.lifecounter.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.lifecounter.R

val bebasNueue = FontFamily(
     Font(R.font.bebasneue_regular)
)

val firaSans = FontFamily(
    Font(R.font.firasans_black)
)

val AppTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = bebasNueue,
        fontWeight = FontWeight.Normal,
        fontSize = 72.sp
    ),
    displayMedium = TextStyle(
        fontFamily = bebasNueue,
        fontWeight = FontWeight.Medium,
        fontSize = 48.sp
    ),
    labelLarge = TextStyle(
        fontFamily = firaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = firaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    labelSmall = TextStyle(
        fontFamily = firaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = bebasNueue,
        fontWeight = FontWeight.Thin,
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = firaSans,
        fontWeight = FontWeight.Normal,
        fontSize = 8.sp
    )
)


