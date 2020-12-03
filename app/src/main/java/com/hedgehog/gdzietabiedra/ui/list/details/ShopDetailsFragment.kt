package com.hedgehog.gdzietabiedra.ui.list.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.asvid.biedra.domain.OpenHours
import com.github.asvid.biedra.domain.Shop
import com.github.asvid.biedra.domain.printName
import com.hedgehog.gdzietabiedra.R
import com.hedgehog.gdzietabiedra.appservice.map.GoogleMapProvider
import com.hedgehog.gdzietabiedra.utils.generateDistanceText
import kotlinx.android.synthetic.main.fragment_shop_details.*
import org.koin.androidx.viewmodel.compat.ViewModelCompat.viewModel
import java.text.DateFormatSymbols
import java.util.*

class ShopDetailsFragment : Fragment() {

    private val vm: ShopDetailsViewModel by viewModel(this, ShopDetailsViewModel::class.java)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        arguments?.getString("shopId")?.let {
            vm.openedFromList(it)
        }
        vm.shopData.observe(viewLifecycleOwner) { shop ->
            shop_name_view.text = shop.printName()
            shop_features_view.text = shop.features.toString()
            shop_open_hours_view.text = shop.openHours.prettyPrint()
            shop_distance_view.text = shop.distance.generateDistanceText(resources)

            start_navigation_button.setOnClickListener {
                startNavigation(shop)
            }
        }
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_shop_details, container, false)
    }

    override fun onResume() {
        setupMap()
        super.onResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun setupMap() {
        shop_details_map_view.onCreate(null)
        shop_details_map_view.getMapAsync {
            val googleMapProvider = GoogleMapProvider.create(it, requireContext())
            shop_details_map_view.onResume()
            vm.mapLoaded(googleMapProvider)
        }
    }

    private fun startNavigation(shop: Shop) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("geo:" + shop.location.lat + "," +
                shop.location.lng + "?q=" + shop.address)
        requireContext().startActivity(intent)
    }
}

fun OpenHours.prettyPrint(): String {
    val symbols = DateFormatSymbols(Locale.getDefault()).shortWeekdays
    return if (this.sunday == null) {
        """
    ${symbols[2]}-${symbols[6]} : ${this.weekDay}
    ${symbols[7]} : ${this.saturday}
""".trimIndent()
    } else {
        """
    ${symbols[2]}-${symbols[6]} : ${this.weekDay}
    ${symbols[7]} : ${this.saturday}
    ${symbols[1]} : ${this.sunday}
""".trimIndent()
    }
}
