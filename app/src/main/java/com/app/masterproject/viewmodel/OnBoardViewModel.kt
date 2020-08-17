package com.app.masterproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.masterproject.api.requestmodel.ForgotPasswordRequestModel
import com.app.masterproject.api.requestmodel.LoginRequestModel
import com.app.masterproject.api.requestmodel.RegisterRequestModel
import com.app.masterproject.api.responsemodel.LoginResponse
import com.app.masterproject.api.responsemodel.RegisterResponse
import com.app.masterproject.base.APIResource
import com.app.masterproject.custom.Event
import com.app.masterproject.repository.OnBoardRepository

class OnBoardViewModel : ViewModel() {

    private var repository: OnBoardRepository = OnBoardRepository.getInstance()

    private val signInRequestLiveData = MutableLiveData<LoginRequestModel>()
    private val registerRequestLiveData = MutableLiveData<RegisterRequestModel>()
    private val forgotPasswordRequestLiveData = MutableLiveData<ForgotPasswordRequestModel>()

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

    fun callLoginAPI(signInRequestModel: LoginRequestModel) {
        signInRequestLiveData.value = signInRequestModel
    }

    fun callRegisterAPI(registerRequestModel: RegisterRequestModel) {
        registerRequestLiveData.value = registerRequestModel
    }

    fun callForgotPasswordAPI(forgotPasswordRequestModel: ForgotPasswordRequestModel) {
        forgotPasswordRequestLiveData.value = forgotPasswordRequestModel
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
