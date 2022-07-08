package br.com.walter.walter.core.platform

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class CoroutinePresenter: CoroutineScope {

    private val job = Job()

    override val coroutineContext = Dispatchers.Main + job

    fun onViewDetached() {
        job.cancel()
    }

}