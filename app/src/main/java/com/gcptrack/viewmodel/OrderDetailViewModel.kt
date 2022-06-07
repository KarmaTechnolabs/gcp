package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.gcptrack.api.requestmodel.OrderDetailsRequestModel
import com.gcptrack.api.responsemodel.OrdersDetailsResponse
import com.gcptrack.api.responsemodel.OrdersResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.OrderDetailsRepository

class OrderDetailViewModel : ViewModel() {

    private var repository: OrderDetailsRepository = OrderDetailsRepository.getInstance()
    private val orderDetailRequestLiveData = MutableLiveData<OrderDetailsRequestModel>()
    val orderDetailResponseLiveData = MutableLiveData<OrdersDetailsResponse>()
    val orderStatusResponse = MutableLiveData<OrdersResponse>()
    val isCustomer = MutableLiveData<Boolean>(true)

    val orderDetailsResponse: LiveData<Event<APIResource<OrdersDetailsResponse>>> =
        orderDetailRequestLiveData.switchMap {
            repository.callOrderDetailsUpdateAPI(it)
        }

    fun callOrderDetailAPI(request: OrderDetailsRequestModel) {
        orderDetailRequestLiveData.value = request
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
