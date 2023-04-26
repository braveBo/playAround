package com.redfin.hw.data

import android.os.Parcel
import android.os.Parcelable

data class FoodTruck(
    val dayorder: Int,
    val dayofweekstr: String,
    val starttime: String,
    val endtime: String,
    val permit: String,
    val location: String,
    val locationdesc: String?,
    val optionaltext: String,
    val locationid: Int,
    val scheduleid: Int?,
    val start24: String,
    val end24: String,
    val cnn: Int,
    val addr_date_create: String,
    val addr_date_modified: String,
    val block: String,
    val lot: String,
    val coldtruck: String,
    val applicant: String,
    val x: Double,
    val y: Double,
    val latitude: Double,
    val longitude: Double,
    val location_2: Location
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readValue(Int::class.java.classLoader) as Int?,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readParcelable(Location::class.java.classLoader)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dayorder)
        parcel.writeString(dayofweekstr)
        parcel.writeString(starttime)
        parcel.writeString(endtime)
        parcel.writeString(permit)
        parcel.writeString(location)
        parcel.writeString(locationdesc)
        parcel.writeString(optionaltext)
        parcel.writeInt(locationid)
        parcel.writeValue(scheduleid)
        parcel.writeString(start24)
        parcel.writeString(end24)
        parcel.writeInt(cnn)
        parcel.writeString(addr_date_create)
        parcel.writeString(addr_date_modified)
        parcel.writeString(block)
        parcel.writeString(lot)
        parcel.writeString(coldtruck)
        parcel.writeString(applicant)
        parcel.writeDouble(x)
        parcel.writeDouble(y)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
        parcel.writeParcelable(location_2, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FoodTruck> {
        override fun createFromParcel(parcel: Parcel): FoodTruck {
            return FoodTruck(parcel)
        }

        override fun newArray(size: Int): Array<FoodTruck?> {
            return arrayOfNulls(size)
        }
    }
}
