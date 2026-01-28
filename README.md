# ValidaTech

App Android 11 (API 30) para etiquetagem e controle de validade em dispositivos SUNMI V2s.

## Stack
- Kotlin + Jetpack Compose (Material 3)
- MVVM (ViewModels simples por tela)
- Room (offline-first)
- Navigation Compose

## Como rodar no emulador
1. Abra o projeto no Android Studio.
2. Aguarde o Gradle sincronizar.
3. Crie um emulador Android 11 (API 30).
4. Execute o app.

## Integração SUNMI (Scanner e Impressora)
As interfaces estão em:
- `services/ScannerService.kt`
- `services/PrinterService.kt`

As implementações fake ficam em:
- `services/FakeScannerService.kt`
- `services/FakePrinterService.kt`

Para integrar o SDK SUNMI, substitua as chamadas nos métodos `startScan` e `printLabel`.

## Estrutura
- `domain`: modelos e enums
- `data`: Room entities/DAO/repositories
- `ui`: telas/components
- `services`: interfaces de scanner/impressora
- `util`: helpers (datas, CSV, PIN)

## CSV
Colunas obrigatórias:
`sku,nome_produto,validade_dias,categoria,conservacao,porcao_qtd,porcao_unid,data_base`

Colunas opcionais:
`marca_fornecedor,registro,observacao,local_padrao`

## Seed inicial
- Roles Admin e Operador
- Admin padrão (PIN 2323)
- Joana (PIN 1234)
- Jorge (PIN 2323)
- Categorias e locais de exemplo
