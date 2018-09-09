const val versionMajor = 0
const val versionMinor = 1
const val versionPatch = 0
const val versionBuild = 0

object Versions {
  const val dagger = "2.12"
  const val support = "27.0.2"
  const val robolectric = "3.4.2"
  const val kotlin = "1.2.21"
  const val timber = "4.7.0"
  const val rx = "2.1.9"
  const val room = "1.0.0"
  const val realm = "5.4.2"
}

object ProjectDeps {
  const val gradlePlugin = "com.android.tools.build:gradle:3.1.4"
  const val googleServices = "com.google.gms:google-services:3.0.0"
  const val kotlinPlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
  const val kotlinExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:${Versions.kotlin}"
  const val firebasePlugin = "com.google.firebase:firebase-plugins:1.0.5"
  const val dexcount = "com.getkeepsafe.dexcount:dexcount-gradle-plugin:0.8.3"
  const val apkSize = "com.vanniktech:gradle-android-apk-size-plugin:0.4.0"
  const val coveralls = "org.kt3k.gradle.plugin:coveralls-gradle-plugin:2.8.1"
  const val jacoco = "com.dicedmelon.gradle:jacoco-android:0.1.2"
}

object Build {
  const val compileSdkVersion = 28
  const val minSdkVersion = 19
  const val targetSdkVersion = 28
  const val buildToolsVersion = "28.0.2"
  const val versionCode = versionMajor * 1000 + versionMinor * 100 + versionPatch * 10 + versionBuild
  const val versionName = "$versionMajor.$versionMinor.$versionPatch.$versionBuild"
  const val appId = "com.hedgehog.gdzietabiedra"
}

object Apt {
  const val androidApi = "com.google.android:android:2.2.1"
  const val daggerCompiler = "com.google.dagger:dagger-compiler:${Versions.dagger}"
  const val roomCompiler = "android.arch.persistence.room:compiler:${Versions.room}"
}

object Support {
  const val appcompat = "com.android.support:appcompat-v7:${Versions.support}"
  const val annotations = "com.android.support:support-annotations:${Versions.support}"
  const val design = "com.android.support:design:${Versions.support}"
  const val constraint = "com.android.support.constraint:constraint-layout:1.0.2"
  const val preference = "com.android.support:preference-v7:${Versions.support}"
  const val recyclerView = "com.android.support:recyclerview-v7:${Versions.support}"
  const val cardView = "com.android.support:cardview-v7:${Versions.support}"
}

object TestDeps {
  const val junit = "junit:junit:4.12"
  const val mockito = "org.mockito:mockito-core:2.0.42-beta"
  const val assertj = "org.assertj:assertj-core:2.6.0"
  const val compileTesting = "com.google.testing.compile:compile-testing:0.11"
  const val runner = "com.android.support.test:runner:1.0.2"
  const val espresso = "com.android.support.test.espresso:espresso-core:3.0.2"
}

object External {
  const val dagger = "com.google.dagger:dagger:${Versions.dagger}"
  const val daggerSupport = "com.google.dagger:dagger-android-support:${Versions.dagger}"
  const val daggerProcessor = "com.google.dagger:dagger-android-processor:${Versions.dagger}"
  const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rx}"
  const val rxbinding = "com.jakewharton.rxbinding2:rxbinding:2.0.0"
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jre8:${Versions.kotlin}"
  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
  const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
  const val prettyTime = "org.ocpsoft.prettytime:prettytime:4.0.1.Final"
  const val leakcanaryDebug = "com.squareup.leakcanary:leakcanary-android:1.5.4"
  const val leakcanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:1.5.4"
  const val gson = "com.google.code.gson:gson:2.8.2"
  const val dexter = "com.karumi:dexter:5.0.0"
  const val retrofit = "com.squareup.retrofit2:retrofit:2.4.0"
  const val moshiConverter = "com.squareup.retrofit2:converter-moshi:2.4.0"
  const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:2.4.0"
}

object Realm {
  const val plugin = "io.realm:realm-gradle-plugin:${Versions.realm}"
  const val nameHelper = "dk.ilios:realmfieldnameshelper:1.1.1"
}

object Uber {
  const val ribCompilerTest = "com.uber.rib:rib-compiler-test:0.9.1"
  const val ribs = "com.uber.rib:rib-android:0.9.1"
  const val ribTestUtils = "com.uber.rib:rib-test-utils:0.9.1"
}

object Firebase {
  const val core = "com.google.firebase:firebase-core:11.2.0"
  const val crash = "com.google.firebase:firebase-crash:11.2.0"
  const val messaging = "com.google.firebase:firebase-messaging:11.2.0"
}

object Google {
  const val maps = "com.google.android.gms:play-services-maps:15.0.1"
}
