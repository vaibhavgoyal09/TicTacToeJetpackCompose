package com.vaibhav.presentation.common.components

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vaibhav.R
import com.vaibhav.presentation.ui.ShadowStartColor

@ExperimentalMaterialApi
@Composable
fun StandardSettingsIcon(
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    Card(
        modifier = modifier
            .size(60.dp)
            .padding(4.dp),
        backgroundColor = MaterialTheme.colors.surface,
        shape = CircleShape,
        elevation = 12.dp,
        border = BorderStroke(1.dp, ShadowStartColor),
        onClick = {
            Toast.makeText(context, "I'm just for decoration", Toast.LENGTH_SHORT).show()
        }
    ) {
        Image(
            painter = painterResource(id = R.drawable.settings),
            contentDescription = stringResource(id = R.string.settings_content_desc),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        )
    }
}