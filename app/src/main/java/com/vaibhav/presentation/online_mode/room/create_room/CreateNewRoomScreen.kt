package com.vaibhav.presentation.online_mode.room.create_room

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vaibhav.R
import com.vaibhav.presentation.common.components.StandardTextField
import com.vaibhav.presentation.common.navigation.Screen
import com.vaibhav.presentation.common.util.UiEvent
import com.vaibhav.util.Constants
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateNewRoomScreen(
    navController: NavController,
    scaffoldState: ScaffoldState,
    viewModel: CreateRoomViewModel = hiltViewModel()
) {

    val focusManager = LocalFocusManager.current
    val textFieldState = viewModel.textFieldState.value

    LaunchedEffect(key1 = 1) {
        viewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message
                    )
                }
                is UiEvent.Navigate -> {
                    navController.navigate(event.route) {
                        popUpTo(Screen.CreateNewRoomScreen.route) {
                            inclusive = true
                        }
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
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 40.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_normal_head),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 150.dp, height = 125.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )

            Spacer(
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = stringResource(id = R.string.create_new_room),
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
                text = textFieldState.text,
                labelText = stringResource(id = R.string.room_name),
                maxLength = Constants.MAX_ROOM_NAME_CHAR_COUNT,
                error = when (textFieldState.error) {
                    is RoomNameValidationErrors.Empty -> stringResource(id = R.string.room_name_empty_error)
                    is RoomNameValidationErrors.Short -> stringResource(id = R.string.short_room_name_error)
                    is RoomNameValidationErrors.Long -> stringResource(id = R.string.room_name_long_error)
                    else -> ""
                },
                onValueChange = {
                    viewModel.onEvent(CreateRoomInputEvents.EnteredRoomName(it))
                },
                onImeAction = {
                    createAndJoinRoom(viewModel, focusManager)
                },
                textColor = MaterialTheme.colors.onSurface
            )

            Spacer(
                modifier = Modifier.size(width = 0.dp, height = 16.dp)
            )

            Button(
                shape = RoundedCornerShape(15.dp),
                onClick = {
                    createAndJoinRoom(viewModel, focusManager)
                },
                modifier = Modifier
                    .wrapContentSize()
                    .align(alignment = Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    text = "Create Room",
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onPrimary,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun createAndJoinRoom(
    viewModel: CreateRoomViewModel,
    focusManager: FocusManager
) {
    viewModel.onEvent(CreateRoomInputEvents.CreateRoom)
    focusManager.clearFocus()
}