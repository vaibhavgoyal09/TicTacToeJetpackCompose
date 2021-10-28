package com.vaibhav.presentation.offline_mode.username

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
import androidx.compose.ui.focus.FocusDirection
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
import com.vaibhav.util.Constants
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@Composable
fun EnterUserNameScreen(
    navController: NavController,
    viewModel: EnterPlayerNamesViewModel = getViewModel()
) {

    val focusManager = LocalFocusManager.current
    val player1NameState = viewModel.player1NameState.value
    val player2FieldState = viewModel.player2NameState.value

    LaunchedEffect(key1 = "success") {
        viewModel.userNameValidationEvent.collectLatest { event ->
            when (event) {
                is EnterPlayerNamesValidationEvent.Success -> {
                    delay(500L)

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
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
                text = stringResource(id = R.string.enter_player_names),
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
                imeAction = ImeAction.Next,
                text = player1NameState.text,
                labelText = stringResource(id = R.string.player_1_name),
                maxLength = Constants.MAX_USERNAME_CHAR_COUNT,
                error = when (player1NameState.error) {
                    is UserNameValidationErrors.Empty -> stringResource(id = R.string.empty_username_error)
                    is UserNameValidationErrors.TooShort -> stringResource(id = R.string.short_username_error)
                    is UserNameValidationErrors.ContainsUnAllowedCharacters -> stringResource(id = R.string.invalid_username_error)
                    else -> ""
                },
                onValueChange = {
                    viewModel.onEvent(EnterPlayerNamesEvent.EnteredPlayer1Name(it))
                },
                onImeAction = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
                textColor = MaterialTheme.colors.onSurface
            )

            Spacer(
                modifier = Modifier.size(width = 0.dp, height = 8.dp)
            )

            StandardTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                imeAction = ImeAction.Done,
                text = player2FieldState.text,
                labelText = stringResource(id = R.string.player_2_name),
                maxLength = Constants.MAX_USERNAME_CHAR_COUNT,
                error = when (player2FieldState.error) {
                    is UserNameValidationErrors.Empty -> stringResource(id = R.string.empty_username_error)
                    is UserNameValidationErrors.TooShort -> stringResource(id = R.string.short_username_error)
                    is UserNameValidationErrors.ContainsUnAllowedCharacters -> stringResource(id = R.string.invalid_username_error)
                    else -> ""
                },
                onValueChange = {
                    viewModel.onEvent(EnterPlayerNamesEvent.EnteredPlayer2Name(it))
                },
                onImeAction = {
                    viewModel.onEvent(EnterPlayerNamesEvent.ValidateUserNames)
                    focusManager.clearFocus(force = true)
                },
                textColor = MaterialTheme.colors.onSurface
            )

            Spacer(
                modifier = Modifier.size(width = 0.dp, height = 16.dp)
            )

            Button(
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    viewModel.onEvent(EnterPlayerNamesEvent.ValidateUserNames)
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