package com.advancedfinance.core.platform

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean
import androidx.annotation.Nullable

class ActionLiveData<T> : MutableLiveData<T>() {

    private val isPending = AtomicBoolean(false)

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        super.observe(owner, Observer { data ->
            if (isPending.compareAndSet(true, false)) {
                observer.onChanged(data)
            }
        })
    }

    @MainThread
    override fun setValue(@Nullable value: T) {
        isPending.set(true)
        super.setValue(value)
    }
}