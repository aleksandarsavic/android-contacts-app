package com.afterlogic.auroracontacts.presentation.navigation

import android.content.Context
import android.content.Intent
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.afterlogic.auroracontacts.R
import com.afterlogic.auroracontacts.presentation.foreground.login.LoginFragment
import com.afterlogic.auroracontacts.presentation.foreground.main.MainFragment
import com.afterlogic.auroracontacts.presentation.foreground.unsuportedApi.UnsupportedApiFragment
import ru.terrakok.cicerone.android.SupportAppNavigator
import javax.inject.Inject

/**
 * Created by sunny on 07.12.2017.
 * mail: mail@sunnydaydev.me
 */

class AppNavigator @Inject constructor(
        activity: FragmentActivity, @IdRes contentId: Int = R.id.content
): SupportAppNavigator(activity, contentId) {

    override fun createActivityIntent(context: Context, screenKey: String, data: Any?): Intent? = null

    override fun createFragment(screenKey: String, data: Any?): Fragment? = when(screenKey) {

        Screens.LOGIN -> LoginFragment.newInstance()
        Screens.MAIN -> MainFragment.newInstance()
        Screens.UNSUPPORTED_API -> UnsupportedApiFragment.newInstance()

        else -> null

    }

}