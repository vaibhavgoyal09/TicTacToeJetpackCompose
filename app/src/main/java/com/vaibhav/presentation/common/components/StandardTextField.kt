package com.vaibhav.presentation.common.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vaibhav.R
import com.vaibhav.presentation.common.theme.TextSecondary

@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = MaterialTheme.colors.onSurface,
    maxLength: Int = 400,
    error: String = "",
    style: TextStyle = MaterialTheme.typography.body1,
    labelText: String? = null,
    hintText: String? = null,
    hintTextColor: Color = TextSecondary,
    backgroundColor: Color = MaterialTheme.colors.surface,
    focusedIndicatorColor: Color = MaterialTheme.colors.primary,
    unfocusedIndicatorColor: Color = MaterialTheme.colors.secondary,
    labelUnfocusedColor: Color = TextSecondary,
    labelFocusedColor: Color = MaterialTheme.colors.onBackground,
    cursorColor: Color = MaterialTheme.colors.secondary,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    leadingIcon: ImageVector? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Done,
    isPasswordToggleDisplayed: Boolean = keyboardType == KeyboardType.Password,
    isPasswordVisible: Boolean = false,
    onPasswordToggleClick: (Boolean) -> Unit = {},
    onImeAction: () -> Unit = {},
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                if (it.length <= maxLength) {
                    onValueChange(it)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = textColor,
                backgroundColor = backgroundColor,
                unfocusedIndicatorColor = unfocusedIndicatorColor,
                focusedIndicatorColor = focusedIndicatorColor,
                unfocusedLabelColor = labelUnfocusedColor,
                focusedLabelColor = labelFocusedColor,
                cursorColor = cursorColor
            ),
            maxLines = maxLines,
            textStyle = style,
            label = if (labelText != null) {
                val labelTextComposable: @Composable () -> Unit = {
                    Text(
                        text = labelText,
                        style = MaterialTheme.typography.body1
                    )
                }
                labelTextComposable
            } else {
                null
            },
            placeholder = if (hintText != null) {
                val placeholderText: @Composable () -> Unit = {
                    Text(
                        text = hintText,
                        color = hintTextColor,
                        style = MaterialTheme.typography.body1
                    )
                }
                placeholderText
            } else {
                null
            },
            isError = error != "",
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onAny = {
                    onImeAction()
                }
            ),
            visualTransformation = if (!isPasswordVisible && isPasswordToggleDisplayed) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            singleLine = singleLine,
            leadingIcon = if (leadingIcon != null) {
                val icon: @Composable () -> Unit = {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground,
                        modifier = Modifier.size(25.dp)
                    )
                }
                icon
            } else null,
            trailingIcon = if (isPasswordToggleDisplayed) {
                val icon: @Composable () -> Unit = {
                    IconButton(
                        onClick = {
                            onPasswordToggleClick(!isPasswordVisible)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = if (isPasswordVisible) {
                                Icons.Filled.VisibilityOff
                            } else {
                                Icons.Filled.Visibility
                            },
                            tint = Color.White,
                            contentDescription = if (isPasswordVisible) {
                                stringResource(id = R.string.password_visible_content_description)
                            } else {
                                stringResource(id = R.string.password_hidden_content_description)
                            }
                        )
                    }
                }
                icon
            } else null,
            modifier = Modifier
                .fillMaxWidth()
        )
        if (error.isNotEmpty()) {
            Text(
                text = error,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}