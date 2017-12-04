package com.afterlogic.auroracontacts.presentation.common.base

import android.arch.lifecycle.*
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import com.afterlogic.auroracontacts.BR
import com.afterlogic.auroracontacts.core.rx.Subscriber
import com.afterlogic.auroracontacts.core.util.Tagged
import com.afterlogic.auroracontacts.presentation.common.databinding.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created by sunny on 04.12.2017.
 * mail: mail@sunnydaydev.me
 */

abstract class MVVMActivity<VM: ObservableViewModel, VDB: ViewDataBinding> :
        DaggerAppCompatActivity(), Tagged, HasLifecycleDisposables {

    protected var viewModelKey = BR.vm

    @set:Inject
    protected lateinit var viewModelFactory: ViewModelFactory

    @set:Inject
    override lateinit var lifecycleDisposables: LifecycleDisposables

    @set:Inject
    override lateinit var subscriber: Subscriber

    protected val binding: VDB by lazy { bindView() }

    protected val viewModel: VM by lazy {
        val provider = getViewModelProvider(viewModelFactory)
        getViewModel(provider)
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleDisposables.classTag = this.toString().split(".").last()

        lifecycle.addObserver(viewModel)

        binding.setVariable(viewModelKey, viewModel)

        lifecycleDisposables.onCreate()

    }

    @CallSuper
    override fun onStart() {
        super.onStart()
        lifecycleDisposables.onStart()
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        lifecycleDisposables.onResume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
        lifecycleDisposables.onPause()
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
        lifecycleDisposables.onStop()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        lifecycleDisposables.onDestroy()
    }

    abstract protected fun bindView(): VDB

    abstract protected fun getViewModel(provider: ViewModelProvider): VM

    open protected fun getViewModelProvider(factory: ViewModelFactory): ViewModelProvider {
        return ViewModelProviders.of(this, factory)
    }

}