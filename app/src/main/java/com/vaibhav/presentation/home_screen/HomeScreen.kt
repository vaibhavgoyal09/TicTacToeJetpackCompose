package com.vaibhav.presentation.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.R
import com.vaibhav.presentation.common.components.StandardSettingsIcon
import com.vaibhav.presentation.common.navigation.Screen
import com.vaibhav.presentation.common.theme.TextPrimary

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier.size(width = 350.dp, height = 220.dp)
            )

            Text(
                text = stringResource(id = R.string.choose_play_mode),
                color = TextPrimary,
                style = MaterialTheme.typography.h1
            )

            Spacer(
                modifier = Modifier.size(16.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Screen.ChooseUserNameScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.multi_player_online),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.size(8.dp)
            )

            Button(
                onClick = {
                    navController.navigate(Screen.EnterUserNameScreen.route)
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.surface
                ),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .width(200.dp)
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.multi_player_offline),
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(
                modifier = Modifier.size(60.dp)
            )

            StandardSettingsIcon()
        }
    }
}