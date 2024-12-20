package com.comppot.mindsnack.notifications.presentation.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comppot.mindsnack.core.data.settings.SettingsRepository
import com.comppot.mindsnack.notifications.domain.model.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _notificationState = MutableStateFlow(NotificationState())
    val notificationState = _notificationState.asStateFlow()

    init {
        initState()
    }

    private fun initState() = viewModelScope.launch {
        val welcomeNotification = getWelcomeNotification()
        _notificationState.emit(
            _notificationState.value.copy(
                isLoading = false,
                notifications = listOf(welcomeNotification)
            )
        )
    }

    private suspend fun getWelcomeNotification(): Notification {
        val loginTimestamp = settingsRepository.loginTimestamp.first()
        return Notification(
            0,
            "\uD83D\uDC4B Вітаємо у MindSnack",
            "Дякуємо, що тестуєте цей додаток і допомагаєте нам ставати краще. Сподіваємось ви знайдете тут щось цікаве і пізнавальне для себе.",
            loginTimestamp,
            true
        )
    }
}
