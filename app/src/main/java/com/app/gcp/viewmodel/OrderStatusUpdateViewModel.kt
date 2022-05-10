package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.OrderStatusUpdateRequestModel
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.EmptyResponse
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.DashBoardRepository
import com.app.gcp.repository.OrderStatusRepository

class OrderStatusUpdateViewModel : ViewModel() {

    private var repository: OrderStatusRepository = OrderStatusRepository.getInstance()
    private val orderStatusRequestLiveData = MutableLiveData<TrackingOrderRequestModel>()
    var orderStatusArray = mutableListOf<OrderStatusResponse>()
    val orderStatusResponse = MutableLiveData<OrdersResponse>()

    val orderStatusListResponse: LiveData<Event<APIResource<List<OrderStatusResponse>>>> =
        orderStatusRequestLiveData.switchMap {
            repository.callOrderStatusAPI(it)
        }

    fun callOrderStatusUpdateAPI(request: OrderStatusUpdateRequestModel): LiveData<Event<APIResource<EmptyResponse>>> {
        return repository.callOrderStatusUpdateAPI(request)
    }



    fun callOrderStatusAPI(request: TrackingOrderRequestModel) {
        orderStatusRequestLiveData.value = request
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
