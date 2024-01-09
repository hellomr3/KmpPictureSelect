plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("maven-publish")
}

kotlin{
//    withSourcesJar(publish = false)
    cocoapods {
        version = "1.0.0"
        ios.deploymentTarget = "12.0"
        pod("TZImagePickerController/Basic") {
            version = "~> 3.8.4"
        }
    }
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishLibraryVariants("release", "debug")
//        publishLibraryVariantsGroupedByFlavor = true
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlin.coroutines.core)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.exifInterface)
                implementation(libs.pictureselector)
                implementation(libs.pictureselector.compress)
            }
        }
    }
}

android {
    compileSdk = 33
    namespace = "com.usecase.picture_select"
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

group="com.hellomr3"
version = "1.0.0"

publishing {
    publishing{
        repositories {
            mavenLocal()
        }
    }
}