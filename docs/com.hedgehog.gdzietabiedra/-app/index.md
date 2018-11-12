[com.hedgehog.gdzietabiedra](../index.md) / [App](./index.md)

# App

`class App : `[`Application`](https://developer.android.com/reference/android/app/Application.html)`, HasActivityInjector, HasServiceInjector` [(source)](https://github.com/asvid/GdzieTaBiedra/tree/master/app/src/main/java/com/hedgehog/gdzietabiedra/App.kt#L23)

Application class, no rocket science here

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `App()`<br>Application class, no rocket science here |

### Properties

| Name | Summary |
|---|---|
| [dispatchingAndroidInjector](dispatching-android-injector.md) | `lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<`[`Activity`](https://developer.android.com/reference/android/app/Activity.html)`>` |
| [dispatchingServiceInjector](dispatching-service-injector.md) | `lateinit var dispatchingServiceInjector: DispatchingAndroidInjector<`[`Service`](https://developer.android.com/reference/android/app/Service.html)`>` |

### Functions

| Name | Summary |
|---|---|
| [activityInjector](activity-injector.md) | `fun activityInjector(): AndroidInjector<`[`Activity`](https://developer.android.com/reference/android/app/Activity.html)`>` |
| [onCreate](on-create.md) | `fun onCreate(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>[Fabric](#) initialisation [LeakCanary](#) initialisation [Timber](http://jakewharton.github.io/timber/timber/log/Timber.html) initialisation |
| [serviceInjector](service-injector.md) | `fun serviceInjector(): AndroidInjector<`[`Service`](https://developer.android.com/reference/android/app/Service.html)`>` |
