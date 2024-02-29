@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.com.android.application)
  alias(libs.plugins.org.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
}

android {
  namespace = "com.sadri.starwars"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.sadri.starwars"
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
    debug {
      isShrinkResources = false
      isMinifyEnabled = false
      isCrunchPngs = false
      isDebuggable = true
      enableUnitTestCoverage = true
      applicationIdSuffix = ".dev"
      versionNameSuffix = "-dev"
      signingConfig = signingConfigs.getByName("debug")
    }
    release {
      isShrinkResources = true
      isMinifyEnabled = true
      isCrunchPngs = true
      isDebuggable = false
      enableUnitTestCoverage = false
      proguardFiles(
        "proguard-rules.pro", getDefaultProguardFile("proguard-android.txt")
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_17.toString()
  }
  buildFeatures {
    compose = true
    buildConfig = true
  }
  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.7"
  }
  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
  }
}

dependencies {
  implementation(projects.feature.search)
  implementation(projects.core.designsystem)
  implementation(projects.core.logger)

  implementation(platform(libs.compose.bom))
  implementation(libs.material3)
  implementation(libs.androidx.compose.material3.windowSizeClass)
  implementation(libs.androidx.window.manager)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.lifecycle.viewModelCompose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.core.ktx)
  implementation(libs.activity.compose)
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.ui.test.junit4)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)

  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
}