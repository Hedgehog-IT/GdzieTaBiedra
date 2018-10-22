package com.hedgehog.gdzietabiedra.ribs.bottomnav.map

import com.github.asvid.biedra.domain.Position
import com.google.android.gms.maps.GoogleMap
import com.hedgehog.gdzietabiedra.appservice.LocationService
import com.hedgehog.gdzietabiedra.appservice.ShopService
import com.hedgehog.gdzietabiedra.appservice.map.GoogleMapProvider
import com.hedgehog.gdzietabiedra.appservice.map.IMapProvider
import com.hedgehog.gdzietabiedra.appservice.map.ShopMarker
import com.uber.rib.core.BaseInteractor
import com.uber.rib.core.Bundle
import com.uber.rib.core.RibInteractor
import io.reactivex.BackpressureStrategy.LATEST
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [MapScope].
 *
 */
@RibInteractor
class MapInteractor : BaseInteractor<MapInteractor.MapPresenter, MapRouter>() {

  @Inject
  lateinit var presenter: MapPresenter
  @Inject
  lateinit var locationService: LocationService
  @Inject
  lateinit var shopsService: ShopService

  private lateinit var mapProvider: IMapProvider
  private val mapSubject = BehaviorSubject.create<IMapProvider>()

  override fun didBecomeActive(savedInstanceState: Bundle?) {
    super.didBecomeActive(savedInstanceState)
    presenter.initView().zipWith(
        locationService.getLocation(),
        BiFunction<GoogleMap, Position, Position> { googleMap, position ->
          mapProvider = GoogleMapProvider.create(googleMap)
          mapProvider.goToPosition(position)
          mapSubject.onNext(mapProvider)
          position
        })
        .toFlowable()
        .flatMap {
          shopsService.getShopsInRange(it, 0.1)
        }
        .subscribeBy { shop ->
          mapProvider.drawMarker(
              ShopMarker(shop.location, shop))
        }
        .addToDisposables()

    mapSubject.concatMap {
      it.shopMarkerClicked()
    }.subscribe {
      presenter.switchNavigationButton(true)
    }.addToDisposables()

    mapSubject.concatMap {
      it.mapClicked()
    }.subscribe {
      presenter.switchNavigationButton(false)
    }.addToDisposables()

    mapSubject.concatMap { it.mapMoved() }
        .toFlowable(LATEST)
        .flatMap {
          mapProvider.clearMap()
          shopsService.getShopsInRange(it, 0.1)
        }
        .subscribe { shop ->
          mapProvider.drawMarker(
              ShopMarker(shop.location, shop))
        }
        .addToDisposables()

    mapSubject
        .flatMap {
          it.shopMarkerClicked()
        }
        .flatMap { shopMarker ->
          presenter.navigationButtonListener()
              .concatMap {
                Observable.just(shopMarker)
              }
        }
        .subscribe {
          Timber.d("navigating to shop: $it")
          presenter.startNavigation(it.position)
        }
        .addToDisposables()
  }

  /**
   * Presenter interface implemented by this RIB's view.
   */
  interface MapPresenter {

    fun initView(): Single<GoogleMap>
    fun navigationButtonListener(): Observable<*>
    fun startNavigation(position: Position)
    fun switchNavigationButton(visible: Boolean)
  }
}
