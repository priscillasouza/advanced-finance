package com.advancedfinance.core.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.advancedfinance.core.Inflate
import org.koin.androidx.viewmodel.ext.android.viewModelForClass
import kotlin.reflect.KClass

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(
    private val inflate: Inflate<VB>,
    viewModeClass: KClass<VM>,
) : Fragment() {

    protected lateinit var viewBinding: VB
    protected val viewModel by viewModelForClass(viewModeClass)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = inflate.invoke(inflater, container, false)

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView(savedInstanceState)
    }

    fun bindOnBackPressed(enable: Boolean = true) {
        with(activity as? BaseActivity) {
            this?.enableBackButton = enable
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveInstanceStateAllTextsInScreen(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        restoreInstanceStateAllTextsInScreen(savedInstanceState)
    }

    /**
     * Method must be implemented by calling other methods that configure
     * resources for view like observables and listeners
     */
    abstract fun prepareView(savedInstanceState: Bundle?)

    private fun saveInstanceStateAllTextsInScreen(outState: Bundle) {
        viewBinding.root.let { root ->
            val countViews = (root as ViewGroup).childCount
            for (i in 0..countViews) {
                root.getChildAt(i).takeIf {
                    it is EditText
                }?.let {
                    val exitText = it as EditText
                    outState.putString(exitText.id.toString(), exitText.text.toString())
                }
            }
        }
    }

    private fun restoreInstanceStateAllTextsInScreen(outState: Bundle?) {
        viewBinding.root.let { root ->
            outState?.let { bundle ->
                val countViews = (root as ViewGroup).childCount
                for (i in 0..countViews) {
                    root.getChildAt(i).takeIf {
                        it is EditText
                    }?.let {
                        val exitText = it as EditText
                        val text = bundle.getString(exitText.id.toString(), "")
                        exitText.setText(text)
                    }
                }
            }
        }
    }
}