package di

import logic.GetItalianMealsForLargeGroupUsesCase
import org.koin.dsl.module

val useCaseModule = module{
    single { GetItalianMealsForLargeGroupUsesCase(get()) }
}