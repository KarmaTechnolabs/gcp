package com.app.estore.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.estore.api.responsemodel.LoginResponse
import com.app.estore.base.APIResource
import com.app.estore.custom.Event
import com.app.estore.model.*
import com.app.estore.repository.ProfileRepository

class ProfileViewModel : ViewModel() {

    private var repository = ProfileRepository.getInstance()

    private val stateLiveData = MutableLiveData<StateRequestModel>()
    private val updateProfileLiveData = MutableLiveData<UpdateProfileRequestModel>()

    val stateResponse: LiveData<Event<APIResource<List<StateModel>>>> =
        stateLiveData.switchMap {
            repository.getStateListApi(it)
        }


    val updateProfileResponse: LiveData<Event<APIResource<LoginResponse>>> =
        updateProfileLiveData.switchMap {
            repository.updateProfile(it)
        }

    fun getStateLists(stateRequestModel: StateRequestModel) {
        stateLiveData.value = stateRequestModel
    }

    fun updateProfile(updateProfileRequestModel: UpdateProfileRequestModel) {
        updateProfileLiveData.value = updateProfileRequestModel
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
