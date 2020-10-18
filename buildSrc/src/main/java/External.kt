object External {
  const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"

  const val rxjava2 = "io.reactivex.rxjava2:rxjava:${Versions.rx}"
  const val rxbinding = "com.jakewharton.rxbinding3:rxbinding:3.0.0-alpha1"
  const val rxbindingCore = "com.jakewharton.rxbinding3:rxbinding-core:3.0.0-alpha1"
  const val rxkotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

  const val leakcanaryDebug = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"
  const val leakcanaryRelease = "com.squareup.leakcanary:leakcanary-android-no-op:${Versions.leakCanary}"

  const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
  const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
  const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofit}"
  const val okhttpLoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"

  const val dexter = "com.karumi:dexter:5.0.0"

  const val firebaseCore = "com.google.firebase:firebase-core:16.0.4"
  const val crashlytics =  "com.google.firebase:firebase-crashlytics:17.2.2"

  const val jodaTime = "net.danlew:android.joda:${Versions.jodaTime}"

  const val koin = "org.koin:koin-android:${Versions.koin}"
  const val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
}

object Realm {
  const val plugin = "io.realm:realm-gradle-plugin:${Versions.realm}"
  const val nameHelper = "dk.ilios:realmfieldnameshelper:1.1.1"
}

object Firebase {
  const val core = "com.google.firebase:firebase-core:11.2.0"
  const val crash = "com.google.firebase:firebase-crash:11.2.0"
  const val messaging = "com.google.firebase:firebase-messaging:11.2.0"
}

object Google {
  const val maps = "com.google.android.gms:play-services-maps:${Versions.googlePlayServices}"
  const val location = "com.google.android.gms:play-services-location:${Versions.googlePlayServices}"
}