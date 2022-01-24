package io.salario.app.features.salary_details.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.salario.app.features.salary_details.domain.model.Paycheck
import io.salario.app.features.salary_details.domain.model.PaycheckData
import io.salario.app.features.salary_details.domain.model.PersonalData

@Entity(tableName = "paycheck")
data class PaycheckEntity(
    @PrimaryKey val id: String,
    val personalData: PersonalData,
    val paycheckData: PaycheckData
) {
    fun toPaycheck(): Paycheck {
        return Paycheck(
            personalData.id,
            personalData.name,
            personalData.address,
            paycheckData.company,
            paycheckData.period,
            paycheckData.numOfDays,
            paycheckData.numOfHours,
            paycheckData.payments.brutePaymentAmount,
            paycheckData.paymentNetAmount
        )
    }
}
