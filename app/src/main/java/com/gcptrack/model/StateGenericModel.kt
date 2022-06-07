package com.gcptrack.model


import android.os.Parcel
import android.os.Parcelable

data class StateGenericModel(
    val id: Int,
    val title: String?,
    var isSelected: Boolean = false,
    var selectedPosition: Int = -1,
    val lastSelectedPosition: Int = -1
) : Parcelable {
    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<StateGenericModel> =
            object : Parcelable.Creator<StateGenericModel> {
                override fun createFromParcel(source: Parcel): StateGenericModel =
                    StateGenericModel(source)

                override fun newArray(size: Int): Array<StateGenericModel?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel): this(
    source.readInt(),
    source.readString(),
    1 == source.readInt(),
    source.readInt(),
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(title)
        writeInt((if (isSelected) 1 else 0))
        writeInt(selectedPosition)
        writeInt(lastSelectedPosition)
    }
}