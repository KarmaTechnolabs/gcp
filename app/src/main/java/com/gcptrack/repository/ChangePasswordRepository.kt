package com.gcptrack.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gcptrack.api.APIConstants
import com.gcptrack.api.ApiHelperClass
import com.gcptrack.api.requestmodel.ChangePasswordRequestModel
import com.gcptrack.api.responsemodel.EmptyResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ChangePasswordRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun callChangePasswordAPI(requestModel: ChangePasswordRequestModel): LiveData<Event<APIResource<EmptyResponse>>> {
        val data = MutableLiveData<Event<APIResource<EmptyResponse>>>()
        data.value = Event(APIResource.loading(null))

//        val baseRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callChangePasswordAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        data.value = Event(APIResource.success(responseModel,responseModel.message))
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
        private var instance: ChangePasswordRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: ChangePasswordRepository().also { instance = it }
            }
    }
}
