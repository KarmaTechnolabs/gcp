package com.gcptrack.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gcptrack.api.APIConstants
import com.gcptrack.api.ApiHelperClass
import com.gcptrack.api.responsemodel.LoginResponse
import com.gcptrack.base.APIResource
import com.gcptrack.base.BaseRequestModel
import com.gcptrack.custom.Event
import com.gcptrack.model.*
import com.gcptrack.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber
import java.io.File
import java.util.ArrayList

class ProfileRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getStateListApi(signUpRequestModel: StateRequestModel): LiveData<Event<APIResource<List<StateModel>>>> {
        val data = MutableLiveData<Event<APIResource<List<StateModel>>>>()
        data.value = Event(APIResource.loading(null))

        val baseSignUpRequestModel = BaseRequestModel(signUpRequestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().getStateList(baseSignUpRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val typeOfObjectsList = object : TypeToken<ArrayList<StateModel>>() {}.type
                        val stateResponse =
                            responseModel.getResponseModel(typeOfObjectsList)
                        data.value = Event(APIResource.success(stateResponse, responseModel.message))
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

    fun updateProfile(requestModel: UpdateProfileRequestModel): LiveData<Event<APIResource<LoginResponse>>> {
        val data = MutableLiveData<Event<APIResource<LoginResponse>>>()
        data.value = Event(APIResource.loading(null))
        var part: MultipartBody.Part? = null
        val baseLoginRequestModel = BaseRequestModel(requestModel)

        if (!requestModel.profilePhoto.isNullOrEmpty()){

            val file = File(requestModel.profilePhoto)
            // Create a request body with file and image media type
            val fileReqBody = RequestBody.create("image/*".toMediaTypeOrNull(), file)

            part = MultipartBody.Part.createFormData("profile_image", file.name, fileReqBody)
        }

        val dataRequestbody =  RequestBody.create("multipart/form-data".toMediaTypeOrNull(), Gson().toJson(baseLoginRequestModel))

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().updateProfile(part,dataRequestbody)
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

    fun clearRepo() {
        compositeDisposable.clear()
    }
    companion object {
        @Volatile
        private var instance: ProfileRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: ProfileRepository().also { instance = it }
            }
    }
}
