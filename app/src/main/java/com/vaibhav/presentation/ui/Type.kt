package com.vaibhav.presentation.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vaibhav.R

val customFonts = FontFamily(
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.balsamiq_sans_bold, weight = FontWeight.SemiBold),
    Font(R.font.balsamiq_sans_regular, weight = FontWeight.Normal),
    Font(R.font.acme_regular, weight = FontWeight.Bold)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = customFonts,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    body1 = TextStyle(
        fontFamily = customFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h2 = TextStyle(
        fontFamily = customFonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = customFonts,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    ),
    h3 = TextStyle(
        fontFamily = customFonts,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    )
)