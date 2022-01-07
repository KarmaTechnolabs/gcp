package com.app.estore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.estore.api.APIConstants
import com.app.estore.api.ApiHelperClass
import com.app.estore.api.requestmodel.ForgotPasswordRequestModel
import com.app.estore.api.requestmodel.LoginRequestModel
import com.app.estore.api.requestmodel.RegisterRequestModel
import com.app.estore.api.responsemodel.LoginResponse
import com.app.estore.api.responsemodel.RegisterResponse
import com.app.estore.base.APIResource
import com.app.estore.base.BaseRequestModel
import com.app.estore.custom.Event
import com.app.estore.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

//This repo will be used to handle signin, singup, forgot password like api.
class OnBoardRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun callSignUpAPI(signUpRequestModel: RegisterRequestModel): LiveData<Event<APIResource<RegisterResponse>>> {
        val data = MutableLiveData<Event<APIResource<RegisterResponse>>>()
        data.value = Event(APIResource.loading(null))

        val baseSignUpRequestModel = BaseRequestModel(signUpRequestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callRegisterAPI(baseSignUpRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val signUpResponse =
                            responseModel.getResponseModel(RegisterResponse::class.java)
                        data.value =
                            Event(APIResource.success(signUpResponse, responseModel.message))
                    } else {
                        responseModel.message?.let {
                            data.value = Event(APIResource.error(it, null))
                        }
                    }
                }, { e ->
                    Timber.e(e)
                    data.postValue(Event(APIResource.error(e.localizedMessage ?: "", null)))
                })

            compositeDisposable.add(disposable)
        } else {
            data.value = Event(APIResource.noNetwork())
        }
        return data
    }

    fun callLoginAPI(signInRequestModel: LoginRequestModel): LiveData<Event<APIResource<LoginResponse>>> {
        val data = MutableLiveData<Event<APIResource<LoginResponse>>>()
        data.value = Event(APIResource.loading(null))

        val baseLoginRequestModel = BaseRequestModel(signInRequestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callLoginAPI(baseLoginRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val signInResponse =
                            responseModel.getResponseModel(LoginResponse::class.java)
                        data.value =
                            Event(APIResource.success(signInResponse, responseModel.message))
                    } else {
                        responseModel.message?.let {
                            data.value = Event(APIResource.error(it, null))
                        }
                    }
                }, { e ->
                    Timber.e(e)
                    data.postValue(Event(APIResource.error(e.localizedMessage ?: "", null)))
                })
            compositeDisposable.add(disposable)
        } else {
            data.value = Event(APIResource.noNetwork())
        }
        return data
    }

    fun callForgotPasswordAPI(forgotPasswordModel: ForgotPasswordRequestModel): LiveData<Event<APIResource<Any>>> {
        val data = MutableLiveData<Event<APIResource<Any>>>()
        data.value = Event(APIResource.loading(null))

        val baseRequestModel = BaseRequestModel(forgotPasswordModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callForgotPasswordAPI(baseRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val otpInt =
                            responseModel.getResponseModel(Any::class.java)
                        data.value = Event(APIResource.success(otpInt, responseModel.message))
                    } else {
                        responseModel.message?.let {
                            data.value = Event(APIResource.error(it, null))
                        }
                    }
                }, { e ->
                    Timber.e(e)
                    data.postValue(Event(APIResource.error(e.localizedMessage ?: "", null)))
                })

            compositeDisposable.add(disposable)
        } else {
            data.value = Event(APIResource.noNetwork())
        }
        return data
    }

    fun clearRepo() {
        compositeDisposable.clear()
    }

    companion object {
        @Volatile
        private var instance: OnBoardRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: OnBoardRepository().also { instance = it }
            }
    }
}
