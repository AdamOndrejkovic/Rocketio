package com.ao.rocketio.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/* Defined class of user for database with required anotations */
@Entity
class BEUser(@PrimaryKey(autoGenerate = true) var id:Int, var name: String, var rank: String, var latitude: Double, var longitude: Double ) {
}