package com.validatech.domain.models

import com.validatech.domain.enums.ConservationType
import java.time.LocalDateTime

 data class LabelPreview(
    val restaurantName: String,
    val productName: String,
    val conservationType: ConservationType,
    val portionQty: Double,
    val portionUnit: String,
    val baseDateTime: LocalDateTime,
    val expiryDateTime: LocalDateTime,
    val shortCode: String,
    val responsibleName: String
)
