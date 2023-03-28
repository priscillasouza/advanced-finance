package com.advancedfinance.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.advancedfinance.core.Inflate
import org.koin.androidx.viewmodel.ext.android.viewModelForClass
import kotlin.reflect.KClass

abstract class BaseFragment<T : ViewBinding, V : ViewModel>(
    private val inflate: Inflate<T>,
    viewModeClass: KClass<V>
) : Fragment() {

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
}