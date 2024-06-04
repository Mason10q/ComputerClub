import buildsrc.Libs
import buildsrc.Versions
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "ru.mirea.computerclub"
    compileSdk = Versions.compileSdk

    defaultConfig {
        applicationId = "ru.mirea.computerclub"
        minSdk = Versions.minSdk
        targetSdk = Versions.targetSdk
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField("String", "ENDPOINT_URL", "\"${properties.getProperty("ENDPOINT_URL")}\"")
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
        sourceCompatibility = Versions.compatibility
        targetCompatibility = Versions.compatibility
    }
    kotlinOptions {
        jvmTarget = Versions.jvmTarget
    }
    viewBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(Libs.AndroidX.core)
    implementation(Libs.AndroidX.appCompat)
    implementation(Libs.Google.material)
    implementation(Libs.AndroidX.constraintlayout)
    implementation(Libs.AndroidX.fragments)
    implementation(Libs.AndroidX.navigationUiKtx)
    implementation(Libs.AndroidX.navigationFragmentKtx)
    implementation(Libs.AndroidX.paging)

    implementation(Libs.Network.retrofit)
    implementation(Libs.Network.retrofitGson)
    implementation(Libs.Network.retrofitFlowAdapter)
    implementation(Libs.Network.ohttp)
    implementation(Libs.Network.okhttpLogInter)
    implementation(platform(Libs.Network.okhttpBom))
    implementation(Libs.Network.gson)

    implementation(Libs.DI.dagger)
    kapt(Libs.DI.daggerCompiler)

    implementation(Libs.Kotlin.coroutinesCore)
    implementation(Libs.Kotlin.coroutinesAndroid)

    implementation(Libs.UI.picasso)


    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    testImplementation("app.cash.turbine:turbine:0.5.2")

}