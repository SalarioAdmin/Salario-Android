package io.salario.app.features.salary_details.domain.model

data class Paycheck(
    val id: String,
    val name: String,
    val address: String,
    val company: String,
    val period: String,
    val numOfDays: Float,
    val numOfHours: Float,
    val brutePaymentAmount: Float,
    val paymentNetAmount: Int
)
