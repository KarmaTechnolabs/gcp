package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.ChangePasswordRequestModel
import com.app.gcp.api.responsemodel.EmptyResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.ChangePasswordRepository

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
