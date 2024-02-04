package com.example.customalert

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.customalert.ui.theme.AlertCyan


@Composable
fun <T> CustomAlert(
    title: String, message: String,
    actionText: String,
    data: T?,
    showAlert: MutableState<Boolean>,
    actionWithValue: ((T) -> Unit)?,
    action: (() -> Unit)?,
) {
    val animationState = remember { MutableTransitionState(false) }

    /// Run only once
    LaunchedEffect(Unit) {
        animationState.targetState = showAlert.value
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AnimatedVisibility(
            animationState,
            enter = slideInHorizontally(
                animationSpec = tween(200)
            ),
            exit = slideOutHorizontally(
                animationSpec = tween(200),
                targetOffsetX = { fullWidth -> fullWidth }
            )
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp),
                shape = RoundedCornerShape(35.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        title,
                        modifier = Modifier.fillMaxWidth(),
                        color = AlertCyan,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 19.sp,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )

                    /// Buttons
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = {
                                animationState.targetState = false
                            },
                            modifier = Modifier
                                .background(
                                    MaterialTheme.colorScheme.surface,
                                    shape = RoundedCornerShape(35.dp)
                                )
                                .height(55.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Transparent,
                            ),
                        ) {
                            Text(
                                text = "Cancel",
                                modifier = Modifier,
                                color = AlertCyan,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                        Button(
                            onClick = {
                                animationState.targetState = false

                                if (actionWithValue != null) {
                                    data?.let {
                                        actionWithValue(data)
                                    }
                                } else if (action != null) {
                                    action()
                                }
                            },
                            modifier = Modifier
                                .background(
                                    AlertCyan,
                                    shape = RoundedCornerShape(35.dp)
                                )
                                .height(55.dp)
                                .weight(1f),
                            shape = RoundedCornerShape(0.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.White,
                                disabledContainerColor = Color.Transparent,
                            ),
                        ) {
                            Text(
                                text = actionText,
                                modifier = Modifier,
                                fontWeight = FontWeight.SemiBold,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }

        /// Current state changes after animation finishes
        LaunchedEffect(animationState.currentState) {
            /// Animation is completed when current state is the same as target state.
            /// And currentState is false
            if (!animationState.currentState && animationState.targetState == animationState.currentState) {
                showAlert.value = false
            }
        }
    }
}