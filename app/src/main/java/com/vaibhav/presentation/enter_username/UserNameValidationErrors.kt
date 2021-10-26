package com.vaibhav.presentation.enter_username

import com.vaibhav.presentation.common.util.Error

sealed class UserNameValidationErrors: Error() {

    object Empty: UserNameValidationErrors()

    object TooShort: UserNameValidationErrors()

    object ContainsUnAllowedCharacters: UserNameValidationErrors()
}
