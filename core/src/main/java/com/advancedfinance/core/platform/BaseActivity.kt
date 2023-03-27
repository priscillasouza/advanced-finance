package com.advancedfinance.core.platform

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

@IntDef(
    BaseActivity.LENGTH_SHORT,
    BaseActivity.LENGTH_LONG
)
@IntRange(from = 1)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
annotation class Duration

abstract class BaseActivity : AppCompatActivity() {

    val TAG: String = javaClass.simpleName

    private var toolbar: MaterialToolbar? = null
    var enableBackButton = true
    private val snackbar: Snackbar by lazy {
        Snackbar.make(
            findViewById(android.R.id.content), "",
            Snackbar.LENGTH_INDEFINITE
        )
    }
    //private var dialogFragmen: DialogFragmentBox? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    @IdRes
    abstract fun getNavHostFragmentRes(): Int

    abstract fun getToolbar(): MaterialToolbar?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        configureToolbarWithNavigation()
    }

    override fun onBackPressed() {
        if (enableBackButton) super.onBackPressed()
    }

   /* fun showAlert(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButton: DialogFragmentBox.OnClickListener? = null,
        negativeButton: DialogFragmentBox.OnClickListener? = null
    ) {
        instanceDialog(
            title,
            message,
            isCancelable,
            positiveButton,
            negativeButton
        )
    }

    fun hideAlert() {
        dialogFragmen?.run {
            dismiss()

        }
    }

    private fun instanceDialog(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButton: DialogFragmentBox.OnClickListener? = null,
        negativeButton: DialogFragmentBox.OnClickListener? = null
    ) {
        dialogFragmen = DialogFragmentBox.newInstance(
            title,
            message,
            isCancelable,
            positiveButton = positiveButton,
            negativeButton = negativeButton
        )

        dialogFragmen?.show(supportFragmentManager, "Teste")
    }*/

    private val navControler: NavController by lazy {
        findNavController(getNavHostFragmentRes())
    }

    private fun configureToolbarWithNavigation() {
        toolbar = getToolbar()
        toolbar?.let {
            setSupportActionBar(toolbar)
            val appBarConfiguration = AppBarConfiguration(navControler.graph)

            setupActionBarWithNavController(navControler, appBarConfiguration)
        }
    }

    companion object {
        const val LENGTH_LONG = 1
        const val LENGTH_SHORT = 0
    }

}