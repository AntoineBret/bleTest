package com.ab.main

import android.app.Activity
import com.ab.domain.DomainComponent
import com.ab.domain.FeatureSingleton
import com.ab.domain.domainComponent
import com.ab.kotlinutils.ComponentHolder
import com.ab.kotlinutils.getOrCreate
import dagger.BindsInstance
import dagger.Component
import inversion.Inversion
import inversion.InversionDef
import inversion.of

@Component(
  dependencies = [
    DomainComponent::class,
    MainDependencies::class
  ])
@FeatureSingleton
interface MainComponent {
  fun inject(activity: MainActivity)

  @Component.Factory
  interface Factory {
    fun create(
      dependencies: MainDependencies,
      domainComponent: DomainComponent,
      @BindsInstance permissionManager: PermissionManager,
      @BindsInstance connectionManager: ConnectionManager
    ): MainComponent
  }
}

interface MainNavigator {
  fun openDetail(activity: Activity)
}

interface MainDependencies {
  val mainNavigator: MainNavigator
}

@get:InversionDef
val ComponentHolder.mainDependencies by Inversion.of(MainDependencies::class)

fun ComponentHolder.mainComponent(): MainComponent = getOrCreate {
  kotlin.io.println("onCreate DaggerMainComponent.factory()")
  DaggerMainComponent.factory()
    .create(
      mainDependencies(),
      domainComponent(),
      PermissionManager(),
      ConnectionManager()
    )
}
