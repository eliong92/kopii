package com.eliong92.kopii.network

import io.reactivex.rxjava3.core.Scheduler

interface IScheduleProvider {
    fun io(): Scheduler
    fun mainThread(): Scheduler
}