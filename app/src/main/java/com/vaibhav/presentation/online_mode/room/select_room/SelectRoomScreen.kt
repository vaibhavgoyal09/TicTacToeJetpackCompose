package com.vaibhav.presentation.online_mode.room.select_room

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.material.icons.sharp.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vaibhav.R
import com.vaibhav.core.models.Room
import com.vaibhav.presentation.common.components.StandardTextField
import com.vaibhav.presentation.common.theme.customFonts
import com.vaibhav.presentation.common.util.Error
import com.vaibhav.util.Constants
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun SelectRoomScreen(
    navController: NavController,
    userName: String,
    scaffoldState: ScaffoldState,
    viewModel: SelectRoomViewModel = getViewModel()
) {

    val focusManager = LocalFocusManager.current
    val searchFieldState = viewModel.searchFieldState.value

    val roomEventState = viewModel.roomsEventState.value

    LaunchedEffect(key1 = true) {
        viewModel.selectRoomEvent.collectLatest { event ->
            when (event) {
                is SelectRoomOutputEvent.Navigate -> {
                    navController.navigate(event.route)
                }
                is SelectRoomOutputEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        event.message
                    )
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StandardTextField(
                    imeAction = ImeAction.Search,
                    modifier = Modifier
                        .fillMaxWidth(0.87f)
                        .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                    hintText = stringResource(id = R.string.search_for_rooms),
                    maxLength = Constants.MAX_ROOM_NAME_CHAR_COUNT,
                    onImeAction = {
                        viewModel.onEvent(SelectRoomInputEvent.Search)
                        focusManager.clearFocus()
                    },
                    onValueChange = {
                        viewModel.onEvent(SelectRoomInputEvent.EnteredRoomName(it))
                    },
                    text = searchFieldState.text,
                    leadingIcon = Icons.Sharp.Search
                )

                IconButton(
                    modifier = Modifier
                        .size(42.dp),
                    onClick = {
                        viewModel.onEvent(SelectRoomInputEvent.Refresh)
                        focusManager.clearFocus()
                    }
                ) {
                    Icon(
                        Icons.Sharp.Refresh,
                        contentDescription = stringResource(id = R.string.refresh),
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            AnimatedVisibility(
                visible = roomEventState.rooms != null && roomEventState.rooms.isNotEmpty(),
                enter = expandIn(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ),
                exit = shrinkOut(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                )
            ) {
                if (roomEventState.rooms != null) {
                    RoomsListComposable(rooms = roomEventState.rooms) {
                        viewModel.onEvent(
                            SelectRoomInputEvent.JoinRoom(
                                userName = userName,
                                roomName = it.name
                            )
                        )
                    }
                }
            }

            AnimatedVisibility(
                visible = roomEventState.isLoading,
                enter = expandIn(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ),
                exit = shrinkOut(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                )
            ) {
                if (roomEventState.isLoading) {
                    LoadingRoomsComposable()
                }
            }

            AnimatedVisibility(
                visible = roomEventState.error != null || roomEventState.rooms?.isEmpty() == true,
                enter = expandIn(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                ),
                exit = shrinkOut(
                    animationSpec = tween(
                        durationMillis = 200
                    )
                )
            ) {
                if (roomEventState.rooms != null && roomEventState.rooms.isEmpty()) {
                    ErrorLoadingOrEmptyRoomsComposable(error = RoomEventErrors.NoRoomsFound)
                } else {
                    ErrorLoadingOrEmptyRoomsComposable(error = RoomEventErrors.NetworkError)
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 12.dp)
                .background(MaterialTheme.colors.background)
                .align(alignment = Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Or",
                color = MaterialTheme.colors.onBackground,
                fontFamily = customFonts,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            TextButton(
                onClick = {
                    viewModel.onEvent(SelectRoomInputEvent.CreateNewRoom(userName))
                }
            ) {
                Text(
                    text = "Create New",
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.h2
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun RoomsListComposable(
    rooms: List<Room>,
    onRoomClick: (Room) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp)
    ) {
        items(rooms) { room ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp , bottom = 8.dp)
            ) {
                Card(
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colors.onSurface),
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onRoomClick(room)
                    },
                    backgroundColor = MaterialTheme.colors.background
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = room.name,
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body1
                        )

                        Text(
                            text = "${room.playersCount}/2",
                            color = MaterialTheme.colors.onSurface,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorLoadingOrEmptyRoomsComposable(
    error: Error
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_sad_head),
            contentDescription = stringResource(id = R.string.error_loading_rooms),
            modifier = Modifier
                .size(width = 150.dp, height = 125.dp)
        )

        Spacer(
            modifier = Modifier.size(20.dp)
        )

        Text(
            text = when (error) {
                is RoomEventErrors.NoRoomsFound -> "No Rooms found"
                else -> "Something went wrong"
            },
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun LoadingRoomsComposable() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Loading",
            color = MaterialTheme.colors.onBackground,
            style = MaterialTheme.typography.h1
        )
    }
}