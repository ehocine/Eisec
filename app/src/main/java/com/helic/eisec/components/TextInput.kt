package com.helic.eisec.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.helic.eisec.ui.theme.AppTheme

@Composable
fun EisecLabelView(title: String) {
    Text(
        text = title,
        style = AppTheme.typography.caption,
        textAlign = TextAlign.Start,
        color = AppTheme.colors.text
    )
}

@Stable
@Composable
fun EisecInputTextField(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onValueChanged: (String) -> Unit
) {
    var textState by rememberSaveable { mutableStateOf("") }
    var errorState by rememberSaveable { mutableStateOf(false) }
    var errorMessage by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(horizontalAlignment = Alignment.Start) {

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingXL
                ),
            value = value,
            readOnly = readOnly,
            enabled = enabled,
            onValueChange = {
                textState = it
                when (textState.isEmpty()) {
                    true -> {
                        errorState = true
                        errorMessage = "$title should not be empty"
                    }
                    false -> {
                        errorState = false
                        errorMessage = ""
                    }
                }
                onValueChanged(it)
            },

            label = { EisecLabelView(title = title) },
            textStyle = AppTheme.typography.body,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = {

                    // Focus to next input
                    focusManager.moveFocus(FocusDirection.Next)

                    // validate
                    when (textState.isEmpty()) {
                        true -> {
                            errorState = true
                            errorMessage = "$title should not be empty"
                        }
                        false -> {
                            errorState = false
                            errorMessage = ""
                        }
                    }
                }
            ),
            isError = errorState
        )

        if (errorState) {
            Text(
                errorMessage,
                style = AppTheme.typography.caption,
                color = AppTheme.colors.error,
                modifier = modifier.padding(
                    top = AppTheme.dimensions.paddingLarge,
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingLarge
                )
            )
        }
    }
}

@Stable
@Composable
fun EisecInputTextFieldWithoutHint(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onValueChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(horizontalAlignment = Alignment.Start) {

        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(
                    start = AppTheme.dimensions.paddingXL,
                    end = AppTheme.dimensions.paddingXL
                ),
            value = value,
            onValueChange = {
                onValueChanged(it)
            },

            label = { EisecLabelView(title = title) },
            textStyle = AppTheme.typography.body,
            colors = textFieldColors(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onNext = {

                    // Focus to next input
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
        )
    }
}

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = AppTheme.colors.text,
    focusedLabelColor = AppTheme.colors.text,
    focusedIndicatorColor = AppTheme.colors.text,
    unfocusedIndicatorColor = AppTheme.colors.card,
    cursorColor = AppTheme.colors.text,
    backgroundColor = AppTheme.colors.card,
    placeholderColor = AppTheme.colors.card,
    disabledPlaceholderColor = AppTheme.colors.card
)