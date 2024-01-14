package com.creative.qrcodescanner.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
data class QRCodeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "raw_data")
    val rawData: String? = null,
    @ColumnInfo(name = "qr_type")
    val qrType: Int = 0,
    @ColumnInfo(name = "qr_details")
    val qrDetails: String? = null,
    @ColumnInfo(name = "scan_date_time_millis")
    val scanDateTimeMillis: Long = 0L,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "is_scanned")
    val isScanned: Boolean = false,
)

@JsonClass(generateAdapter = true)
data class QRCodeURL(
    val url: String? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeWifi(
    val pass: String? = null,
    val ssid: String? = null,
    val encryptionType: Int? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeContact(
    val name: String? = null,
    val organization: String? = null,
    val title: String? = null,
    val email: List<String?>? = null,
    val address: List<String?>? = null,
    val phone: List<String?>? = null,
    val urls: List<String?>? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeCalendar(
    val description: String? = null,
    val start: Long? = null,
    val end: Long? = null,
    val location: String? = null,
    val organizer: String? = null,
    val status: String? = null,
    val summary: String? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeEmail (
    val address: String? = null,
    val body: String? = null,
    val subject: String? = null
)

@JsonClass(generateAdapter = true)
data class QRCodePhone(
    val number: String? = null
)

@JsonClass(generateAdapter = true)
data class QRCodeSMS(
    val number: String? = null,
    val message: String? = null
)