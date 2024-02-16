-keep class * extends android.app.Activity
-keep class * extends android.app.Application
-keep class * extends android.app.Service
-keep class * extends android.content.BroadcastReceiver
-keep class * extends android.content.ContentProvider
-keep class * extends androidx.** { *; }
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}
-keepattributes *Annotation*

# Kotlin
-keep class kotlin.Metadata { *; }
-keepclassmembers class *$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.** {
    public synthetic <methods>;
}

# Kotlin coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.** {
    volatile <fields>;
}

# Ktor
-keepattributes Signature
-keepattributes *Annotation*
-keepattributes Exceptions
-dontwarn io.ktor.**

# OkHttp & Okio
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-keep class sun.misc.Unsafe { *; }
-dontwarn java.nio.file.**
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.* class * {*;}
-keep @androidx.room.Dao class * {*;}
-keep class androidx.room.** { *; }
-keep class * extends androidx.room.RoomDatabase {
    public static <methods>;
}

# Gson
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }

# Coil
-keep class coil.** { *; }
-dontwarn coil.**

# Timber
-keep class timber.log.Timber { *; }
-dontwarn timber.log.Timber

# Koin
-keep class org.koin.** { *; }
-dontwarn org.koin.**

# Material3
-keep class androidx.compose.material3.** { *; }
-dontwarn androidx.compose.material3.**

# Navigation Compose
-keep class androidx.navigation.compose.** { *; }
-dontwarn androidx.navigation.compose.**

# MockK
-dontwarn io.mockk.proxy.jvm.**
-keepattributes InnerClasses
