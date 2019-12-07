package com.ab.ble

import android.app.Application
import com.ab.domain.PermissionManager
import com.ab.kotlinutils.getOrCreate
import com.ab.utils.ComponentHolderApp
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
internal annotation class PermissionSingleton

interface PermissionComponent {
  val permissionManager: PermissionManager
}

@Component(
  modules = [PermissionModule::class]
)
@PermissionSingleton
interface PermissionComponentImpl : PermissionComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance app: Application): PermissionComponentImpl
  }
}

@Module
internal class PermissionModule {

  @PermissionSingleton
  @Provides
  fun providePermissionManager(impl: AndroidPermissionManager): PermissionManager = impl
}

fun ComponentHolderApp.permissionComponent(): PermissionComponent = getOrCreate {
  DaggerPermissionComponentImpl
    .factory()
    .create(this)
}
