package io.salario.app.features.salary_details.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import io.salario.app.features.salary_details.data.local.db.entity.PaycheckEntity
import io.salario.app.features.salary_details.domain.model.PaycheckData
import io.salario.app.features.salary_details.domain.model.PersonalData

data class PaycheckDto(
    @SerializedName("_id")
    val id: String,
    val personalData: PersonalData,
    val paycheckData: PaycheckData
) {
    fun toPaycheckEntity(): PaycheckEntity {
        return PaycheckEntity(
            id,
            personalData,
            paycheckData
        )
    }
}
