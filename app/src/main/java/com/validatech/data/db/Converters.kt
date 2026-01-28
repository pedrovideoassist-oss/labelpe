package com.validatech.data.db

import androidx.room.TypeConverter
import com.validatech.domain.enums.*
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromConservation(type: ConservationType?): String? = type?.name

    @TypeConverter
    fun toConservation(value: String?): ConservationType? = value?.let { ConservationType.valueOf(it) }

    @TypeConverter
    fun fromPortionUnit(unit: PortionUnit?): String? = unit?.name

    @TypeConverter
    fun toPortionUnit(value: String?): PortionUnit? = value?.let { PortionUnit.valueOf(it) }

    @TypeConverter
    fun fromBaseDateType(type: BaseDateType?): String? = type?.name

    @TypeConverter
    fun toBaseDateType(value: String?): BaseDateType? = value?.let { BaseDateType.valueOf(it) }

    @TypeConverter
    fun fromLabelStatus(status: LabelStatus?): String? = status?.name

    @TypeConverter
    fun toLabelStatus(value: String?): LabelStatus? = value?.let { LabelStatus.valueOf(it) }

    @TypeConverter
    fun fromLabelSize(size: LabelSize?): String? = size?.name

    @TypeConverter
    fun toLabelSize(value: String?): LabelSize? = value?.let { LabelSize.valueOf(it) }

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }
}
