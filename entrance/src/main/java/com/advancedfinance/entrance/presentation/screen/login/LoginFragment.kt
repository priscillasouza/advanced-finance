package com.advancedfinance.entrance.presentation.screen.login

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.advancedfinance.core.platform.BaseFragment
import com.advancedfinance.entrance.BuildConfig
import com.advancedfinance.entrance.databinding.EntranceFragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<EntranceFragmentLoginBinding, LoginViewModel>(
    EntranceFragmentLoginBinding::inflate,
    LoginViewModel::class
)  {

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
                        Snackbar.make(viewBinding.root, "No ID token!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Snackbar.make(
                            viewBinding.root,
                            "A caixa de diálogo de um toque foi fechada.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        Snackbar.make(
                            viewBinding.root,
                            "Login com um toque encontrou um erro de rede.",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                    else -> {
                        Snackbar.make(
                            viewBinding.root,
                            "Não foi possível obter a credencial do resultado.\" +\n" +
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
                    .setServerClientId(BuildConfig.GOOGLE_API_OAUTH)
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
                                    // Shouldn't happen.
                                    Log.d("login", "No ID token or password!")
                                }
                            }
                        }

                    } catch (e: ApiException) {
                        e.printStackTrace()
                    }
                }
            }
    }
}