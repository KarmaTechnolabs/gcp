package com.app.estore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.estore.base.APIResource
import com.app.estore.custom.Event
import com.app.estore.repository.MoreRepository

class MoreViewModel : ViewModel() {

    private var repository: MoreRepository = MoreRepository.getInstance()

    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
