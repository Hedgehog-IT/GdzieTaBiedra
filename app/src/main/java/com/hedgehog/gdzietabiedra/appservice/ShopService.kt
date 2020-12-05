package com.hedgehog.gdzietabiedra.appservice

import com.github.asvid.biedra.domain.shops.Location
import com.github.asvid.biedra.domain.shops.Shop
import com.hedgehog.gdzietabiedra.data.repository.ShopsRepository
import timber.log.Timber

/**
 * Only class that provides access to [Shop]s
 * For the moment it's used only to return [Shop]s from [ShopsRepository], but in future it might be used to manipulate data
 * */
class ShopService constructor(
        private val shopsRepository: ShopsRepository,
        private val distanceCalculator: DistanceCalculator
) {

    /**
     * @param address - user query for city or street where [Shop] might be
     * @param location - user location to calculate distance to returned [Shop]
     *
     * @return [List] of [Shop]s that fit to query
     * */
    suspend fun getShopsByAddress(address: String, location: Location?): List<Shop> {
        Timber.d("looking for shops with address like: $address around: $location")
        return shopsRepository.fetchByAddress(address)
                .apply {
                    this.forEach {
                        it.calculateDistance(location)
                    }
                }
                .sortedBy { it.distance }
                .take(10)
    }

    /**
     * @param location - user location to narrow DB query
     * @param range - range in lat/lng degrees in which shops around [location] will be returned. Typical value is about 0.1
     *
     * @return [List] of [Shop]s that are in [range] of [location]
     * */
    suspend fun getShopsInRange(location: Location?, range: Double): List<Shop> {
        return if (location == null) listOf()
        else shopsRepository.fetchByLocationAndRange(location, range)
                .apply {
                    this.forEach {
                        it.calculateDistance(location)
                    }
                }
    }

    suspend fun getShopsInCloseArea(location: Location?): List<Shop> {
        return if (location == null) listOf()
        else shopsRepository.fetchByLocationAndRange(location, 0.1)
                .onEach {
                    it.calculateDistance(location)
                }
    }

    suspend fun getAllShops(): List<Shop> {
        return shopsRepository.fetchAll()
    }

    private fun Shop.calculateDistance(observerLocation: Location?) {
        distance = if (observerLocation == null) {
            null
        } else {
            distanceCalculator.calculateDistance(location, observerLocation)
        }
    }

    suspend fun getShopById(shopId: String): Shop? {
        return shopsRepository.fetchById(shopId)
    }

    suspend fun getShopById(shopId: String, observerLocation: Location?): Shop? {
        return shopsRepository.fetchById(shopId).apply {
            this?.calculateDistance(observerLocation)
        }
    }
}