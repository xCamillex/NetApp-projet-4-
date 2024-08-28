plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.openclassrooms.netapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.openclassrooms.netapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {

    implementation (libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation (libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation (libs.androidx.core)
    implementation (libs.butterknife)
    annotationProcessor (libs.butterknife.compiler)
    implementation (libs.recyclerview)

    androidTestImplementation (libs.runner)

    // Retrofit for Network Requests
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)
    implementation (libs.converter.gson)
    //implementation (libs.rxjava3.adapter)
    // RxJava3
    //implementation (libs.rxjava)
    // RxAndroid
    //implementation (libs.rxandroid)
    // Glide (si vous utilisez Glide pour le chargement des images)
    implementation (libs.glide)
    annotationProcessor (libs.compiler)
    // SwipeRefreshLayout
    implementation (libs.swiperefreshlayout)
    implementation (libs.rxjava2.rxjava)
    implementation (libs.adapter.rxjava2)
    implementation (libs.rxjava2.rxandroid)


}
