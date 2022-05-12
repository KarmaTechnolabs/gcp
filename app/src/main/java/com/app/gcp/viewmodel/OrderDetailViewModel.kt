package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.OrderDetailsRequestModel
import com.app.gcp.api.requestmodel.OrderStatusUpdateRequestModel
import com.app.gcp.api.responsemodel.EmptyResponse
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersDetailsResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.OrderDetailsRepository

class OrderDetailViewModel : ViewModel() {

    private var repository: OrderDetailsRepository = OrderDetailsRepository.getInstance()
    private val orderDetailRequestLiveData = MutableLiveData<OrderDetailsRequestModel>()
    val orderStatusResponse = MutableLiveData<OrdersResponse>()

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
