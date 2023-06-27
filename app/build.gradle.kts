plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.alerdoci.marvelsuperheroes"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.alerdoci.marvelsuperheroes"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        vectorDrawables.useSupportLibrary = true


        testInstrumentationRunner =
            "com.example.android.architecture.blueprints.todoapp.CustomTestRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += "room.incremental" to "true"
            }
        }
    }

    buildTypes {
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("String", "BASE_URL", "\"${project.properties["BASE_URL"]}\"")
            buildConfigField(
                "String",
                "API_KEY_PUBLIC",
                "\"${project.properties["API_KEY_PUBLIC"]}\""
            )
            buildConfigField(
                "String",
                "API_KEY_PRIVATE",
                "\"${project.properties["API_KEY_PRIVATE"]}\""
            )
        }

        getByName("release") {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"${project.properties["BASE_URL"]}\"")
            buildConfigField(
                "String",
                "API_KEY_PUBLIC",
                "\"${project.properties["API_KEY_PUBLIC"]}\""
            )
            buildConfigField(
                "String",
                "API_KEY_PRIVATE",
                "\"${project.properties["API_KEY_PRIVATE"]}\""
            )
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    packagingOptions {
        excludes += "META-INF/AL2.0"
        excludes += "META-INF/LGPL2.1"
    }

}

dependencies {
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.compose.material.iconsExtended)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.materialWindow)
    implementation(libs.androidx.compose.runtime.livedata)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemuicontroller)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.glance)
    implementation(libs.androidx.glance.appwidget)
    implementation(libs.androidx.glance.material3)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.window)

    //Splash
    implementation(libs.splashScreen)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // JVM tests - Hilt
    testImplementation(libs.hilt.android.testing)
    kaptTest(libs.hilt.compiler)

    //Lottie
    implementation(libs.lottieXML)
    implementation(libs.lottieCompose)

    //Coil
    implementation(libs.coil)
    implementation(libs.coilGif)

    //Paging
    implementation(libs.pagingCompose)
    implementation(libs.pagingRuntime)

    //Fragments
    implementation(libs.fragmentKtx)
    implementation(libs.navigationFragmentKtx)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitConverter)
    implementation(libs.retrofitAdapter)

    //Shimmer
    implementation(libs.shimmer)

    //Material Animations
    implementation(libs.soup.anims.core)
    implementation(libs.soup.anims.navigation)
    implementation(libs.google.android.material)

    implementation(libs.customtabs)

//    //Firebase and GMS
//    implementation(libs.firebase.auth)
//    implementation(libs.gms.playservices.auth)
//    implementation(libs.google.services)

//    //Exoplayer
//    implementation(libs.exoplayer.core)
//    implementation(libs.exoplayer.ui)
//    implementation(libs.exoplayer.cast)
//    implementation(libs.exoplayer.hls)
//    implementation(libs.exoplayer.dash)
//    implementation(libs.exoplayer.extension)
//    implementation(libs.exoplayer.smoothstreaming)

    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    // AndroidX Test - Hilt testing
    androidTestImplementation(libs.hilt.android.testing)
}