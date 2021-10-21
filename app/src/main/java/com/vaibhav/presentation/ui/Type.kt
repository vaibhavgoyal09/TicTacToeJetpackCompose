package com.vaibhav.presentation.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.vaibhav.R

val poppinsFont = FontFamily(
    Font(R.font.poppins_light, weight = FontWeight.Light),
    Font(R.font.poppins_medium, weight = FontWeight.Medium),
    Font(R.font.poppins_regular, weight = FontWeight.Normal),
    Font(R.font.poppins_semi_bold, weight = FontWeight.SemiBold),
    Font(R.font.poppins_bold, weight = FontWeight.Bold)
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    h2 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    ),
    h3 = TextStyle(
        fontFamily = poppinsFont,
        fontWeight = FontWeight.Light,
        fontSize = 13.sp
    )
)