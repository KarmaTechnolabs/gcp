package com.app.gcp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.app.gcp.api.requestmodel.OrderListRequestModel
import com.app.gcp.api.requestmodel.TrackingOrderRequestModel
import com.app.gcp.api.responsemodel.LoginResponse
import com.app.gcp.api.responsemodel.OrderStatusResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.base.APIResource
import com.app.gcp.custom.Event
import com.app.gcp.repository.DashBoardRepository

class DashBoardViewModel : ViewModel() {

    private var repository: DashBoardRepository = DashBoardRepository.getInstance()
    private val orderListRequestLiveData = MutableLiveData<OrderListRequestModel>()
    private val orderStatusRequestLiveData = MutableLiveData<TrackingOrderRequestModel>()
    var orderStatusArray = mutableListOf<OrderStatusResponse>()

    val orderListResponse: LiveData<Event<APIResource<List<OrdersResponse>>>> =
        orderListRequestLiveData.switchMap {
            repository.callOrderListAPI(it)
        }

    val orderStatusListResponse: LiveData<Event<APIResource<List<OrderStatusResponse>>>> =
        orderStatusRequestLiveData.switchMap {
            repository.callOrderStatusAPI()
        }

    //    val callLogoutAPI: LiveData<Event<APIResource<Any>>> = repository.callLogoutAPI()
    fun callOrderListAPI(request: OrderListRequestModel) {
        orderListRequestLiveData.value = request
    }

    fun callOrderStatusAPI(request: TrackingOrderRequestModel) {
        orderStatusRequestLiveData.value = request
    }

    override fun onCleared() {
        super.onCleared()
        repository.clearRepo()
    }
}
