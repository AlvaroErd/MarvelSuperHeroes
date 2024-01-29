import java.util.Locale

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin.parcelize)
}

val versionMajor = 1
val versionMinor = 0
val versionPatch = 0
val versionBuild = 0 // bump for dogfood builds, public betas, etc.

android {
    compileSdk = libs.versions.compileSdk.get().toInt()
    namespace = "com.alerdoci.marvelsuperheroes"

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
    }

    /* TODO: Pending set keystore signature data
        signingConfigs {
            release {
                storeFile file("keystore/MyProject.jks")
                storePassword "password"
                keyAlias "ProjectSignature"
                keyPassword "password"
            }
        }
    */

    defaultConfig {
        applicationId = "com.alerdoci.marvelsuperheroes"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode =
            versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild
        versionName = "${versionMajor}.${versionMinor}.${versionPatch}"

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
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
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
            resValue("string", "app_name", "@string/app_name_debug")
        }

        getByName("release") {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "app_name", "@string/app_name_release")

            // Force copy of distributable apk to custom folder dist in root project
            val archiveBuildTypes = listOf("release", "debug")
            val distFolder = "../dist/"
            val apkNameBase = android.defaultConfig.applicationId

            applicationVariants.filter { appVariant -> appVariant.buildType.name in archiveBuildTypes }
                .map { variant ->
                    variant.outputs.map { output ->

                        val filename =
                            "$apkNameBase-${if (variant.versionName.isNotEmpty()) variant.versionName else "no-version"}-${output.baseName}.apk"
                        val taskSuffix = variant.name.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.getDefault()
                            ) else it.toString()
                        }
                        val assembleTaskName = "assemble$taskSuffix"
                        val taskAssemble = tasks.findByName(assembleTaskName)
                        if (taskAssemble != null) {
                            val copyApkFolderTask = tasks.create(
                                name = "archive$taskSuffix",
                                type = org.gradle.api.tasks.Copy::class
                            ) {

                                description = "Archive/copy APK folder to a shared folder."
                                val sourceFolder =
                                    "$buildDir/outputs/apk/${output.baseName.replace("-", "/")}"
                                val destinationFolder =
                                    "$distFolder${output.baseName.replace("-", "/")}"

                                println("Copying APK folder from: $sourceFolder into $destinationFolder")
                                from(sourceFolder)
                                into(destinationFolder)
                                eachFile {
                                    path = filename
                                }
                                includeEmptyDirs = false
                            }

                            taskAssemble.finalizedBy(copyApkFolderTask)
                        }
                    }
                }

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

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // Specifies one flavor dimension.
    flavorDimensions += "version"
    productFlavors {
        create("pre") {
            dimension = "version"
            applicationIdSuffix = ".pre"
            versionNameSuffix = "-pre"
        }
        create("pro") {
            dimension = "version"
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
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
    debugImplementation(libs.androidx.compose.ui.test.manifest)
//    debugImplementation(libs.androidx.compose.ui.tooling.preview.android)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.accompanist.permissions)

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
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.window)
    implementation(libs.androidx.animation.graphics.android)

    //Splash
    implementation(libs.splashScreen)

    // Hilt
    implementation(libs.hilt.android.core)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // JVM tests - Hilt
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.compiler)

    //Lottie
    implementation(libs.lottieXML)
    implementation(libs.lottieCompose)

    //Coil
    implementation(libs.coil)
    implementation(libs.coilCompose)
    implementation(libs.coilGif)
    implementation(libs.coilSvg)
    implementation(libs.placeholder)
    implementation(libs.zoomable)

    //XML Zoomable Image
    implementation(libs.photoview)

    //Paging
    implementation(libs.pagingCompose)
    implementation(libs.pagingRuntime)

    //Fragments
    implementation(libs.fragmentKtx)
    implementation(libs.androidx.navigation.fragment)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofitConverter)
    implementation(libs.retrofitAdapter)
    implementation(libs.okhttp.logging)
    implementation(libs.okhttp3)

    //Shimmer
    implementation(libs.shimmer)

    //Material Animations
    implementation(libs.soup.anims.core)
    implementation(libs.google.android.material)

    implementation(libs.customtabs)

    //Room
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
//    implementation(libs.androidx.room.paging)
    ksp(libs.room.compiler)

    //Datastore
    implementation(libs.datastore)

    //Firebase and GMS
    implementation(libs.firebase.auth)
    implementation(libs.gms.playservices.auth)
    implementation(libs.google.services)

    //Detekt
    detektPlugins(libs.detekt.formatting)

    //Error handle screen
    implementation(libs.error.handle)

    //Switch with double image
    implementation(libs.switch.image)

    //Google fonts provider
    implementation(libs.google.fonts)

    //Typist - Font animations
    implementation(libs.typist)

    //Toast
    implementation(libs.motionToast)
    implementation(libs.mdToast)
    implementation(libs.toastic)
    implementation(libs.sToast)
    implementation(libs.toasty)

    implementation(libs.konfetti)

    implementation(libs.eva)

//    //Exoplayer
//    implementation(libs.exoplayer.core)
//    implementation(libs.exoplayer.ui)
//    implementation(libs.exoplayer.cast)
//    implementation(libs.exoplayer.hls)
//    implementation(libs.exoplayer.dash)
//    implementation(libs.exoplayer.extension.mediasession)
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
