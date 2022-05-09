package com.app.gcp.api

import com.app.gcp.api.requestmodel.*
import com.app.gcp.api.responsemodel.LoginResponse
import com.app.gcp.api.responsemodel.OrdersResponse
import com.app.gcp.api.responsemodel.RegisterResponse
import com.app.gcp.api.responsemodel.TrackOrderResponse
import com.app.gcp.base.BaseRequestModel
import com.app.gcp.base.BaseResponseModel
import com.app.gcp.model.*
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

    @POST(APIConstants.API_FORGOT_PASSWORD)
    fun callForgotPasswordAPI(@Body requestBody: BaseRequestModel<ForgotPasswordRequestModel>): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_TRACK_ORDER)
    fun callTrackOrderAPI(@Body requestBody: TrackingOrderRequestModel): Single<BaseResponseModel<TrackOrderResponse>>

    @POST(APIConstants.API_LOGOUT)
    fun callLogoutAPI(): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_CHANGE_PASSWORD)
    fun callChangePasswordAPI(@Body requestBody: BaseRequestModel<ChangePasswordRequestModel>): Single<BaseResponseModel<Any>>

    @POST(APIConstants.API_GET_STATE)
    fun getStateList(@Body requestBody: BaseRequestModel<StateRequestModel>): Single<BaseResponseModel<List<StateModel>>>

    @Multipart
    @POST(APIConstants.API_UPDATE_PROFILE)
    fun updateProfile(@Part profile_photo: MultipartBody.Part?, @Part("data") data: RequestBody)
            : Single<BaseResponseModel<LoginResponse>>

}