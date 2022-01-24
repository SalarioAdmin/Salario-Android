package io.salario.app.features.salary_details.data.local.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.salario.app.features.salary_details.data.local.db.converters.Converters
import io.salario.app.features.salary_details.data.local.db.dao.PaycheckDao
import io.salario.app.features.salary_details.data.local.db.entity.PaycheckEntity

@Database(
    entities = [PaycheckEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class SalaryDatabase : RoomDatabase() {
    abstract val dao: PaycheckDao

    companion object {
        const val DATABASE_NAME = "salary_db"
    }
}