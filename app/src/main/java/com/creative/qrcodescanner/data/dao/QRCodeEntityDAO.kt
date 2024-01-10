package com.creative.qrcodescanner.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.creative.qrcodescanner.data.entity.QRCodeEntity

/**
 * Created by dan on 10/01/2024
 *
 * Copyright © 2024 1010 Creative. All rights reserved.
 */

@Dao
interface QRCodeEntityDAO {

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    suspend fun insertQRCodeEntity(qrCodeEntity: QRCodeEntity)

    @Query("SELECT * FROM QRCodeEntity ORDER BY scan_date_time_millis DESC")
    suspend fun getAllQRCodeEntity(): List<QRCodeEntity>

    @Query("SELECT * FROM QRCodeEntity WHERE id = :id")
    suspend fun getQRCodeEntityById(id: Int): QRCodeEntity

    // Query to get all QRCodeEntity that is not favorite
    @Query("SELECT * FROM QRCodeEntity WHERE is_favorite = 0 ORDER BY scan_date_time_millis DESC")
    suspend fun getAllQRCodeEntityNotFavorite(): List<QRCodeEntity>

    // Query to get all QRCodeEntity that is favorite
    @Query("SELECT * FROM QRCodeEntity WHERE is_favorite = 1 ORDER BY scan_date_time_millis DESC")
    suspend fun getAllQRCodeEntityFavorite(): List<QRCodeEntity>

    // Query to get all QRCodeEntity that is not scanned
    @Query("SELECT * FROM QRCodeEntity WHERE is_scanned = 0 ORDER BY scan_date_time_millis DESC")
    suspend fun getAllQRCodeEntityNotScanned(): List<QRCodeEntity>

    // Query to get all QRCodeEntity that is scanned
    @Query("SELECT * FROM QRCodeEntity WHERE is_scanned = 1 ORDER BY scan_date_time_millis DESC")
    suspend fun getAllQRCodeEntityScanned(): List<QRCodeEntity>

    // delete 1 QRCodeEntity
    @Query("DELETE FROM QRCodeEntity WHERE id = :id")
    suspend fun deleteQRCodeEntity(id: Int)

    // delete 1 item QRCodeEntity
    @Delete
    suspend fun deleteQRCodeEntity(qrCodeEntity: QRCodeEntity)

    // update 1 item QRCodeEntity
    @Upsert
    suspend fun updateQRCodeEntity(qrCodeEntity: QRCodeEntity)
}