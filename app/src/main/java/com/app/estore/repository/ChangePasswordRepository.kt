package com.app.estore.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.estore.api.APIConstants
import com.app.estore.api.ApiHelperClass
import com.app.estore.api.requestmodel.ChangePasswordRequestModel
import com.app.estore.base.APIResource
import com.app.estore.base.BaseRequestModel
import com.app.estore.custom.Event
import com.app.estore.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ChangePasswordRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun callChangePasswordAPI(requestModel: ChangePasswordRequestModel): LiveData<Event<APIResource<Any>>> {
        val data = MutableLiveData<Event<APIResource<Any>>>()
        data.value = Event(APIResource.loading(null))

        val baseRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callChangePasswordAPI(baseRequestModel)
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
        private var instance: ChangePasswordRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: ChangePasswordRepository().also { instance = it }
            }
    }
}
