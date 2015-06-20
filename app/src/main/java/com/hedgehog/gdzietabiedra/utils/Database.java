package com.hedgehog.gdzietabiedra.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.hedgehog.gdzietabiedra.pojo.Shops.Shop;
import com.hedgehog.gdzietabiedra.pojo.Shops.ShopList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Adam on 2015-06-18.
 */
public class Database {

    private static Realm realm = Biedra.getRealm();

    public static void populate(Double lat, Double lng){
        Biedra.shopApi.getShops(lat, lng, new Callback<ShopList>() {
            @Override
            public void success(ShopList shopList, Response response) {
                Log.d("sklepy", shopList.getShops().toString());

                realm.beginTransaction();
                realm.copyToRealmOrUpdate(shopList.getShops());
                realm.commitTransaction();
            }
            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    public static RealmResults<Shop> getShopInfo(String id){
        RealmQuery<Shop> query = realm.where(Shop.class);
        query.equalTo("id", id);
        return query.findAll();
    }

    public static RealmResults<Shop> getAll(){
        RealmQuery<Shop> query = realm.where(Shop.class);
        return query.findAll();
    }

    public static List<Shop> getListClosest(){
        Location lastLocation = ((LocationManager) Biedra.getAppContext().getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
        RealmResults<Shop> query = getAll();
        List<Shop> result = new ArrayList<Shop>();

        for(int i = 0, l = query.size(); i < l; i++){
            Shop current = query.get(i);
            Location currentLoc = new Location(lastLocation);
            currentLoc.setLatitude(Double.parseDouble(current.getLatitude()));
            currentLoc.setLongitude(Double.parseDouble(current.getLongitude()));
            if(lastLocation.distanceTo(currentLoc) < Const.radius){
                realm.beginTransaction();
                current.setDistance(lastLocation.distanceTo(currentLoc) + "");
                realm.commitTransaction();
                result.add(current);
            }
        }
        Log.d("database ", result.toString());
        Collections.sort(result, new Comparator<Shop>() {
            @Override
            public int compare(Shop lhs, Shop rhs) {
                if (Double.parseDouble(lhs.getDistance()) == Double.parseDouble(rhs.getDistance()))
                    return 0;
                return Double.parseDouble(lhs.getDistance()) < Double.parseDouble(rhs.getDistance()) ? -1 : 1;
            }
        });
        Log.d("database ", result.toString());
        return result;
    }

}
