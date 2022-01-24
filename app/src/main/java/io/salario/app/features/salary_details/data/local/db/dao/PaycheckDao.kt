package io.salario.app.features.salary_details.data.local.db.dao

import androidx.room.*
import io.salario.app.features.salary_details.data.local.db.entity.PaycheckEntity

@Dao
interface PaycheckDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaycheck(paycheck: PaycheckEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaychecks(paycheck: List<PaycheckEntity>)

    @Query("SELECT * FROM paycheck WHERE id = :id")
    suspend fun getPaycheckById(id: String): PaycheckEntity

    @Query("SELECT * FROM paycheck")
    suspend fun getPaychecks(): List<PaycheckEntity>

    @Query("DELETE FROM paycheck WHERE id IN(:ids)")
    suspend fun deletePaychecksByIds(ids: List<String>)

    @Query("DELETE FROM paycheck WHERE id = :id")
    suspend fun deletePaycheck(id: String)
}