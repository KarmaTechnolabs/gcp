package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.ForgotPasswordRequestModel
import com.app.gcp.api.requestmodel.LoginRequestModel
import com.app.gcp.api.requestmodel.RegisterRequestModel
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.LoginResponse
import com.app.gcp.api.responsemodel.RegisterResponse
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.OnBoardRepository

class OnBoardViewModel : ViewModel() {

    private var repository: OnBoardRepository = OnBoardRepository.getInstance()

    private val signInRequestLiveData = MutableLiveData<LoginRequestModel>()
    private val registerRequestLiveData = MutableLiveData<RegisterRequestModel>()
    private val forgotPasswordRequestLiveData = MutableLiveData<ForgotPasswordRequestModel>()
    private val trackingOrderRequestLiveData = MutableLiveData<TrackingOrderRequestModel>()

    val loginResponse: LiveData<Event<APIResource<LoginResponse>>> =
        signInRequestLiveData.switchMap {
            repository.callLoginAPI(it)
        }

    val registerResponse: LiveData<Event<APIResource<RegisterResponse>>> =
        registerRequestLiveData.switchMap {
            repository.callSignUpAPI(it)
        }

    val forgotPasswordResponse: LiveData<Event<APIResource<Any>>> =
        forgotPasswordRequestLiveData.switchMap {
            repository.callForgotPasswordAPI(it)
        }

    val trackingOrderResponse: LiveData<Event<APIResource<TrackOrderResponse>>> =
        trackingOrderRequestLiveData.switchMap {
            repository.callTrackOrderAPI(it)
        }

    fun callLoginAPI(request: LoginRequestModel) {
        signInRequestLiveData.value = request
    }

    fun callRegisterAPI(registerRequestModel: RegisterRequestModel) {
        registerRequestLiveData.value = registerRequestModel
    }

    fun callForgotPasswordAPI(forgotPasswordRequestModel: ForgotPasswordRequestModel) {
        forgotPasswordRequestLiveData.value = forgotPasswordRequestModel
    }

    fun callTrackingOrderAPI(request: TrackingOrderRequestModel) {
        trackingOrderRequestLiveData.value = request
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
