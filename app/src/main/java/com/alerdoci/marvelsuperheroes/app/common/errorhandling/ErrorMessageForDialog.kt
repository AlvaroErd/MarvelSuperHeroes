package com.alerdoci.marvelsuperheroes.app.common.errorhandling

import android.os.Parcel
import android.os.Parcelable

data class ErrorMessageForDialog(
    val title: String, // Dialog title
    val message: String, // Dialog message
    val debugMessage: String, // Debug message
    val actionCode: Long, // Action that caused the error
    val errorCode: Long, // Error code for support team
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(message)
        dest.writeString(debugMessage)
        dest.writeLong(actionCode)
        dest.writeLong(errorCode)
    }

    //Parcelable interface implementation
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ErrorMessageForDialog> {
        override fun createFromParcel(parcel: Parcel): ErrorMessageForDialog {
            return ErrorMessageForDialog(parcel)
        }

        override fun newArray(size: Int): Array<ErrorMessageForDialog?> {
            return arrayOfNulls(size)
        }
    }
}

