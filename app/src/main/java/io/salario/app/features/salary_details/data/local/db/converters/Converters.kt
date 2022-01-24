package io.salario.app.features.salary_details.data.local.db.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import io.salario.app.core.util.db.JsonParser
import io.salario.app.features.salary_details.domain.model.PaycheckData
import io.salario.app.features.salary_details.domain.model.PersonalData

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {
    @TypeConverter
    fun fromPersonalDataJson(json: String): PersonalData? {
        return jsonParser.fromJson<PersonalData>(
            json,
            object : TypeToken<PersonalData>() {}.type
        )
    }

    @TypeConverter
    fun toPersonalDataJson(personalData: PersonalData): String? {
        return jsonParser.toJson(
            personalData,
            object : TypeToken<PersonalData>() {}.type
        )
    }

    @TypeConverter
    fun fromPaycheckDataJson(json: String): PaycheckData? {
        return jsonParser.fromJson<PaycheckData>(
            json,
            object : TypeToken<PaycheckData>() {}.type
        )
    }

    @TypeConverter
    fun toPaycheckDataJson(paycheckData: PaycheckData): String? {
        return jsonParser.toJson(
            paycheckData,
            object : TypeToken<PaycheckData>() {}.type
        )
    }
}