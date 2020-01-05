package com.ab.ble

import android.app.Application
import com.ab.domain.PermissionManager
import com.ab.entities.AppPermission
import javax.inject.Inject

@PermissionSingleton
class AndroidPermissionManager  @Inject constructor(context: Application) : PermissionManager {
  override suspend fun readAppPermission(): AppPermission {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getBluetoothPermission(): AppPermission {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getWifiPermission(): AppPermission {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun geStoragePermission(): AppPermission {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

}
