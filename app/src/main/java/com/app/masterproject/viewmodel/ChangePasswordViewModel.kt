package com.app.masterproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.masterproject.api.requestmodel.ChangePasswordRequestModel
import com.app.masterproject.base.APIResource
import com.app.masterproject.custom.Event
import com.app.masterproject.repository.ChangePasswordRepository

class ChangePasswordViewModel : ViewModel() {

    private var repository: ChangePasswordRepository = ChangePasswordRepository.getInstance()

    private val changePasswordRequestLiveData = MutableLiveData<ChangePasswordRequestModel>()

    val changePasswordResponse: LiveData<Event<APIResource<Any>>> =
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
