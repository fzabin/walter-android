package br.com.walter.walter.core.archcomponents.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.walter.walter.core.archcomponents.Event
import br.com.walter.walter.core.archcomponents.LiveEvent
import br.com.walter.walter.core.archcomponents.MutableLiveEvent

/**
 * Creates an instance of [LiveData] with `this` as its value.
 */
fun <T> T?.asLiveData(): LiveData<T> = MutableLiveData<T>().apply { value = this@asLiveData }

/**
 * Creates an instance of [LiveEvent] with `this` as its value.
 */
fun <T> T?.asLiveEvent(): LiveEvent<T> = MutableLiveEvent<T>().apply { value = Event(this@asLiveEvent) }