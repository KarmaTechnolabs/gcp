package com.gcptrack.api

import com.gcptrack.api.requestmodel.*
import com.gcptrack.api.responsemodel.*
import com.gcptrack.base.BaseRequestModel
import com.gcptrack.base.BaseResponseModel
import com.gcptrack.model.*
import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface ApiCallInterface {

    @POST(APIConstants.API_REGISTER)
    fun callRegisterAPI(@Body requestBody: BaseRequestModel<RegisterRequestModel>): Single<BaseResponseModel<RegisterResponse>>

    @POST(APIConstants.API_SIGN_IN)
    fun callLoginAPI(@Body requestBody: LoginRequestModel): Single<BaseResponseModel<LoginResponse>>

    @POST(APIConstants.API_ORDERS)
    fun callOrderListAPI(@Body requestBody: OrderListRequestModel): Single<BaseResponseModel<List<OrdersResponse>>>

    @POST(APIConstants.API_CLIENTS)
    fun callCustomerListAPI(@Body requestBody: OrderListRequestModel): Single<BaseResponseModel<List<CustomersResponse>>>

    @POST(APIConstants.API_ORDER_STATUS)
    fun callOrderStatusAPI(@Body requestBody: OrderStatusRequestModel): Single<BaseResponseModel<OrderStatusResponse>>

    @POST(APIConstants.API_UPDATE_ORDER)
    fun callOrderStatusUpdateAPI(@Body requestBody: OrderStatusUpdateRequestModel): Single<EmptyResponse>

    @POST(APIConstants.API_ORDER_DETAILS)
    fun callOrderDetailsAPI(@Body requestBody: OrderDetailsRequestModel): Single<BaseResponseModel<OrdersDetailsResponse>>

    @POST(APIConstants.API_CUSTOMER_DETAILS)
    fun callCustomerDetailsAPI(@Body requestBody: CustomerDetailsRequestModel): Single<BaseResponseModel<CustomerDetailsResponse>>

    @POST(APIConstants.API_FORGOT_PASSWORD)
    fun callForgotPasswordAPI(@Body requestBody: ForgotPasswordRequestModel): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_TRACK_ORDER)
    fun callTrackOrderAPI(@Body requestBody: TrackingOrderRequestModel): Single<BaseResponseModel<TrackOrderResponse>>

    @POST(APIConstants.API_LOGOUT)
    fun callLogoutAPI(): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_CHANGE_PASSWORD)
    fun callChangePasswordAPI(@Body requestBody: ChangePasswordRequestModel): Single<EmptyResponse>

    @POST(APIConstants.API_GET_STATE)
    fun getStateList(@Body requestBody: BaseRequestModel<StateRequestModel>): Single<BaseResponseModel<List<StateModel>>>

    @GET(APIConstants.API_ORDER_STAGES)
    fun getStagesList(): Single<BaseResponseModel<List<OrderStatusResponse.OrderStatus>>>

    @Multipart
    @POST(APIConstants.API_UPDATE_PROFILE)
    fun updateProfile(@Part profile_photo: MultipartBody.Part?, @Part("data") data: RequestBody)
            : Single<BaseResponseModel<LoginResponse>>

}