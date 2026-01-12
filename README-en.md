# ListingsToWordConverter

[<img src="composeApp/src/jvmMain/resources/drawble/icon_ltw.png" align="left"
width="200" hspace="10" vspace="10">](composeApp/src/jvmMain/resources/drawble/icon_ltw.png)

[![rus](https://img.shields.io/badge/lang-ru-green.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README.md)
[![en](https://img.shields.io/badge/lang-en-red.svg)](https://github.com/Leturgone/ListingsToWordConverter/blob/main/README-en.md)

ListingsToWordConverter is a cross-platform application for Android and Desktop (JVM), written in Kotlin, that allows converting code files from a folder into a single Word document (.docx) in the form of numbered tables.<br>
<br><br><br><br><br><br>


## Application Functionality

- Allows selecting a code folder via file explorer or by dragging and dropping the folder (Desktop (JVM) only).
- Uses BFS to scan the folder and generates a single Word document (.docx) `folderWithCode/listing_YYYYMMDD_HHMMSS.docx`
- The Word document contains tables with headers "Листинг N — filename.ext", ormatted in Times New Roman 14 italic, with zero margins and single line spacing
- The table contents are formatted in Times New Roman 10, with zero margins and single line spacing

<br>
<img width="1351" height="478" alt="изображение" src="https://github.com/user-attachments/assets/13871568-2f90-4b40-b8bb-93798b126661" />
<br>

## Tech Stack
- **Kotlin** – main programming language used for development
- **Compose Multiplatform** - UI toolkit
- **Clean Architecture**
- **MVI** - architectural pattern
- **Apache POI OOXML** - for generating and formatting Word documents
- **Koin** - dependency injection (DI) 
- **Coroutines + Flow**
- **Okio** - for IO operations

## Installation 

### Installation on Android
1. Download the APK from the latest release;
2. Install it on your device.

### Installation on Desktop (JVM)

1. Download the JAR from the latest release
2. Run in the cmd
  ```shell
  java -jar ListingsToWordConvertor.jar
  ```

## Usage
1. Click on the card with the plus (+) icon.  
2. In the opened file explorer, select the folder containing the code for conversion.  
3. After a success message appears, a Word document with tables will be generated in the same folder as the code.<br>

> [!TIP]
> You can simply drag and drop the folder from the file explorer onto the plus (+) card, and the conversion will be also performed automatically

<br>

<img width="775" height="583" alt="изображение" src="https://github.com/user-attachments/assets/3e09d28f-3b9d-43c8-a895-d6d507400c6a" />



## Supported Types

| Language | Extension |
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


