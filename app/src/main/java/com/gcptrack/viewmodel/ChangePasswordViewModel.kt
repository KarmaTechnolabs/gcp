package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gcptrack.api.requestmodel.ChangePasswordRequestModel
import com.gcptrack.api.responsemodel.EmptyResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.ChangePasswordRepository

class ChangePasswordViewModel : ViewModel() {

    private var repository: ChangePasswordRepository = ChangePasswordRepository.getInstance()

    private val changePasswordRequestLiveData = MutableLiveData<ChangePasswordRequestModel>()

    val changePasswordResponse: LiveData<Event<APIResource<EmptyResponse>>> =
        changePasswordRequestLiveData.switchMap {
            repository.callChangePasswordAPI(it)
        }

    fun callChangePasswordAPI(changePasswordRequestModel: ChangePasswordRequestModel) {
        changePasswordRequestLiveData.value = changePasswordRequestModel
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
