package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gcptrack.api.responsemodel.LoginResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.model.*
import com.gcptrack.repository.ProfileRepository

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
