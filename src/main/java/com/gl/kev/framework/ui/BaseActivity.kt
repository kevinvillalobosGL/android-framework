package com.gl.kev.framework.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import java.lang.reflect.ParameterizedType


/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/12/2019
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseActivity<T : ViewDataBinding, M : AndroidViewModel> : AppCompatActivity() {

    protected val DEFAULT_BINDING_VARIABLE = 0

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: M

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
        if (types.size > 0) {
            mViewModel = ViewModelProviders.of(this).get(types[if (types.size > 1) 1 else 0] as Class<M>)
        }
    }

    /**
     * View Model Getter.
     *
     * @return Instance of <M>, consequent to View Model.
     */
    fun getViewModel(): M {
        return mViewModel
    }

    /**
     * Binding Getter
     *
     * @return Instance of <T>, as part of the Binding view.
     */
    fun getBinding(): T {
        return mBinding
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

}