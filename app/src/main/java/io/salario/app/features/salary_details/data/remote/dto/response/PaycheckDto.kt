package io.salario.app.features.salary_details.data.remote.dto.response

import io.salario.app.features.salary_details.domain.model.Paycheck

data class PaycheckDto(
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

data class PersonalData(
    val id: String,
    val name: String,
    val address: String,
    val bankAccountData: BankAccountData,
    val taxReductionPoints: TaxReduction
)

data class BankAccountData(
    val bankName: String,
    val branchNumber: String,
    val accountNumber: String
)

data class TaxReduction(
    val normalPoints: String,
    val extraPoints: String,
    val sum: String
)

data class PaycheckData(
    val company: String,
    val period: String,
    val startWorkingDate: String,
    val numOfDays: Float,
    val numOfHours: Float,
    val payments: Payments,
    val deductions: Deductions,
    val vacationData: VacationData,
    val sickLeaveDaysData: SickLeaveDaysData,
    val marginalTaxRate: String,
    val paymentNetAmount: Int
)

data class Payments(
    val netPaymentAmount: Float,
    val brutePaymentAmount: Float,
    val payments: List<PaymentDetails>
)

data class PaymentDetails(
    val description: String,
    val quantity: Float,
    val tariff: String,
    val incarnation: String,
    val taxValue: String,
    val amount: String
)

data class Deductions(
    val totalAmount: Float,
    val deductions: List<DeductionDetails>
)

data class DeductionDetails(
    val description: String,
    val amount: String
)

data class VacationData(
    val lastBalance: Float,
    val added: Float,
    val used: Float,
    val newBalance: Float
)

data class SickLeaveDaysData(
    val lastBalance: Float,
    val added: Float,
    val used: Float,
    val newBalance: Float
)
