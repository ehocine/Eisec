package com.helic.eisec.utils

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.datepicker.CalendarConstraints


class DateValidator() : CalendarConstraints.DateValidator {
    var startDate: Long = -1
    var endDate: Long = -1

    constructor(startDate: Long = -1, endDate: Long = -1) : this() {
        this.startDate = startDate
        this.endDate = endDate
    }

    constructor(parcel: Parcel) : this() {
        startDate = parcel.readLong()
        endDate = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(startDate)
        parcel.writeLong(endDate)
    }

    override fun isValid(date: Long): Boolean {
        return if (startDate != -1L && endDate != -1L && date in startDate..endDate) {
            // valid date between start and end date
            true
        } else if (startDate == -1L && date < endDate) {
            true
        } else endDate == -1L && date >= startDate
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DateValidator> {
        override fun createFromParcel(parcel: Parcel): DateValidator {
            return DateValidator(parcel)
        }

        override fun newArray(size: Int): Array<DateValidator?> {
            return arrayOfNulls(size)
        }
    }
}