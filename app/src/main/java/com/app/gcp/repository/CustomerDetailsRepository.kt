package com.app.gcp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.gcp.api.APIConstants
import com.app.gcp.api.ApiHelperClass
import com.app.gcp.api.requestmodel.*
import com.app.gcp.api.responsemodel.*
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.utils.Utils
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.ArrayList

class CustomerDetailsRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun callCustomerDetailsUpdateAPI(requestModel: CustomerDetailsRequestModel): LiveData<Event<APIResource<CustomerDetailsResponse>>> {
        val data = MutableLiveData<Event<APIResource<CustomerDetailsResponse>>>()
        data.value = Event(APIResource.loading(null))

//        val baseLoginRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callCustomerDetailsAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val response =
                            responseModel.getResponseModel(CustomerDetailsResponse::class.java)
                        data.value =
                            Event(APIResource.success(response, responseModel.message))
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
        private var instance: CustomerDetailsRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: CustomerDetailsRepository().also { instance = it }
            }
    }
}
