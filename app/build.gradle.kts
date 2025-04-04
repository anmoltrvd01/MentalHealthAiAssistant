plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mentalhealthaibot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mentalhealthaibot"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
    kotlinOptions {
        jvmTarget = "11"
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.code.gson:gson:2.10.1")

    implementation ("com.google.android.material:material:1.8.0")

    implementation ("com.google.android.gms:play-services-fitness:21.1.0")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    implementation ("com.google.android.gms:play-services-auth:19.0.0")

    implementation ("com.google.android.material:material:1.8.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")


    implementation ("com.github.bumptech.glide:glide:4.15.1")
    ksp ("com.github.bumptech.glide:compiler:4.15.1")



    //Room DB
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    implementation("androidx.room:room-ktx:$room_version") // Coroutines support
    ksp("androidx.room:room-compiler:$room_version") // Use KSP Instead of kapt


}