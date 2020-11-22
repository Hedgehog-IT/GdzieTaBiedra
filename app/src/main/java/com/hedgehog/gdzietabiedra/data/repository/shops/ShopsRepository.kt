package com.hedgehog.gdzietabiedra.data.repository.shops

import com.github.asvid.biedra.domain.Location
import com.github.asvid.biedra.domain.Shop

class ShopsRepository constructor(private val shopDao: ShopRoomDao) {

    suspend fun fetchFirst(number: Int = 1): List<Shop> {
        return shopDao.getAll().take(number)
                .map {
                    it.toDomainModel()
                }
    }

    suspend fun fetchById(id: String): Shop? {
        return shopDao.getById(id)?.toDomainModel()
    }

    suspend fun fetchByAddress(address: String): List<Shop> {
        return shopDao.fetchForAddress(address)
                .map { it.toDomainModel() }
    }

    suspend fun fetchByLocationAndRange(
            location: Location,
            range: Double
    ): List<Shop> {
        val minLat = location.lat - range / 2
        val maxLat = location.lat + range / 2
        val minLng = location.lng - range
        val maxLng = location.lng + range

        return shopDao.fetchInRange(minLat, maxLat, minLng, maxLng)
                .map { it.toDomainModel() }
    }
}