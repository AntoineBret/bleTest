package com.ab.main

import android.app.Application
import com.ab.domain.ConfigManager
import com.ab.kotlinutils.getOrCreate
import com.ab.utils.ComponentHolderApp
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
internal annotation class PermissionSingleton

interface ConfigComponent {
  val configManager: ConfigManager
}

@Component(
  modules = [ConfigModule::class]
)
@PermissionSingleton
interface ConfigComponentImpl : ConfigComponent {

  @Component.Factory
  interface Factory {
    fun create(@BindsInstance app: Application): ConfigComponentImpl
  }
}

@Module
internal class ConfigModule {

  @PermissionSingleton
  @Provides
  fun provideConfigManager(impl: com.ab.main.AndroidConfigManager): ConfigManager = impl
}

fun ComponentHolderApp.configComponent(): ConfigComponent = getOrCreate {
  DaggerConfigComponentImpl
    .factory()
    .create(this)
}
