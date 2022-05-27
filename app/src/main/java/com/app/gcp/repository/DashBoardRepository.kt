package com.app.gcp.repository

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.gcp.api.APIConstants
import com.app.gcp.api.ApiHelperClass
import com.app.gcp.api.requestmodel.OrderListRequestModel
import com.app.gcp.api.requestmodel.OrderStatusRequestModel
import com.app.gcp.api.responsemodel.CustomersResponse
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.utils.Utils
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DashBoardRepository private constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun callLogoutAPI(): LiveData<Event<APIResource<Any>>> {
        val data = MutableLiveData<Event<APIResource<Any>>>()
        data.value = Event(APIResource.loading(null))
        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callLogoutAPI()
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

    fun callOrderListAPI(requestModel: OrderListRequestModel): LiveData<Event<APIResource<List<OrdersResponse>>>> {
        val data = MutableLiveData<Event<APIResource<List<OrdersResponse>>>>()
        data.value = Event(APIResource.loading(null))

//        val baseLoginRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callOrderListAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val typeOfObjectsList =
                            object : TypeToken<ArrayList<OrdersResponse>>() {}.type
                        val orderResponse =
                            responseModel.getResponseModel(typeOfObjectsList)
                        data.value =
                            Event(APIResource.success(orderResponse, responseModel.message))
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

    fun callCustomerListAPI(requestModel: OrderListRequestModel): LiveData<Event<APIResource<List<CustomersResponse>>>> {
        val data = MutableLiveData<Event<APIResource<List<CustomersResponse>>>>()
        data.value = Event(APIResource.loading(null))

//        val baseLoginRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callCustomerListAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val typeOfObjectsList =
                            object : TypeToken<ArrayList<CustomersResponse>>() {}.type
                        val orderResponse =
                            responseModel.getResponseModel(typeOfObjectsList)
                        data.value =
                            Event(APIResource.success(orderResponse, responseModel.message))
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

    @SuppressLint("CheckResult")
    fun callOrderStageListAPI(): LiveData<Event<APIResource<List<OrderStatusResponse.OrderStatus>>>> {
        val data = MutableLiveData<Event<APIResource<List<OrderStatusResponse.OrderStatus>>>>()
        data.value = Event(APIResource.loading(null))

        if (Utils.isNetworkAvailable()) {
            ApiHelperClass.getAPIClient().getStagesList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }

                    if (responseModel.status == APIConstants.SUCCESS) {
                        val typeOfObjectsList =
                            object : TypeToken<ArrayList<OrderStatusResponse.OrderStatus>>() {}.type
                        val orderResponse =
                            responseModel.getResponseModel(typeOfObjectsList)
                        data.value =
                            Event(APIResource.success(orderResponse, responseModel.message))
                    } else {
                        responseModel.message?.let {
                            data.value = Event(APIResource.error(it, null))
                        }
                    }
                }, { e ->
                    Timber.e(e)
                    data.postValue(Event(APIResource.error(e.localizedMessage ?: "", null)))
                })
        } else {
            data.value = Event(APIResource.noNetwork())
        }
        return data
    }

    fun callOrderStatusAPI(requestModel: OrderStatusRequestModel): LiveData<Event<APIResource<OrderStatusResponse>>> {
        val data = MutableLiveData<Event<APIResource<OrderStatusResponse>>>()
        data.value = Event(APIResource.loading(null))

//        val baseLoginRequestModel = BaseRequestModel(requestModel)

        if (Utils.isNetworkAvailable()) {
            val disposable = ApiHelperClass.getAPIClient().callOrderStatusAPI(requestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ responseModel ->

                    if (responseModel == null) {
                        data.value = Event(APIResource.error("", null))
                        return@subscribe
                    }
                    if (responseModel.status == APIConstants.SUCCESS) {
                        val otpInt =
                            responseModel.getResponseModel(OrderStatusResponse::class.java)
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
        private var instance: DashBoardRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance
                    ?: DashBoardRepository().also { instance = it }
            }
    }
}
