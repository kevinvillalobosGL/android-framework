package com.gl.kev.framework.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import com.gl.kev.framework.utils.ViewUtils
import java.lang.reflect.ParameterizedType


/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/12/2019
 */
@Suppress("UNCHECKED_CAST", "unused")
abstract class BaseFragment<T : ViewDataBinding, M : AndroidViewModel> : Fragment() {

    protected lateinit var mBinding: T
    protected lateinit var mViewModel: M

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initViews()
        mBinding.setVariable(getBindingVariable(), mViewModel)
        mBinding.executePendingBindings()
    }

    @ColorInt
    protected fun getColor(@ColorRes colorId: Int): Int {
        return ViewUtils.getColor(context, colorId)
    }

    /**
     * Gets the View Model from the View Model Providers using the <M> class argument.
     */
    private fun initViewModel() {
        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        mViewModel = ViewModelProviders.of(this).get(types[1] as Class<M>)
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
     * */
    fun getBinding(): T {
        return mBinding
    }

    /**
     * Called in the #BaseActivity[.onCreate]
     */
    protected abstract fun initViews()

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