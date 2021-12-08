package io.salario.app.data.source.local.assets

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import io.salario.app.application.SalarioApplication
import io.salario.app.data.model.Paycheck
import java.nio.charset.StandardCharsets

class LocalAssetsDataSource {
    private lateinit var paycheck: Paycheck

    companion object {
        const val FILE_NAME = "PaycheckData.json"
    }

    init {
        loadPaycheckDataFromLocalAsset(SalarioApplication.INSTANCE, fileName = FILE_NAME)
    }

    fun getPaycheckData(): Paycheck {
        return paycheck
    }

    private fun loadPaycheckDataFromLocalAsset(app: Application, fileName: String) {
        lateinit var jsonString: String
        try {
            val inputStream = app.assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            Log.e(this.javaClass.name, "Exception occurred while reading json from assets")
        }

        paycheck = Gson().fromJson(jsonString, Paycheck::class.java)
    }
}