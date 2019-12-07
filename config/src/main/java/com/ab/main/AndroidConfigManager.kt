package com.ab.main

import android.app.Application
import com.ab.domain.ConfigManager
import com.ab.entities.JsonProperties
import javax.inject.Inject

@PermissionSingleton
class AndroidConfigManager  @Inject constructor(context: Application) : ConfigManager {
  override suspend fun getJsonProperties(obu: Float, retry: Int): JsonProperties {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
