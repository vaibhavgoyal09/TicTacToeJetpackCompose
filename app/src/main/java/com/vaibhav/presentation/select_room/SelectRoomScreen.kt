package com.vaibhav.presentation.select_room

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vaibhav.R
import com.vaibhav.presentation.common.components.StandardTextField
import com.vaibhav.util.Constants
import org.koin.androidx.compose.getViewModel

@Composable
fun SelectRoomScreen(
    navController: NavController,
    userName: String,
    viewModel: SelectRoomViewModel = getViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, start = 8.dp, end = 8.dp, bottom = 12.dp)
                .align(alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            StandardTextField(
                imeAction = ImeAction.Search,
                modifier = Modifier
                    .fillMaxWidth(.85f)
                    .padding(start = 8.dp, end = 8.dp, top = 4.dp, bottom = 4.dp),
                labelText = stringResource(id = R.string.search_for_rooms),
                maxLength = Constants.MAX_ROOM_NAME_CHAR_COUNT,
                onImeAction = {

                },
                onValueChange = {

                }
            )

            IconButton(
                modifier = Modifier
                    .size(45.dp),
                onClick = {

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
    }
}