package com.hedgehog.gdzietabiedra

import android.app.Application
import androidx.room.Room
import com.hedgehog.gdzietabiedra.api.BiedraKtorService
import com.hedgehog.gdzietabiedra.appservice.DistanceCalculator
import com.hedgehog.gdzietabiedra.appservice.LocationWatchdog
import com.hedgehog.gdzietabiedra.appservice.LocationWatchdogCoroutines
import com.hedgehog.gdzietabiedra.appservice.ShopService
import com.hedgehog.gdzietabiedra.data.repository.shops.AppDatabase
import com.hedgehog.gdzietabiedra.data.repository.shops.ShopsRepository
import com.hedgehog.gdzietabiedra.ui.list.ListViewModel
import com.hedgehog.gdzietabiedra.utils.CrashlyticsTree
import com.squareup.leakcanary.LeakCanary
import net.danlew.android.joda.JodaTimeAndroid
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber
import timber.log.Timber.DebugTree

/**
 * Application class, no rocket science here
 * */
class App : Application() {

  /**
   * [LeakCanary] initialisation
   * [Timber] initialisation
   * */
  override fun onCreate() {
    super.onCreate()

    initLeakCanary()
    initTimber()
    initJoda()
    initKoin()
  }

  private fun initKoin() {
    startKoin {
      androidContext(this@App)
      modules(module {
        single<AppDatabase> {
          Room.databaseBuilder(
              applicationContext,
              AppDatabase::class.java, "biedra-shops.db"
          ).createFromAsset("biedra-shops.db")
              .build()        }
        single { DistanceCalculator() }
        single { ShopsRepository(get<AppDatabase>().shopRoomDao()) }
        single { ShopService(get(), get()) }
        single { BiedraKtorService() }
        single { LocationWatchdog(get()) }
        single { LocationWatchdogCoroutines(get()) }

        viewModel { ListViewModel(get(), get()) }
      })
    }
  }

  private fun initJoda() {
    JodaTimeAndroid.init(this)
  }

  private fun initTimber() {
    Timber.plant(DebugTree(), CrashlyticsTree())
  }

  private fun initLeakCanary() {
    if (LeakCanary.isInAnalyzerProcess(this)) {
      return
    }
    LeakCanary.install(this)
  }
}