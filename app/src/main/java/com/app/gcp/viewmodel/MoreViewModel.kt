package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.MoreRepository

class MoreViewModel : ViewModel() {

    private var repository: MoreRepository = MoreRepository.getInstance()

    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
