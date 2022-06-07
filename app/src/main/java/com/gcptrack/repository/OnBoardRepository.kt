package com.gcptrack.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gcptrack.api.APIConstants
import com.gcptrack.api.ApiHelperClass
import com.gcptrack.api.ForgotPasswordApiHelperClass
import com.gcptrack.api.requestmodel.ForgotPasswordRequestModel
import com.gcptrack.api.requestmodel.LoginRequestModel
import com.gcptrack.api.requestmodel.RegisterRequestModel
import com.gcptrack.api.requestmodel.TrackingOrderRequestModel
import com.gcptrack.api.responsemodel.LoginResponse
import com.gcptrack.api.responsemodel.RegisterResponse
import com.gcptrack.api.responsemodel.TrackOrderResponse
import com.gcptrack.base.APIResource
import com.gcptrack.base.BaseRequestModel
import com.gcptrack.custom.Event
import com.gcptrack.utils.Utils
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
            val disposable = ApiHelperClass.getAPIClient().callLoginAPI(signInRequestModel)
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

//        val baseRequestModel = BaseRequestModel(forgotPasswordModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ForgotPasswordApiHelperClass.getAPIClient().callForgotPasswordAPI(forgotPasswordModel)
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

    fun callTrackOrderAPI(requestModel: TrackingOrderRequestModel): LiveData<Event<APIResource<TrackOrderResponse>>> {
        val data = MutableLiveData<Event<APIResource<TrackOrderResponse>>>()
        data.value = Event(APIResource.loading(null))

//        val baseRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callTrackOrderAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val otpInt =
                            responseModel.getResponseModel(TrackOrderResponse::class.java)
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
