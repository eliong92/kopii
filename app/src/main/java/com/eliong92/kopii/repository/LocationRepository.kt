package com.eliong92.kopii.repository

import android.content.Context
import android.location.LocationManager
import io.reactivex.rxjava3.core.Single

class LocationRepository(
    private val context: Context
): ILocationRepository {
    override fun getCurrentLocation(): Single<Pair<Double, Double>> {
        return Single.fromCallable {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return@fromCallable try {
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    Pair(it.latitude, it.longitude)
                } ?: let {
                    throw Exception()
                }
            } catch (e: SecurityException) {
                throw e
            }
        }
    }
}