# ListingsToWordConverter

[<img src="composeApp/src/jvmMain/resources/drawble/icon_ltw.png" align="left"
width="200" hspace="10" vspace="10">](composeApp/src/jvmMain/resources/drawble/icon_ltw.png)

[![rus](https://img.shields.io/badge/lang-ru-green.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README.md)
[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README-en.md)

ListingsToWordConverter это кроссплатформенное приложение для Android и Desktop (JVM), написанное на языке Kotlin, позволяющее конвертировать файлы с кодом из папки в единый Word документ (.docx) в виде пронумерованных таблиц .<br>
<br><br><br><br><br><br>

## Поддерживаемые типы

| Язык | Расширение |
|----------|-----------|
| Kotlin | `.kt` |
| Java | `.java` |
| JavaScript/TypeScript | `.js`, `.ts` |
| Python | `.py` |
| C/C++ | `.c`, `.cpp`, `.h` |
| Swift | `.swift` |
| Markdown | `.md` |
| XML | `.xml` |
| YML | `.yml` |
| YML | `.yml` |
|Dockerfile| |

## Функционал приложения

- Позволяет выбрать папку с кодом путем открытия проводника, или путем перетаскивания папки (Только Desktop (JVM))
- При помощи BFS сканирует папку и формирует единый единый Word документ (.docx) `folderWithCode/listing_YYYYMMDD_HHMMSS.docx`
- Word документ содержит таблицы с заголовками "Листинг N — filename.ext", оформлены как Times New Roman 14 курсив, нулевые отступы и единичный межстрочный интервал
- Содержимое таблиц оформлено как Times New Roman 10, нулевые отступы и единичный межстрочный интервал

<br>
<img width="1351" height="478" alt="изображение" src="https://github.com/user-attachments/assets/13871568-2f90-4b40-b8bb-93798b126661" />
<br>

## Технологический стек
- **Kotlin** – Основной язык, на котором разработано прилжение
- **Compose Multiplatform** - набор инструментов для построения UI
- **Clean Architecture**
- **MVI** - Архитектурный паттерн
- **Apache POI OOXML** - для генерации Word документа с форматированием
- **Koin** - для внедрения зависимостей (DI)  
- **Coroutines + Flow**
- **Okio** - для IO операций

## Установка 

### Установка на Android
1. Скачать apk из последнего релиза;
2. Установить на устройство.

### Установка на Desktop (JVM)

1. Скачать jar из последнего релиза
2. Запустить в cmd
  ```shell
  java -jar ListingsToWordConvertor.jar
  ```






---

  
