package com.ab.bletest.app

import com.ab.ble.PermissionComponent
import com.ab.ble.permissionComponent
import com.ab.domain.DomainDependencies
import com.ab.kotlinutils.ComponentHolder
import com.ab.kotlinutils.getOrCreate
import com.ab.main.ConfigComponent
import com.ab.main.MainDependencies
import com.ab.main.MainNavigator
import com.ab.main.configComponent
import com.ab.utils.ComponentHolderApp
import dagger.Binds
import dagger.Component
import dagger.Module
import inversion.InversionProvider
import javax.inject.Scope

@Scope
annotation class AppSingleton

@Module
interface MainDependenciesModule {
  @Binds
  fun provideNavigator(impl: MainNavigatorImpl): MainNavigator
}

@Component(
  modules = [
    MainDependenciesModule::class
  ]
)
@AppSingleton
interface MainDependenciesImpl : MainDependencies

@InversionProvider
fun ComponentHolder.provideMainDependenciesImpl(): MainDependencies = getOrCreate {
  DaggerMainDependenciesImpl.create()
}

@Component(dependencies = [ConfigComponent::class, PermissionComponent::class])
interface DomainDependenciesImpl : DomainDependencies {

  @Component.Factory
  interface Factory {
    fun create(
      permissionComponent: PermissionComponent,
      configComponent: ConfigComponent
    ): DomainDependenciesImpl
  }
}

@InversionProvider
fun ComponentHolder.provideImpl(): DomainDependencies = getOrCreate {
  DaggerDomainDependenciesImpl.factory().create(
    (this as ComponentHolderApp).permissionComponent(), configComponent()
  )
}
