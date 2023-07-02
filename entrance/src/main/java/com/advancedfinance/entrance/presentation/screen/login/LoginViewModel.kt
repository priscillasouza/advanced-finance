package com.advancedfinance.entrance.presentation.screen.login

import androidx.biometric.BiometricManager
import com.advancedfinance.core.platform.BaseViewModel
import com.advancedfinance.entrance.presentation.screen.login.LoginViewModel.BiometricViewAction
import com.advancedfinance.entrance.presentation.screen.login.LoginViewModel.BiometricViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : BaseViewModel<BiometricViewState, BiometricViewAction>() {

    private val stateFlowBiometricMutable = MutableStateFlow<BiometricViewState>(BiometricViewState.Loading)
    override val listViewState: StateFlow<BiometricViewState> = stateFlowBiometricMutable

    override fun dispatchViewAction(viewAction: BiometricViewAction) {
        when(viewAction) {
            is BiometricViewAction.CheckBiometricViewState -> checkStateBiometric(state = viewAction.state)
        }
    }

    fun checkStateBiometric(state: Int) {
        when (state) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                stateFlowBiometricMutable.value = BiometricViewState.BiometricViewStateSuccess
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                stateFlowBiometricMutable.value = BiometricViewState.BiometricViewStateErrorNoHardware
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                stateFlowBiometricMutable.value = BiometricViewState.BiometricViewStateErrorNoneEnrolled
            }
        }
    }

    sealed class BiometricViewAction {
        class CheckBiometricViewState(val state: Int) : BiometricViewAction()
    }

    sealed class BiometricViewState {
        object Loading : BiometricViewState()
        object BiometricViewStateSuccess : BiometricViewState()
        object BiometricViewStateErrorNoHardware : BiometricViewState()
        object BiometricViewStateErrorNoneEnrolled : BiometricViewState()
    }
}