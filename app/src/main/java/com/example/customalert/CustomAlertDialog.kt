package com.example.customalert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties


@Composable
fun <T> CustomAlertDialog(
    title: String,
    message: String,
    actionText: String,
    data: T?,
    showAlert: MutableState<Boolean>,
    action: (T) -> Unit
) {
    Dialog(
        onDismissRequest = { showAlert.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CustomAlert(
            title, message, actionText, data, showAlert,
            actionWithValue = action, action = null
        )
    }
}

@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    actionText: String,
    showAlert: MutableState<Boolean>,
    action: () -> Unit
) {
    Dialog(
        onDismissRequest = { showAlert.value = false },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        CustomAlert(
            title, message, actionText, null, showAlert,
            actionWithValue = null, action = action
        )
    }
}