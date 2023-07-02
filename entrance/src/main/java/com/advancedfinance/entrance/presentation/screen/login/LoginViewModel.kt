package com.advancedfinance.entrance.presentation.screen.login

import androidx.biometric.BiometricManager
import com.advancedfinance.core.platform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(
    private val nomeString: String
) : BaseViewModel() {

    private val stateFlowBiometricMutable = MutableStateFlow<StateBiometric>(StateBiometric.Loading)
    val stateFlowBiometric: StateFlow<StateBiometric> = stateFlowBiometricMutable

    fun checkStateBiometric(state: Int) {
        when (state) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                stateFlowBiometricMutable.value = StateBiometric.BiometricSuccess
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                stateFlowBiometricMutable.value = StateBiometric.BiometricErrorNoHardware
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                stateFlowBiometricMutable.value = StateBiometric.BiometricErrorNoneEnrolled
            }
        }
    }

    sealed class StateBiometric {
        object Loading : StateBiometric()
        object BiometricSuccess : StateBiometric()
        object BiometricErrorNoHardware : StateBiometric()
        object BiometricErrorNoneEnrolled : StateBiometric()
    }
}