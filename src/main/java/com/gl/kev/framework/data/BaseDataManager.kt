package com.gl.kev.framework.data

import com.gl.kev.framework.utils.rx.AppSchedulerProvider
import com.gl.kev.framework.utils.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import java.util.concurrent.Callable

/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/13/2019
 */
@Suppress("unused")
abstract class BaseDataManager(val mSchedulerProvider: SchedulerProvider = AppSchedulerProvider()) {

    private var mCompositeDisposable: CompositeDisposable? = null

    fun getCompositeDisposable(): CompositeDisposable {
        if (mCompositeDisposable == null || mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable = CompositeDisposable()
        }
        return mCompositeDisposable!!
    }

    fun <T> genericCallable(callable: Callable<T>, consumer: Consumer<T>, failure: Consumer<Throwable>) {
        getCompositeDisposable().add(
            Observable.fromCallable(callable)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(consumer, failure)
        )
    }

    fun timeOut(mills: Long, consumerCallback: Consumer<Boolean>, failure: Consumer<Throwable>) {
        genericCallable<Boolean>(Callable {
            try {
                Thread.sleep(mills)
            } catch (e: Exception) {
                //Interrupt
            }
            true
        }, consumerCallback, failure)
    }

    fun dispose() {
        getCompositeDisposable().dispose()
    }

}


