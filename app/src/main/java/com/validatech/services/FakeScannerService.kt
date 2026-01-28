package com.validatech.services

class FakeScannerService : ScannerService {
    override fun startScan(onResult: (String) -> Unit) {
        // TODO: Integrate SUNMI scanner SDK here.
        onResult.invoke("")
    }
}
