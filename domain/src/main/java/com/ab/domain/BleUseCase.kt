package com.ab.domain

import javax.inject.Inject

@OpenForTesting
@DomainSingleton
class BleUseCase @Inject constructor(
//  private val locationManager: LocationManager,
  private val configManager: ConfigManager
) {

//  suspend fun getTest(): String = try {
//    coroutineScope {
//      val location = locationManager.getLastLocation()
//
//      val cities = async { locationManager.getCities(location) }
//
//      val temperature = repository.getTemperature(location.lat, location.lon)
//
//      val city = cities.await().getOrElse(0) { "No city found" }
//      "$city \n $temperature"
//    }
//  } catch (e: Exception) {
//    "Error retrieving data: ${e.message}"
//  }

//  suspend fun getForecast(): String = coroutineScope {
////    try {
////      val location = locationManager.getLastLocation()
////
////      val temperature = repository.getForecast(location.lat, location.lon)
////
////      temperature.joinToString("\n")
////    } catch (e: Exception) {
////      "Error retrieving data: ${e.message}"
////    }
//  }
}
