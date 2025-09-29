plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.servicos"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.servicos"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        android.buildFeatures.buildConfig = true

        // Exporta variáveis do gradle.properties para o BuildConfig
        buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL")}\"")
        buildConfigField("String", "TOKEN", "\"${project.findProperty("TOKEN")}\"")
        buildConfigField("String", "TOKEN_CNPJ", "\"${project.findProperty("TOKEN_CNPJ")}\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.media3.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Instalação de bibliotecas
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")


}