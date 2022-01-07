package com.app.estore.base

data class BaseRequestModel<T>(var data: T) {

    override fun toString(): String {
        return "BaseRequestModel(data=$data)"
    }

}