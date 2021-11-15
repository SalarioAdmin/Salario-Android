package io.salario.app.application

import android.app.Application

class SalarioApplication : Application() {
    companion object {
        lateinit var INSTANCE: Application
    }

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this
    }
}