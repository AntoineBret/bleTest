package com.ab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ab.kotlinutils.ComponentHolder
import com.ab.utils.viewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

  private val viewModel: HomeViewModel by viewModel { viewModelProvider }

  @Inject
  lateinit var viewModelProvider: Provider<HomeViewModel>

  @Inject
  lateinit var permissionManager: PermissionManager

  @Inject
  lateinit var mainNavigator: MainNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (application as ComponentHolder).mainComponent().inject(this)

    setContentView(R.layout.activity_main)

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
    //Check permission when app launch
    permissionManager.checkPermissions(this)
  }

  override fun onRestart() {
    super.onRestart()
    //Check permission every time app restart to avoid manual deactivation of authorizations by the user
    permissionManager.checkPermissions(this)
  }
}
