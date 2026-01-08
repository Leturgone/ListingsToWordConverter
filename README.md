# Listings to Word Converter

A **Kotlin Multiplatform** desktop application that converts code listings from a directory into a professionally formatted Microsoft Word document (.docx).

## 🎯 What It Does

Scans a directory recursively for code files and generates a Word document with:
- Each file in a numbered, bordered table
- Russian headers: "Таблица N — filename.ext"
- Times New Roman formatting (12pt headers, 10pt code)
- Timestamped output: `documents/listing_YYYYMMDD_HHMMSS.docx`

**Use Cases**: Academic submissions, code reviews, documentation, archival

## ✨ Features

- **Recursive Scanning**: BFS traversal with 10,000 directory safety limit
- **Multi-Language Support**: Kotlin, Java, JavaScript, TypeScript, Python, C/C++, Swift, Markdown, XML
- **Clean Architecture**: Domain/Data/Presentation layers with Koin DI
- **Cross-Platform**: Targets Desktop (JVM) and Android
- **Error Handling**: Comprehensive Result types and logging

## 🛠️ Technology Stack

- **Kotlin Multiplatform** + **Jetpack Compose**
- **Apache POI OOXML** - Word document generation
- **Okio** - Cross-platform file I/O
- **Koin** - Dependency injection
- **Kotlinx Coroutines** - Async operations

## 📦 Prerequisites

**Java 17 or higher** (required)

```bash
# Check version
java -version

# Download from:
# https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
```

## 🚀 Usage

### Run Desktop Application

**Windows:**
```bash
.\gradlew.bat :composeApp:run
```

**macOS/Linux:**
```bash
./gradlew :composeApp:run
```

### Build Native Installer

```bash
.\gradlew.bat :composeApp:packageDistributionForCurrentOS
```

Creates `.msi` (Windows), `.dmg` (macOS), or `.deb` (Linux) in `composeApp/build/compose/binaries/main/`

## 📁 Project Structure

```
composeApp/src/
├── commonMain/kotlin/compose/project/listingstowordconverter/
│   ├── domain/              # Business logic
│   │   ├── model/           # FileSystemItem, FileWithCodeModel, FolderModel
│   │   ├── repository/      # FileRepository, WordRepository interfaces
│   │   └── usecase/         # ConvertCodeToWordFileUseCase
│   ├── data/                # Implementation
│   │   ├── repository/      # FileRepositoryImpl, WordRepositoryImpl
│   │   ├── source/          # AppFileSystem
│   │   └── di/              # Koin modules
│   └── App.kt               # Compose UI
├── jvmMain/kotlin/          # Desktop entry point
└── androidMain/kotlin/      # Android entry point
```

## ⚙️ How It Works

```
User Input (Directory Path)
    ↓
ConvertCodeToWordFileUseCase
    ↓
FileRepository.getRootFolderByPath() → Validates path
    ↓
FileRepository.readAllFiles() → BFS traversal
    ↓
WordRepository.convertFilesToWord() → Apache POI processing
    ↓
FileRepository.saveFile() → Save to documents/
    ↓
Output: listing_YYYYMMDD_HHMMSS.docx
```

### BFS Traversal

```kotlin
fun traverseWithBFS(startPath: Path, rootPath: Path): List<FileWithCodeModel> {
    val queue = ArrayDeque<Path>()
    queue.addLast(startPath)
    
    while (queue.isNotEmpty()) {
        val currentDir = queue.removeFirst()
        fileSystem.list(currentDir).forEach { path ->
            when {
                isDirectory -> queue.addLast(path)
                isCodeFile -> files.add(createFileModel(path))
            }
        }
    }
    return files
}
```

## 📝 Supported File Types

| Language | Extensions |
|----------|-----------|
| Kotlin | `.kt` |
| Java | `.java` |
| JavaScript/TypeScript | `.js`, `.ts` |
| Python | `.py` |
| C/C++ | `.c`, `.cpp`, `.h` |
| Swift | `.swift` |
| Markdown | `.md` |
| XML | `.xml` |

**Add More Types**: Edit `FileRepositoryImpl.kt`:

```kotlin
private fun isCodeFile(filename: String): Boolean {
    val extension = getFileExtension(filename).lowercase()
    return extension in setOf("kt", "java", "js", "ts", "py", "cpp", "c", "h", 
                              "swift", "md", "xml", "go", "rs", "rb") // Add here
}
```

## 🐛 Troubleshooting

### Java Version Error
```
Error: Dependency requires at least JVM runtime version 17
```

**Solution**: Install JDK 17+ and set JAVA_HOME:

```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17

# macOS/Linux
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-17.jdk/Contents/Home
```

### Gradle Build Failure

```bash
.\gradlew.bat clean build --refresh-dependencies
```

### File Access Errors

Ensure read permissions for target directory and write permissions for `documents/` folder.

## 🔨 Programmatic Usage

```kotlin
val convertUseCase = ConvertCodeToWordFileUseCase(fileRepository, wordRepository)
val result = convertUseCase.invoke("/path/to/code/directory")

result.fold(
    onSuccess = { outputPath -> println("Document created: $outputPath") },
    onFailure = { error -> println("Error: ${error.message}") }
)
```

## 📄 Output Format

```
Таблица 1 — MainActivity.kt
┌─────────────────────────────────────┐
│ package com.example.app             │
│                                     │
│ fun main() {                        │
│     println("Hello, World!")        │
│ }                                   │
└─────────────────────────────────────┘

Таблица 2 — Utils.kt
┌─────────────────────────────────────┐
│ package com.example.utils           │
│ ...                                 │
└─────────────────────────────────────┘
```

**Styling**: Times New Roman, 4pt black borders, 100pt spacing before headers

## 🤝 Contributing

Improvement areas:
- Enhanced Compose UI with file picker dialogs
- User preferences for styling/formatting
- PDF/HTML output formats
- Syntax highlighting in documents
- File/directory filtering options
- Real-time progress tracking

## 🔗 Resources

- [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)
- [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)
- [Apache POI](https://poi.apache.org/components/document/)
- [Okio](https://square.github.io/okio/)
- [Koin](https://insert-koin.io/)

---

**Made with ❤️ using Kotlin Multiplatform and Jetpack Compose**
