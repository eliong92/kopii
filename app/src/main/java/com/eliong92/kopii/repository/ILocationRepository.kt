package com.eliong92.kopii.repository

import io.reactivex.rxjava3.core.Single

interface ILocationRepository {
    fun getCurrentLocation(): Single<Pair<Double, Double>>
}