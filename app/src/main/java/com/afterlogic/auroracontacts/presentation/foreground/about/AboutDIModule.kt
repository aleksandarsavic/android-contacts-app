package com.afterlogic.auroracontacts.presentation.foreground.about

import android.arch.lifecycle.ViewModel
import com.afterlogic.auroracontacts.presentation.common.base.MVVMInjection
import com.afterlogic.auroracontacts.presentation.common.databinding.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

/**
 * Created by sunny on 22.01.2018.
 * mail: mail@sunnydaydev.me
 */

@Module
abstract class AboutDIModule {

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindLoginViewModel(vm: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LicensesViewModel::class)
    abstract fun bindLicencesViewModel(vm: LicensesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LicenseViewModel::class)
    abstract fun bindLicenceViewModel(vm: LicenseViewModel): ViewModel

}

data class AboutInjection @Inject constructor(
        override val config: MVVMInjection.Config
): MVVMInjection

data class LicencesInjection @Inject constructor(
        override val config: MVVMInjection.Config
): MVVMInjection

data class LicenceInjection @Inject constructor(
        override val config: MVVMInjection.Config
): MVVMInjection