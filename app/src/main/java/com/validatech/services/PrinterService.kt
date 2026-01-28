package com.validatech.services

import com.validatech.data.entities.LabelEntity
import com.validatech.data.entities.RestaurantSettingsEntity

interface PrinterService {
    fun printLabel(label: LabelEntity, settings: RestaurantSettingsEntity, onComplete: () -> Unit)
}
