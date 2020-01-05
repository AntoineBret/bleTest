package com.ab.main

import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.ab.domain.OpenForTesting
import com.tbruyelle.rxpermissions2.RxPermissions
import timber.log.Timber


@OpenForTesting
class PermissionManager {

  /**
  PermissionManager manages the permissions of the application necessary to activate connectivity
  and access to external files. In accordance with
  https://developer.android.com/guide/topics/permissions/overview#dangerous-permission-prompt
  Only permissions in the table of dangerous permissions need to requested at runtime.
  BLUETOOTH, BLUETOOTH_ADMIN, INTERNET are normal permissions and are therefore they are
  automatically granted. Moreover, to access the hardware identifiers of nearby external devices
  via Bluetooth and Wi-Fi scans, app must now have the ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION
   */

  private val permissionsList = ArrayList<String>()

  /**
   * Check permissions and add missing ones to the list of future requests
   */
  fun checkPermissions(activity: Activity) {
    permissionsList.clear()
    if (!isLocationPermissionGranted(activity)) {
      permissionsList.add(ACCESS_COARSE_LOCATION)
    }
    if (!isReadOnExternalStoragePermissionGranted(activity)) {
      permissionsList.add(READ_EXTERNAL_STORAGE)
    }
    if (!isWriteOnExternalStoragePermissionGranted(activity)) {
      permissionsList.add(WRITE_EXTERNAL_STORAGE)
    }
    requestPermissions(activity)
  }

  /**
  Get permission : calling this method brings up a standard (native) Android dialog, which you cannot customize.
   */
  @SuppressLint("CheckResult")
  private fun requestPermissions(activity: Activity) {
    if (!permissionsList.isNullOrEmpty()) {
      Timber.d("request permission for :")
      permissionsList.forEach { Timber.d(it) }
      //Convert ArrayList (dynamically created by fun checkPermissions()) into Array (fixed size of permissions) for RxPermissions
      val permissions: Array<String?> = permissionsList.toTypedArray()
      val rxPermissions = RxPermissions(activity) //Init RxPermissions lib
      rxPermissions.request(*permissions)
        .subscribe { granted ->
          if (!granted) {
            showRetryDialog(activity)
          }
        }
    } else {
      Timber.d("All permissions are already granted")
    }
  }

  /**
  Check location permission: return true when permission is granted, false when denied
   */
  private fun isLocationPermissionGranted(activity: Activity): Boolean {
    val locationPermissionResult =
      (ContextCompat.checkSelfPermission(activity, ACCESS_COARSE_LOCATION)
        == PackageManager.PERMISSION_GRANTED)
    if (locationPermissionResult) {
      Timber.d("location permission is granted")
    } else {
      Timber.d("location permission is not granted")
    }
    return locationPermissionResult
  }

  /**
  Check read on external storage permission: return true when permission is granted, false when denied
   */
  private fun isReadOnExternalStoragePermissionGranted(activity: Activity): Boolean {
    val readOnExternalStoragePermissionResult =
      (ContextCompat.checkSelfPermission(activity, READ_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED)
    if (readOnExternalStoragePermissionResult) {
      Timber.d("read on external storage permission is granted")
    } else {
      Timber.d("read on external storage permission is not granted")
    }
    return readOnExternalStoragePermissionResult
  }

  /**
  Check write on external storage permission: return true when permission is granted, false when denied
   */
  private fun isWriteOnExternalStoragePermissionGranted(activity: Activity): Boolean {
    val writeOnExternalStoragePermissionResult =
      (ContextCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE)
        == PackageManager.PERMISSION_GRANTED)
    if (writeOnExternalStoragePermissionResult) {
      Timber.d("write on external storage permission is granted")
    } else {
      Timber.d("write on external storage permission is not granted")
    }
    return writeOnExternalStoragePermissionResult
  }

  /**
   * Help the user understand why this app needs these permissions
   */
  private fun showRetryDialog(activity: Activity) {
    AlertDialog.Builder(activity)
      .setTitle("Permissions required")
      .setMessage("This app needs these permissions to run, please accept")
      .setPositiveButton("Retry") { dialog, which -> checkPermissions(activity) }
      .create()
      .show()
  }
}
