package com.validatech.data.repository

data class Repositories(
    val operatorRepository: OperatorRepository,
    val roleRepository: RoleRepository,
    val locationRepository: LocationRepository,
    val categoryRepository: CategoryRepository,
    val productRepository: ProductRepository,
    val labelRepository: LabelRepository,
    val settingsRepository: SettingsRepository
)
