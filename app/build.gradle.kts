plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.22-1.0.17"
}

android {
    namespace = "com.example.miappbasica"
    compileSdk = 34 // Usando una versión estable y común

    defaultConfig {
        applicationId = "com.example.miappbasica"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8" // Versión estable para Kotlin 1.9.22
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Para usar Coroutines con Room
    "ksp"("androidx.room:room-compiler:$room_version") // Usa 'ksp' en lugar de 'kapt'




    // Definimos las versiones aquí para claridad
    val coreKtxVersion = "1.12.0"
    val appcompatVersion = "1.6.1" // CLAVE para el idioma
    val activityComposeVersion = "1.8.2"
    val lifecycleVersion = "2.7.0"
    val composeBomVersion = "2024.02.01"
    val navigationVersion = "2.7.7"

    implementation("com.google.android.material:material:1.12.0")
    // Core & AppCompat (¡Fundamentales!)
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    // Ciclo de vida y Activity
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion")
    implementation("androidx.activity:activity-compose:$activityComposeVersion")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${lifecycleVersion}")   // <-- LÍNEA AÑADIDA

    // BOM de Compose (Bill of Materials) - Gestiona las versiones de las librerías de Compose
    implementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended") // <-- DEPENDENCIA AÑADIDA
    debugImplementation("androidx.compose.ui:ui-tooling")
    

    // Navegación
    implementation("androidx.navigation:navigation-compose:$navigationVersion")

    // Dependencias de Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBomVersion"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")





}
