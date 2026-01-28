package com.validatech.services

interface ScannerService {
    fun startScan(onResult: (String) -> Unit)
}
