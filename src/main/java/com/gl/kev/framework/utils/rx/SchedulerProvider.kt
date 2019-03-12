package com.gl.kev.framework.utils.rx

import io.reactivex.Scheduler

/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/12/2019
 */
interface SchedulerProvider {

    fun ui(): Scheduler

    fun computation(): Scheduler

    fun io(): Scheduler
}