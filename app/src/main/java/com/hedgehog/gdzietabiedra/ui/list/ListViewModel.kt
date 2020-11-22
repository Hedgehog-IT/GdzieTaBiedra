package com.hedgehog.gdzietabiedra.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.asvid.biedra.domain.Shop
import com.hedgehog.gdzietabiedra.appservice.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class ListViewModel(
        private val shopService: ShopService,
        private val locationService: LocationService,
) : ViewModel() {

    private var searchJob: Job? = null
    private val _viewState = MutableLiveData<ListViewState>()
    val viewState: LiveData<ListViewState> = _viewState

    private val _shopList = MutableLiveData<List<Shop>>()
    val shopList: LiveData<List<Shop>> = _shopList

    fun loadData() {
        _viewState.postValue(LoadingShops)
        loadShopsForUserLocation()
    }

    private fun loadShopsForUserLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val location = locationService.getLocation()) {
                is Success -> loadShopsForLocation(location)
                is Error -> locationNotAvailable()
                is PermissionRequired -> requestLocationPermission()
                is LocationNotAvailable -> locationNotAvailable()
            }
        }
    }

    private fun loadShopsForLocation(location: Success) {
        viewModelScope.launch(Dispatchers.IO) {
            val shopsInRange = shopService.getShopsInCloseArea(location.location)
            _shopList.postValue(shopsInRange)
            _viewState.postValue(ShopsLoaded)
        }
    }

    private fun locationNotAvailable() {
        _viewState.postValue(NoAvailableLocation)
    }

    private fun requestLocationPermission() {
        _viewState.postValue(RequestLocationPermission)
    }

    fun locationPermissionGranted() {
        loadShopsForUserLocation()
    }

    fun shopSearchByQuery(query: String?) {
        if (query.isNullOrEmpty()) {
            _viewState.postValue(NoShopsAvailable)
            return
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            val shops = shopService.getShopsByAddress(query, null)
            if (this.isActive) {
                _shopList.postValue(shops)
                _viewState.postValue(ShopsLoaded)
            }
        }
    }

    fun shopListItemClicked(it: Shop) {

    }

    fun locationPermissionDenied() {
        _viewState.postValue(LocationPermissionDenied)
    }
}

sealed class ListViewState
object RequestLocationPermission : ListViewState()
object LocationPermissionDenied : ListViewState()
object NoAvailableLocation : ListViewState()
object LoadingShops : ListViewState()
object ShopsLoaded : ListViewState()
object NoShopsAvailable : ListViewState()
object ErrorLoadingShops : ListViewState()
