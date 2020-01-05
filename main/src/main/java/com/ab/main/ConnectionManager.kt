package com.ab.main

import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.github.pwittchen.reactivenetwork.library.rx2.Connectivity
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class ConnectionManager {

  /**
  ConnectionManager is used to check that the connectivity of this application is OK during the whole
  process regarding bluetooth, internet access and location. For listening network connection state
  and Internet connectivity we use ReactiveNetwork library. Please refer to https://github.com/pwittchen/ReactiveNetwork
  for further details
   */

  private val internetConnectivityDetails: MutableLiveData<Connectivity> = MutableLiveData()
  private val isInternetConnected: MutableLiveData<Boolean> = MutableLiveData()

  private val connectionList = ArrayList<String>()

  fun checkConnections(activity: AppCompatActivity) {
    connectionList.clear()
    if (!isLocationConnectionEnabled(activity)) {
      connectionList.add(ACTION_LOCATION_SOURCE_SETTINGS)
    }
    if (!isBluetoothConnectionEnabled()) {
      connectionList.add(ACTION_BLUETOOTH_SETTINGS)
    }
    if (!isInternetConnectionEnabled()) {
      connectionList.add(ACTION_WIFI_SETTINGS)
    }
    if (!connectionList.isNullOrEmpty()) {
      showAlertDialog(activity)
    }
  }

  private fun askUser(activity: AppCompatActivity) {
    if (!connectionList.isNullOrEmpty()) {
      val first = connectionList.get(0)
      val enableIntent = Intent(first)
      activity.startActivity(enableIntent)
      connectionList.removeAt(0)
      askUser(activity)
    }
  }

  /**
   * Check Location connectivity: return true when location is enabled, false when disabled
   */
  private fun isLocationConnectionEnabled(activity: Activity): Boolean {
    val locationManager = activity.getSystemService(LOCATION_SERVICE) as LocationManager
    return if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      Timber.d("location service is disabled")
      false
    } else { // Bluetooth is enabled
      Timber.d("location service is enabled")
      true
    }
  }

  /**
   * Check Bluetooth connectivity: return true when bluetooth is enabled, false when disabled
   */
  @SuppressLint("MissingPermission") //uses-permission is already declared in mainApp module manifest
  private fun isBluetoothConnectionEnabled(): Boolean {
    val mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    return if (!mBluetoothAdapter.isEnabled) {
      Timber.d("bluetooth connection is disabled")
      false
    } else {
      Timber.d("bluetooth connection is enabled")
      true
    }
  }

  /**
   * Check Internet connectivity: return true when bluetooth is enabled, false when disabled
   */
  private fun isInternetConnectionEnabled(): Boolean {
    Timber.d("isInternetConnectionEnabled = ${isInternetConnected.value}")

    return if (isInternetConnected.value == false) {
      Timber.d("isInternetConnectionEnabled is disabled")
      false
    } else {
      Timber.d("isInternetConnectionEnabled is enabled")
      true
    }
  }

  /**
   * We observe Connectivity with observeNetworkConnectivity(context) method and then
   * when Connectivity changes, subscriber will be notified. Connectivity can change its state or type.
   */
  @SuppressLint("CheckResult")
  fun observeInternetConnectivity(context: AppCompatActivity) {
    ReactiveNetwork
      .observeNetworkConnectivity(context)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ connectivity ->
        this.internetConnectivityDetails.value = connectivity
        isInternetConnected.value = connectivity.available()
        checkConnections(context)
      }, { throwable ->
        Timber.d("observeInternetConnectivity error cause $throwable")
      })
  }

  fun getInternetConnectivityDetails(): MutableLiveData<Connectivity> {
    return internetConnectivityDetails
  }

  /**
   * Help the user understand why this app needs these connection
   */
  private fun showAlertDialog(activity: AppCompatActivity) {
    AlertDialog.Builder(activity)
      .setTitle("Connection required")
      .setMessage("This app needs these connections to run, please accept : $connectionList")
      .setPositiveButton("Go") { dialog, which -> askUser(activity) }
      .create()
      .show()
  }
}
