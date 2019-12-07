package com.ab.domain

import com.ab.entities.JsonProperties

interface ConfigManager  {
  suspend fun getJsonProperties(obu: Float, retry: Int): JsonProperties
}
