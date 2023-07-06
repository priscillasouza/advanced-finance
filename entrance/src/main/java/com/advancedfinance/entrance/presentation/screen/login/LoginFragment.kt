package com.advancedfinance.entrance.presentation.screen.login

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricManager.from
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.entrance.BuildConfig.GOOGLE_API_OAUTH
import com.advancedfinance.entrance.R
import com.advancedfinance.entrance.databinding.EntranceFragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.concurrent.Executor

class LoginFragment : BaseFragment<EntranceFragmentLoginBinding, LoginViewModel>(
    EntranceFragmentLoginBinding::inflate,
    LoginViewModel::class
) {

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private var oneTapClient: SignInClient? = null
    private var signInRequest: BeginSignInRequest? = null
    private var activityResultLauncher: ActivityResultLauncher<IntentSenderRequest>? = null

    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient?.getSignInCredentialFromIntent(result.data)
                val idToken = credential?.googleIdToken
                when {
                    idToken != null -> {
                        val msg = "idToken: $idToken"
                        Snackbar.make(viewBinding.root, msg, Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        Snackbar.make(viewBinding.root,
                            getString(R.string.entrance_text_on_tap_result_no_id_token),
                            Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Snackbar.make(
                            viewBinding.root,
                            getString(R.string.entrance_text_on_tap_result_one_touch_dialog_has_been_closed),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        Snackbar.make(
                            viewBinding.root,
                            getString(R.string.entrance_text_on_tap_result_network_error),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Snackbar.make(
                            viewBinding.root,
                            getString(R.string.entrance_text_on_tap_result_unable_to_get_credential) +
                                    " (${e.localizedMessage})",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    override fun prepareView(savedInstanceState: Bundle?) {
        settingWidgetListener()
        settingOneTap()
        settingActivityResult()
        setBiometricPrompt()
        setButtonBiometric()
        settingObservable()
        setButtonLogin()
    }

    private fun settingObservable() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listViewState.collect { state ->
                    when (state) {
                        LoginViewModel.BiometricViewState.Loading -> {}
                        LoginViewModel.BiometricViewState.BiometricViewStateSuccess -> {
                            biometricPrompt.authenticate(promptInfo)
                            Toast.makeText(requireContext(),
                                getString(R.string.entrance_text_biometric_success),
                                Toast.LENGTH_SHORT).show()
                        }
                        LoginViewModel.BiometricViewState.BiometricViewStateErrorNoHardware -> {
                            Toast.makeText(requireContext(),
                                getString(R.string.entrance_text_no_biometric_feature_available),
                                Toast.LENGTH_SHORT).show()
                        }
                        LoginViewModel.BiometricViewState.BiometricViewStateErrorNoneEnrolled -> {
                            Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                            }
                            ActivityResultContracts.StartIntentSenderForResult()
                        }
                    }
                }
            }
        }
    }

    private fun settingWidgetListener() {
        activity?.let { fragmentActivity ->
            viewBinding.entranceButtonLoginGoogle.setOnClickListener {
                signInRequest?.let {
                    oneTapClient?.beginSignIn(it)
                        ?.addOnSuccessListener(fragmentActivity) { result ->
                            try {
                                val ib =
                                    IntentSenderRequest.Builder(result.pendingIntent.intentSender)
                                        .build()
                                oneTapResult.launch(ib)
                            } catch (e: IntentSender.SendIntentException) {
                                Log.e(
                                    "login",
                                    "Não foi possível iniciar a IU com um toque: ${e.localizedMessage}"
                                )
                            }
                        }
                        ?.addOnFailureListener() { e ->
                            Log.d("login", e.localizedMessage)
                        }
                }
            }
        }
    }

    private fun settingOneTap() {
        oneTapClient = activity?.let { Identity.getSignInClient(it) }
        signInRequest = BeginSignInRequest.builder()
            .setPasswordRequestOptions(
                BeginSignInRequest.PasswordRequestOptions.builder()
                    .setSupported(true)
                    .build()
            )
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(GOOGLE_API_OAUTH)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun settingActivityResult() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
                if (result.resultCode == AppCompatActivity.RESULT_OK) {
                    try {
                        oneTapClient?.let { client ->
                            val credential = client.getSignInCredentialFromIntent(result.data)
                            val idToken = credential.googleIdToken
                            val username = credential.id
                            val password = credential.password
                            when {
                                idToken != null -> {
                                    val email = credential.id
                                    Log.d("login", "Email: $email")
                                }
                                password != null -> {
                                    val password = credential.password
                                    Log.d("login", "Got password: $password")
                                }
                                else -> {
                                    Log.d("login", "Nenhum token de identificação ou senha!")
                                }
                            }
                        }

                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            }
    }

    private fun setBiometricPrompt() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt =
            BiometricPrompt(requireActivity(), executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence,
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(requireContext(),
                            "Erro na Autenticação: $errString", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult,
                    ) {
                        super.onAuthenticationSucceeded(result)
                        Toast.makeText(requireContext(),
                            getString(R.string.entrance_text_biometric_autentication_success),
                            Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(requireContext(),
                            getString(R.string.entrance_text_biometric_autentication_failed),
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.entrance_text_title_prompt_biometric))
            .setSubtitle(getString(R.string.entrance_text_subtitle_prompt_biometric))
            .setNegativeButtonText(getString(R.string.entrance_text_set_negative_button_prompt_biometric))
            .build()
    }

    private fun setButtonBiometric() {
        viewBinding.entranceButtonLogin.setOnClickListener {
            val state = from(requireContext()).canAuthenticate(BIOMETRIC_WEAK)
            viewModel.checkStateBiometric(state)

        }
    }

    private fun setButtonLogin() {
        viewBinding.entranceButtonLogin.setOnClickListener {
            fromLoginToAccountList()
        }
    }

    private fun fromLoginToAccountList() {
        findNavController().navigate(LoginFragmentDirections.entranceActionEntranceLoginfragmentToEntranceDraweractivity())
    }
}