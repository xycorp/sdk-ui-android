[logo]: https://www.xy.company/img/home/logo_xy.png "XYAccount"

![logo]

# sdk-ui-android

[![](https://jitpack.io/v/XYOracleNetwork/sdk-ui-android.svg)](https://jitpack.io/#XYOracleNetwork/sdk-ui-android) <a href="https://codeclimate.com/github/XYOracleNetwork/sdk-ui-android/maintainability"><img src="https://api.codeclimate.com/v1/badges/4738f16c7d074fca669b/maintainability" /></a>

| Branches        | Status           |
| ------------- |:-------------:|
| Master      | [![Build Status](https://travis-ci.com/XYOracleNetwork/sdk-ui-android.svg?branch=master)](https://travis-ci.com/XYOracleNetwork/sdk-ui-android) |
| Develop      | [![Build Status](https://travis-ci.com/XYOracleNetwork/sdk-ui-android.svg?branch=develop)](https://travis-ci.com/XYOracleNetwork/sdk-ui-android)      |

A UI framework for adding XYO themed toolbars and dialogs to your android application.

### PREREQUISITES

* JDK 1.8
* Android SDK
  - Kotlin
  - Build Tools 28+

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
    implementation 'com.github.XYOracleNetwork:sdk-ui-android:v1.0'
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

<br><hr><br>
<p align="center">Made with  ❤️  by [<b>XY - The Persistent Company</b>] (https://xy.company)</p>
