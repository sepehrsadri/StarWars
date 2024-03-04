@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.com.android.library)
  alias(libs.plugins.org.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
}

android {
  namespace = "com.sadri.testing"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
  }


  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.7"
  }
}

dependencies {
  implementation(projects.core.data)
  implementation(projects.core.domain)
  implementation(projects.core.model)
  implementation(projects.core.common)
  implementation(projects.core.network)
  implementation(projects.feature.search) {
    exclude(group = "androidx.paging", module = "paging-compose")
  }

  implementation(libs.androidx.paging)
  implementation(libs.core.ktx)
  implementation(libs.appcompat)
  api(libs.junit)
  api(libs.androidx.test.ext.junit)
  api(libs.espresso.core)
  api(libs.kotlinx.coroutines.test)
  api(libs.turbine)

  api(platform(libs.compose.bom))
  api(libs.ui.test.manifest)
  api(libs.ui.test.junit4)
  api(libs.hilt.android.testing)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)
}