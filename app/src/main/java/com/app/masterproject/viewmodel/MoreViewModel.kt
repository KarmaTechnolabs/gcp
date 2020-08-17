package com.app.masterproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.app.masterproject.base.APIResource
import com.app.masterproject.custom.Event
import com.app.masterproject.repository.MoreRepository

class MoreViewModel : ViewModel() {

    private var repository: MoreRepository = MoreRepository.getInstance()

    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
