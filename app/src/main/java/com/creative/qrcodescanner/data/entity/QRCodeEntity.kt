package com.creative.qrcodescanner.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.mlkit.vision.barcode.common.Barcode
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi

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

fun Barcode.toQRCodeEntity(): QRCodeEntity {
    val moshi = Moshi.Builder().build()
    return QRCodeEntity(
        rawData = rawValue,
        qrType = if (rawValue?.matches(Regex("[a-zA-Z]+://.*")) == true) Barcode.TYPE_URL else valueType,
        qrDetails = when (valueType) {
            Barcode.TYPE_TEXT, Barcode.TYPE_UNKNOWN -> {
                if (rawValue?.matches(Regex("[a-zA-Z]+://.*")) == true) {
                    moshi.adapter(QRCodeURL::class.java).toJson(QRCodeURL(url = rawValue))
                } else {
                    "{}"
                }
            }

            Barcode.TYPE_URL -> {
                moshi.adapter(QRCodeURL::class.java).toJson(QRCodeURL(url = url?.url))
            }

            Barcode.TYPE_WIFI -> {
                moshi.adapter(QRCodeWifi::class.java).toJson(
                    QRCodeWifi(
                        pass = wifi?.password,
                        ssid = wifi?.ssid,
                        encryptionType = wifi?.encryptionType
                    )
                )
            }

            Barcode.TYPE_CONTACT_INFO -> {
                moshi.adapter(QRCodeContact::class.java).toJson(
                    QRCodeContact(
                        name = contactInfo?.name?.formattedName,
                        organization = contactInfo?.organization,
                        title = contactInfo?.title,
                        email = contactInfo?.emails?.map { it.address },
                        address = contactInfo?.addresses?.map { it.addressLines.joinToString() },
                        phone = contactInfo?.phones?.map { it.number },
                        urls = contactInfo?.urls
                    )
                )
            }

            Barcode.TYPE_PHONE -> {
                moshi.adapter(QRCodePhone::class.java).toJson(
                    QRCodePhone(
                        number = phone?.number
                    )
                )
            }

            Barcode.TYPE_SMS -> {
                moshi.adapter(QRCodeSMS::class.java).toJson(
                    QRCodeSMS(
                        number = sms?.phoneNumber,
                        message = sms?.message
                    )
                )
            }

            else -> {
                "{}"
            }
        },
        scanDateTimeMillis = System.currentTimeMillis(),
        isFavorite = false,
        isScanned = true
    )
}