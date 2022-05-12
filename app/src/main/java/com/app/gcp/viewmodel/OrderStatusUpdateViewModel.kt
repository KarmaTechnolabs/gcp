package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.gcp.api.requestmodel.OrderStatusUpdateRequestModel
import com.app.gcp.api.responsemodel.EmptyResponse
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.OrderStatusRepository

class OrderStatusUpdateViewModel : ViewModel() {

    private var repository: OrderStatusRepository = OrderStatusRepository.getInstance()

    var orderStatusArray = mutableListOf<OrderStatusResponse>()
    val orderStatusResponse = MutableLiveData<OrdersResponse>()

    fun callOrderStatusUpdateAPI(request: OrderStatusUpdateRequestModel): LiveData<Event<APIResource<EmptyResponse>>> {
        return repository.callOrderStatusUpdateAPI(request)
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
