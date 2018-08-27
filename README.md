# sdk-ui-android

A UI framework for adding XYO themed toolbars and dialogs to your existing code. Built on Kotlin

### PREREQUISITES

* JDK 1.8
* Android SDK
  - Kotlin
  - Build Tools 27+
  
[![Build Status](https://semaphoreapp.com/api/v1/projects/d4cca506-99be-44d2-b19e-176f36ec8cf1/128505/shields_badge.svg)](https://semaphoreapp.com/boennemann/badges)
[![GitHub version](https://badge.fury.io/gh/boennemann%2Fbadges.svg)](http://badge.fury.io/gh/boennemann%2Fbadges)

### Installing

You can add sdk-ui-android to your existing app by cloning the project and manually adding it
to your build.gradle:
```bash
git clone git@github.com:XYOracleNetwork/sdk-ui-android.git
```
or by using jitPack:
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```
```gradle
dependencies {
    implementation 'com.github.XYOracleNetwork:sdk-ui-android:[Build Status]'
}
```

### Usage
Most of the UI components can be added to an existing XML layout file. Look at the ui-android-sample
folder for examples
```xml
<network.xyo.ui.views.XYButton
        android:id="@+id/myButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="XY Button"/>
```

### License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
