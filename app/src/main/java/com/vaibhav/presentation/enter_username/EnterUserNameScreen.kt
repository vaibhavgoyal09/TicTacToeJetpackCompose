package com.vaibhav.presentation.enter_username

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.vaibhav.presentation.ui.DarkBlue
import com.vaibhav.presentation.ui.Secondary
import com.vaibhav.presentation.ui.TextSecondary
import com.vaibhav.presentation.ui.White
import com.vaibhav.util.Constants.MAX_USERNAME_CHAR_COUNT
import org.koin.androidx.compose.getViewModel

@Composable
fun EnterUserNameScreen(
    navController: NavController,
    viewModel: EnterUserNameViewModel = getViewModel()
) {

    val userNameState = viewModel.userNameFieldState.value
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 20.dp, end = 20.dp, top = 40.dp, bottom = 20.dp),
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tic_tac_toe),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(width = 220.dp, height = 250.dp)
                )

                Spacer(
                    modifier = Modifier.size(width = 0.dp, height = 40.dp)
                )

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
                    focusedIndicatorColor = White,
                    unfocusedIndicatorColor = White,
                    maxLength = MAX_USERNAME_CHAR_COUNT,
                    style = MaterialTheme.typography.body1,
                    backgroundColor = DarkBlue,
                    onValueChange = {
                        viewModel.onEvent(UserNameEvent.EnteredUserName(it))
                    },
                    onImeAction = {
                        viewModel.onEvent(UserNameEvent.RegisterUserName)
                        focusManager.clearFocus()
                    },
                    textColor = White,
                    cursorColor = Secondary,
                    hintTextColor = TextSecondary
                )

                Spacer(
                    modifier = Modifier.size(width = 0.dp, height = 16.dp)
                )

                OutlinedButton(
                    border = BorderStroke(
                        width = 1.dp,
                        color = White
                    ),
                    onClick = {
                        viewModel.onEvent(UserNameEvent.RegisterUserName)
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .align(alignment = Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    )
                ) {
                    Text(
                        text = "Done",
                        style = MaterialTheme.typography.h2,
                        color = White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}