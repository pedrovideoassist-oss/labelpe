package com.validatech.services

import com.validatech.data.entities.LabelEntity
import com.validatech.data.entities.RestaurantSettingsEntity

class FakePrinterService : PrinterService {
    override fun printLabel(label: LabelEntity, settings: RestaurantSettingsEntity, onComplete: () -> Unit) {
        // TODO: Integrate SUNMI printer SDK here.
        onComplete.invoke()
    }
}
