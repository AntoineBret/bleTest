package com.ab.domain

import com.ab.entities.AppPermission

interface PermissionManager {

  suspend fun readAppPermission(): AppPermission
  suspend fun  getBluetoothPermission(): AppPermission
  suspend fun  getWifiPermission(): AppPermission
  suspend fun  geStoragePermission(): AppPermission
}
