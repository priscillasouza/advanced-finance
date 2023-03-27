package com.advancedfinance.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModelForClass
import kotlin.reflect.KClass

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<T : ViewBinding, V : ViewModel>(
    private val inflate: Inflate<T>,
    viewModeClass: KClass<V>
) : Fragment() {

    val TAG = javaClass.simpleName

    protected var viewBinding: T? = null
    protected val viewModel by viewModelForClass(viewModeClass)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = inflate.invoke(inflater, container, false)

        return viewBinding?.root
    }

    fun bindOnBackPressed(enable: Boolean = true) {
        with(activity as? BaseActivity) {
            this?.enableBackButton = enable
        }
    }

    //TODO: Extrarir
   /* fun showAlert(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButton: DialogFragmentBox.OnClickListener? = null,
        negativeButton: DialogFragmentBox.OnClickListener? = null
    ) {
        (activity as BaseActivity).showAlert(
            title,
            message,
            isCancelable,
            positiveButton,
            negativeButton
        )
    }*/

    //TODO: Extrarir
   /* fun hideAlert() {
        (activity as BaseActivity).hideAlert()
    }*/


    //TODO: revisar apos estudos de viewmodel e lifecycler
     override fun onDestroy() {
         //getViewModel().onDestroyView()
         super.onDestroy()

        viewBinding = null
     }
}

