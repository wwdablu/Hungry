package com.soumya.wwdablu.zomatobuddy.dagger

import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface NetworkComponent {
}