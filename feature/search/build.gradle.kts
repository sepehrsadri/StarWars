@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
  alias(libs.plugins.com.android.library)
  alias(libs.plugins.org.jetbrains.kotlin.android)
  alias(libs.plugins.ksp)
  alias(libs.plugins.hilt)
}

android {
  namespace = "com.sadri.search"
  compileSdk = 34

  defaultConfig {
    minSdk = 24

    testInstrumentationRunner = "com.sadri.testing.TestRunner"
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

  testOptions {
    unitTests {
      isReturnDefaultValues = true
    }
  }
}

dependencies {
  implementation(projects.core.designsystem)
  implementation(projects.core.domain)
  implementation(projects.core.model)
  implementation(projects.core.common)
  implementation(projects.core.data)
  implementation(projects.core.network)
  testImplementation(projects.core.testing)
  androidTestImplementation(projects.core.testing)

  implementation(libs.androidx.paging)
  implementation(libs.androidx.paging.compose)
  implementation(libs.androidx.lifecycle.runtimeCompose)
  implementation(libs.androidx.lifecycle.viewModelCompose)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.core.ktx)
  implementation(libs.lifecycle.runtime.ktx)
  implementation(libs.ui)
  implementation(libs.ui.graphics)
  implementation(libs.ui.tooling.preview)
  implementation(libs.material3)
  testImplementation(libs.io.mockk)
  testImplementation(libs.androidx.paging.testing)
  testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  androidTestImplementation(libs.androidx.test.ext.junit)
  androidTestImplementation(libs.espresso.core)
  debugImplementation(libs.ui.tooling)
  debugImplementation(libs.ui.test.manifest)
  androidTestImplementation(platform(libs.compose.bom))
  androidTestImplementation(libs.ui.test.manifest)
  androidTestImplementation(libs.ui.test.junit4)
  androidTestImplementation(libs.hilt.android.testing)


  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.hilt.android)
  ksp(libs.hilt.compiler)

  implementation(libs.timber)
}