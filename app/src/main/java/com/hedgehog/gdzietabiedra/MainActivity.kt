package com.hedgehog.gdzietabiedra

import android.os.Bundle
import android.view.ViewGroup
import com.hedgehog.gdzietabiedra.appservice.LocationWatchdog
import com.hedgehog.gdzietabiedra.appservice.ShopService
import com.hedgehog.gdzietabiedra.ribs.RootBuilder
import com.hedgehog.gdzietabiedra.utils.analytics.Analytics
import com.karumi.dexter.Dexter
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import dagger.android.AndroidInjection
import javax.inject.Inject


class MainActivity : RibActivity() {

  @Inject
  lateinit var locationWatchdog: LocationWatchdog
  @Inject
  lateinit var shopService: ShopService
  @Inject
  lateinit var analytics: Analytics

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    locationWatchdog.register()
  }

  override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> =
      RootBuilder(object : RootBuilder.ParentComponent {
        override fun locationService() = locationWatchdog
        override fun shopServices() = shopService
        override fun dexter() = Dexter.withActivity(this@MainActivity)
        override fun analytics(): Analytics = analytics
      })
          .build(parentViewGroup)


  override fun onDestroy() {
    super.onDestroy()
    locationWatchdog.unregister()
  }
}
