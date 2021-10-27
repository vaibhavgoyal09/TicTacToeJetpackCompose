package com.vaibhav.presentation.common.util

sealed class UserNameValidationErrors: Error() {

    object Empty: UserNameValidationErrors()

    object TooShort: UserNameValidationErrors()

    object ContainsUnAllowedCharacters: UserNameValidationErrors()
}
