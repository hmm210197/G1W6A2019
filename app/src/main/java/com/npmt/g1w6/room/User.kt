package com.npmt.g1w6.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName="User")
data class User(
    @PrimaryKey(autoGenerate=true) var id:Int? = null,
    var name:String
):Parcelable