package com.vaibhav.presentation.online_mode.username

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.R
import com.vaibhav.presentation.common.components.StandardTextField
import com.vaibhav.presentation.common.util.UserNameValidationErrors
import com.vaibhav.presentation.navigation.Screen
import com.vaibhav.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun ChooseUserNameScreen(
    navController: NavController,
    viewModel: ChooseUserNameViewModel = getViewModel()
) {

    val userNameState = viewModel.userNameFieldState.value
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = true) {
        viewModel.userNameValidationEvent.collectLatest { event ->
            when (event) {
                is UserNameValidationEvent.Success -> {
                    delay(500L)
                    navController.navigate(Screen.SelectRoomScreen.route + "/${event.userName}") {
                        popUpTo(Screen.EnterUserNameScreen.route) { inclusive = true }
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.back_circle),
            contentDescription = null,
            modifier = Modifier
                .size(width = 270.dp, height = 190.dp)
                .align(alignment = Alignment.TopEnd),
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.back_cross),
            contentDescription = null,
            modifier = Modifier
                .size(width = 195.dp, height = 195.dp)
                .align(alignment = Alignment.BottomStart),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.choose_a_username),
                color = MaterialTheme.colors.onBackground,
                style = MaterialTheme.typography.h1,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )

            Spacer(
                modifier = Modifier.size(width = 0.dp, height = 8.dp)
            )

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                imeAction = ImeAction.Done,
                text = userNameState.text,
                labelText = stringResource(id = R.string.username),
                maxLength = Constants.MAX_USERNAME_CHAR_COUNT,
                error = when (userNameState.error) {
                    is UserNameValidationErrors.Empty -> stringResource(id = R.string.empty_username_error)
                    is UserNameValidationErrors.TooShort -> stringResource(id = R.string.short_username_error)
                    is UserNameValidationErrors.ContainsUnAllowedCharacters -> stringResource(id = R.string.invalid_username_error)
                    else -> ""
                },
                onValueChange = {
                    viewModel.onEvent(UserNameEvent.EnteredUserName(it))
                },
                onImeAction = {
                    viewModel.onEvent(UserNameEvent.ValidateUserName)
                    focusManager.clearFocus()
                },
                textColor = MaterialTheme.colors.onSurface
            )

            Spacer(
                modifier = Modifier.size(width = 0.dp, height = 16.dp)
            )

            Button(
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    viewModel.onEvent(UserNameEvent.ValidateUserName)
                    focusManager.clearFocus()
                },
                modifier = Modifier
                    .wrapContentSize()
                    .align(alignment = Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    text = "Done",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}