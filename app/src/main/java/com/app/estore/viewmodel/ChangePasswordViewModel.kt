package com.app.estore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.estore.api.requestmodel.ChangePasswordRequestModel
import com.app.estore.base.APIResource
import com.app.estore.custom.Event
import com.app.estore.repository.ChangePasswordRepository

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
