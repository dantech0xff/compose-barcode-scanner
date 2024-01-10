package com.creative.qrcodescanner.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
