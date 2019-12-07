package com.ab.bletest.app

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import com.ab.main.MainNavigator
import javax.inject.Inject

@AppSingleton
class MainNavigatorImpl @Inject constructor(): MainNavigator {
  override fun openDetail(activity: Activity) {
//    activity.startActivity(Intent().setComponent(ComponentName(activity.packageName, "com.ab.ble.")))
  }
}
