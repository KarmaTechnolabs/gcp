package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.MoreRepository

class MoreViewModel : ViewModel() {

    private var repository: MoreRepository = MoreRepository.getInstance()

    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
