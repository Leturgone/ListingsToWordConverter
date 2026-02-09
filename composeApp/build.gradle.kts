import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.shadow)
}

kotlin {
    val javaMainClass = "compose.project.listingstowordconverter.MainKt"
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_19)
        }
    }

    jvm {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        mainRun {
            mainClass = javaMainClass
        }

        tasks.register<ShadowJar>("jvmShadowJar") {
            val mainCompilation = compilations["main"]
            val jvmRuntimeConfiguration = mainCompilation
                .runtimeDependencyConfigurationName
                .let { project.configurations[it] }

            from(mainCompilation.output.allOutputs)
            configurations = listOf(jvmRuntimeConfiguration)
            archiveClassifier.set("fatjar")
            manifest.attributes("Main-Class" to javaMainClass)
        }

    }

    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.insert.koin.android)
            implementation(libs.androidx.activity.ktx)
            implementation(libs.insert.koin.compose)
            implementation(libs.insert.koin.composeVM)
            implementation(libs.androidx.documentfile)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.okio)
            implementation(libs.insert.koin.core)
            implementation(libs.insert.koin.composeVM)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.apache.poi.poi.ooxml )
            implementation(libs.composeIcons.tablerIcons)
            api(libs.androidx.lifecycle.viewmodelCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.okio.jvm)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(libs.insert.koin.composeVM)
        }
    }

}

android {
    namespace = "compose.project.listingstowordconverter"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "compose.project.listingstowordconverter"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "compose.project.listingstowordconverter.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose.project.listingstowordconverter"
            packageVersion = "1.0.0"
        }
    }
}
