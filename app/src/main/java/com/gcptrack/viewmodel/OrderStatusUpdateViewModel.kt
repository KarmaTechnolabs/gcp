package com.gcptrack.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gcptrack.api.requestmodel.OrderStatusUpdateRequestModel
import com.gcptrack.api.responsemodel.EmptyResponse
import com.gcptrack.api.responsemodel.OrderStatusResponse
import com.gcptrack.api.responsemodel.OrdersResponse
import com.gcptrack.base.APIResource
import com.gcptrack.custom.Event
import com.gcptrack.repository.OrderStatusRepository

class OrderStatusUpdateViewModel : ViewModel() {

    private var repository: OrderStatusRepository = OrderStatusRepository.getInstance()

    var orderStatusArray = mutableListOf<OrderStatusResponse.OrderStatus>()
    val orderStatusResponse = MutableLiveData<OrdersResponse>()

    fun callOrderStatusUpdateAPI(request: OrderStatusUpdateRequestModel): LiveData<Event<APIResource<EmptyResponse>>> {
        return repository.callOrderStatusUpdateAPI(request)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
