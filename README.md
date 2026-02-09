# ListingsToWordConverter

[<img src="composeApp/src/jvmMain/resources/drawble/icon_ltw.png" align="left"
width="200" hspace="10" vspace="10">](composeApp/src/jvmMain/resources/drawble/icon_ltw.png)

[![rus](https://img.shields.io/badge/lang-ru-green.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README.md)
[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README-en.md)

ListingsToWordConverter это кроссплатформенное приложение для Android и Desktop (JVM), написанное на языке Kotlin, позволяющее конвертировать файлы с кодом из папки в единый Word документ (.docx) в виде пронумерованных таблиц .<br>
<br><br><br><br><br><br>

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

## Использование

1. Нажмите на карточку с плюсом (+).
2. В открывшемся проводнике выберите папку с кодом для конвертации.
3. После появления сообщения о успешном завершении в той же папке с кодом появится Word-документ с таблицами.<br>

> [!TIP]
> Также можно сделать проще - просто перетащите папку из проводника на карточку с плюсом (+), и конвертация также выполнится автоматически.

<br>

<img width="775" height="583" alt="изображение" src="https://github.com/user-attachments/assets/3e09d28f-3b9d-43c8-a895-d6d507400c6a" />

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
|Dockerfile| |

## Игноруруемые директории

- .git
- build
- .idea
- .gradle
- gradle
  
