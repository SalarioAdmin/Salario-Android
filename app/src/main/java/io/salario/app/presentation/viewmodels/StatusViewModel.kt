package io.salario.app.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.salario.app.data.model.Paycheck
import io.salario.app.data.repo.SalaryDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class StatusViewModel : ViewModel() {
    private val repo = SalaryDataRepository()
    val paycheckFlow = MutableSharedFlow<Paycheck>()

    init {
        getPaycheck()
    }

    private fun getPaycheck() {
//        viewModelScope.launch {
//            val paycheck = repo.getPaycheckFromRemote()
//            paycheckFlow.emit(paycheck)
//        }
    }
}