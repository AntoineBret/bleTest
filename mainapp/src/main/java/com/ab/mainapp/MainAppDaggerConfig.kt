package com.ab.mainapp

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.ab.domain.ConfigManager
import com.ab.domain.DomainDependencies
import com.ab.domain.PermissionManager
import com.ab.entities.AppPermission
import com.ab.kotlinutils.ComponentHolder
import com.ab.kotlinutils.getOrCreate
import com.ab.main.MainDependencies
import com.ab.main.MainNavigator
import inversion.InversionProvider

class FakeDomainDependencies : DomainDependencies {

   /* override */ val locationManager: PermissionManager
        get() = object : PermissionManager {
          override suspend fun readAppPermission(): AppPermission {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
          }

          override suspend fun getBluetoothPermission(): AppPermission {
            Log.d("","pouet")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
          }

          override suspend fun getWifiPermission(): AppPermission {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
          }

          override suspend fun geStoragePermission(): AppPermission {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
          }
        }

  override val configManager: ConfigManager
    get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}

@InversionProvider
fun provideImpl(componentHolder: ComponentHolder): DomainDependencies = componentHolder.getOrCreate { FakeDomainDependencies() }

class FakeMainDependencies : MainDependencies {
    override val mainNavigator: MainNavigator
        get() = object : MainNavigator {
            override fun openDetail(activity: Activity) {
                Toast.makeText(activity, "Layout clicked!", Toast.LENGTH_LONG).show()
            }
        }
}

@InversionProvider
fun provideMainDependenciesImpl(componentHolder: ComponentHolder): MainDependencies = componentHolder.getOrCreate { FakeMainDependencies() }
