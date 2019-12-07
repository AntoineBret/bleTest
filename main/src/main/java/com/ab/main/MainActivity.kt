package com.ab.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ab.kotlinutils.ComponentHolder
import com.ab.utils.viewModel
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : AppCompatActivity() {

  private val viewModel: HomeViewModel by viewModel { viewModelProvider }

  @Inject
  lateinit var viewModelProvider: Provider<HomeViewModel>

//  @Inject
//  lateinit var permissionManager: PermissionManager

  @Inject
  lateinit var mainNavigator: MainNavigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    (application as ComponentHolder).mainComponent().inject(this)

    setContentView(R.layout.activity_main)

  }
}
