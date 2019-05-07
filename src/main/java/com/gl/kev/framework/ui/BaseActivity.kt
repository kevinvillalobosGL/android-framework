package com.gl.kev.framework.ui

import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.gl.kev.framework.R
import java.lang.reflect.ParameterizedType


/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/12/2019
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseActivity<T : ViewDataBinding, M : AndroidViewModel> : AppCompatActivity() {

    companion object {
        const val DEFAULT_BINDING_VARIABLE = 0
    }

    lateinit var mBinding: T
    lateinit var mViewModel: M
    var mDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        initBinding()
        initViews(savedInstanceState)
    }

    /**
     * Initializes the Binding from the Layout.
     */
    protected fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, getLayout())
        if (DEFAULT_BINDING_VARIABLE != getBindingVariable()) {
            mBinding.setVariable(getBindingVariable(), mViewModel)
        }
        mBinding.executePendingBindings()
    }

    /**
     * Gets the View Model from the View Model Providers using the <M> class argument.
     */
    protected fun initViewModel() {
        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        if (types.isNotEmpty()) {
            mViewModel = ViewModelProviders.of(this).get(types[if (types.size > 1) 1 else 0] as Class<M>)
        }
    }

    /**
     * Called in the #BaseActivity[.onCreate]
     */
    protected abstract fun initViews(savedInstanceState: Bundle?)

    /**
     * Override for set the Layout
     *
     * @return Layout Resource ID
     */
    @LayoutRes
    protected abstract fun getLayout(): Int

    /**
     * Override for set binding variable
     *
     * @return Binding Variable ID
     */
    @IdRes
    protected abstract fun getBindingVariable(): Int

    //Helper Methods
    /**
     * Checks if there's Internet Available
     *
     * @return Boolean
     */
    fun isNetworkConnected(): Boolean {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork.isConnected
    }

    /**
     * Shows an Alert Dialog with a simple String Message with no Click Listener and Ok as Button
     *
     * @param msg String
     */
    fun showGeneralAlertDialog(msg: String) {
        showGeneralAlertDialog(msg, null)
    }

    /**
     * Shows an Alert Dialog with a simple String Message with Click Listener and Ok as Button
     *
     * @param msg             String
     * @param onClickListener Click Listener Action
     */
    fun showGeneralAlertDialog(msg: String, onClickListener: DialogInterface.OnClickListener?) {
        showGeneralAlertDialog(msg, getString(android.R.string.ok), onClickListener)
    }

    /**
     * Shows an Alert Dialog with a simple String Message with Click Listener and Custom Button Text
     *
     * @param msg String
     * @param positiveString String
     * @param onClickListener Click Listener Action
     */
    fun showGeneralAlertDialog(msg: String, positiveString: String, onClickListener: DialogInterface.OnClickListener?) {
        if (mDialog == null) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(msg)
                .setTitle(R.string.app_name)
                .setCancelable(false)
                .setPositiveButton(positiveString, onClickListener)
                .setOnDismissListener { mDialog = null }
            mDialog = builder.create()
            mDialog?.show()
        }
    }

}