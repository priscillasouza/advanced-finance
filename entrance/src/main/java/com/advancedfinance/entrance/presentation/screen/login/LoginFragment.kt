package com.advancedfinance.entrance.presentation.screen.login

import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.advancedfinance.entrance.BuildConfig
import com.advancedfinance.entrance.databinding.EntranceFragmentLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: EntranceFragmentLoginBinding

    private lateinit var oneTapClient: SignInClient
    private lateinit var signInRequest: BeginSignInRequest
    private val oneTapResult =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                when {
                    idToken != null -> {
                        val msg = "idToken: $idToken"
                        Snackbar.make(binding.root, msg, Snackbar.LENGTH_SHORT).show()
                        //findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    }
                    else -> {
                        Snackbar.make(binding.root, "No ID token!", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } catch (e: ApiException) {
                when (e.statusCode) {
                    CommonStatusCodes.CANCELED -> {
                        Snackbar.make(binding.root,
                            "A caixa de diálogo de um toque foi fechada.",
                            Snackbar.LENGTH_SHORT).show()
                    }
                    CommonStatusCodes.NETWORK_ERROR -> {
                        Snackbar.make(binding.root,
                            "Login com um toque encontrou um erro de rede.",
                            Snackbar.LENGTH_SHORT).show()
                    }
                    else -> {
                        Snackbar.make(binding.root,
                            "Não foi possível obter a credencial do resultado.\" +\n" +
                                    " (${e.localizedMessage})",
                            Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = EntranceFragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnTapWithGoogle()
        setClickButtonSignInGoogle()
    }

    private fun setOnTapWithGoogle() {
        oneTapClient = Identity.getSignInClient(requireActivity())
        signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(BuildConfig.GOOGLE_API_OAUTH)
                    .setFilterByAuthorizedAccounts(false)
                    .build())
            .setAutoSelectEnabled(true)
            .build()
    }

    private fun displaySignIn() {
        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener(requireActivity()) { result ->
                try {
                    val ib = IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                    oneTapResult.launch(ib)
                } catch (e: IntentSender.SendIntentException) {
                    Log.e("btn click", "Não foi possível iniciar a IU com um toque: ${e.localizedMessage}")
                }
            }
            .addOnFailureListener(requireActivity()) { e ->
                Log.d("btn click", e.localizedMessage!!)
            }
    }

    private fun setClickButtonSignInGoogle() {
        binding.buttonLoginGoogle.setOnClickListener {
            displaySignIn()
        }
    }
}