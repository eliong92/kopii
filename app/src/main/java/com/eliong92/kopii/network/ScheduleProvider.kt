package com.eliong92.kopii.network

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class ScheduleProvider : IScheduleProvider {
    override fun io() = Schedulers.io()
    override fun mainThread() = AndroidSchedulers.mainThread()
}