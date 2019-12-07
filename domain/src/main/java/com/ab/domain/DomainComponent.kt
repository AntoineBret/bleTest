package com.ab.domain

import com.ab.kotlinutils.ComponentHolder
import com.ab.kotlinutils.getOrCreate
import dagger.Component
import inversion.Inversion
import inversion.InversionDef
import inversion.of
import javax.inject.Scope

@Scope
internal annotation class DomainSingleton

@DomainSingleton
@Component(dependencies = [DomainDependencies::class])
interface DomainComponent {
  val bleUseCase: BleUseCase

  @Component.Factory
  interface Factory {
    fun create(domainDependencies: DomainDependencies): DomainComponent
  }
}

interface DomainDependencies {
//  val locationManager: LocationManager
//
  val configManager: ConfigManager
}

@get:InversionDef
val ComponentHolder.domainDependencies by Inversion.of(DomainDependencies::class)

fun ComponentHolder.domainComponent(): DomainComponent = getOrCreate {
  DaggerDomainComponent.factory().create(domainDependencies())
}
