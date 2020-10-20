package com.hedgehog.gdzietabiedra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hedgehog.gdzietabiedra.api.BiedraKtorService
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

  val biedraApi: BiedraKtorService by inject()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val navView: BottomNavigationView = findViewById(R.id.nav_view)

    val navController = findNavController(R.id.nav_host_fragment)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(setOf(
        R.id.navigation_list, R.id.navigation_map, R.id.navigation_sundays))
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)

    lifecycleScope.launch {
      biedraApi.getShops(51.0, 21.0).shops?.forEach {
        println("shop: $it")
      }
    }
  }
}