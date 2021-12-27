package io.salario.app.core.data.model

data class Paycheck(
    val paycheckData: PaycheckData,
    val personalData: PersonalData
)

data class PaycheckData(
    val brutePaymentAmount: BrutePaymentAmount,
    val deductions: Deductions,
    val netPaymentAmount: Int,
    val numOfHours: Int,
    val period: String,
    val sickLeaveDaysData: SickLeaveDaysData,
    val vacationData: VacationData
)

data class BrutePaymentAmount(
    val amount: Int,
    val paymentDetails: List<PaymentDetails>
)

data class PaymentDetails(
    val additionalHours: Int,
    val car: Int,
    val global: Int
)

data class Deductions(
    val amount: Int,
    val deductionDetails: List<DeductionDetails>
)

data class DeductionDetails(
    val healthTax: Int,
    val incomeTax: Int,
    val socialSecurity: Int
)

data class SickLeaveDaysData(
    val added: Int,
    val lastBalance: Int,
    val newBalance: Int,
    val used: Int
)

data class VacationData(
    val added: Int,
    val lastBalance: Int,
    val newBalance: Int,
    val used: Int
)

data class PersonalData(
    val bankAccountData: BankAccountData,
    val firstName: String,
    val id: String,
    val lastName: String,
    val startWorkingDate: String,
    val taxReductionPoints: Double
)

data class BankAccountData(
    val accountNumber: String,
    val bankName: String,
    val branchNumber: String
)