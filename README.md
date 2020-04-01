<h1 align="center">COVID-19-Bangladesh-Android</h1>
<h2 align="center">Palao Corona</h2>
<p align="center">

[![GitHub license](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT) [![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg)]()  [![Maintenance](https://img.shields.io/badge/Maintained%3F-yes-green.svg)]() [![Open Source Love svg1](https://badges.frapsoft.com/os/v1/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/) [![made-with-love](https://img.shields.io/badge/Made%20with-Love-1f425f.svg)](https://saad.ninja)

<br/><br/>

<img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/images/landing-blue.png" alt="alt text" style="width:200;height:200">

<br></br>
An end to end tool & an awareness app with COVID-19 Facts, Mythbusters & Support to eradicate the language & context barrier of misinformation that's been spreading on social media & thus creating mass panic here in Bangladesh. Palao Corona, the app along with its website & social media support group, is a part of our movement against the COVID-19 outbreak which aims to prepare people to fight against the CoronaVirus by providing the necessary information, strategy and support. 

<br></br>

It's built using Kotlin with layers-by-features packaging, MVVM architecture & other standard practices in Native Android development.

</p>

## Architecture Used

<img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/mvvm.png" alt="MVVM Architecture from Jay Rambhia's Blog" style="width:200;height:200">

## App Screenshots

<img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/1.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/2.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/3.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/4.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/5.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/6.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/7.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/8.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/9.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/10.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/11.jpg" height="400" width="200"> <img src="https://github.com/YAS-opensource/PalaoCorona-frontend/blob/master/appscreenshots/12.jpg" height="400" width="200">


## Features

* Easy bilingual support with user navigation
* Continuous slider with graphic illustrations for myth busters, in both languages
* Information portal about COVID-19 and visualizations of how it spreads
* Free COVID-19 risk assessment test
* Firebase Realitime Database integration
* Information portal which fetches most recent news for credible news sources, both in Bangla & English, from our own backend API
* Illustration section for basic DOs and DON'Ts
* Illustration section for explaining the concept of Quarantine in local, contextual and comprehensive contents
* FAQ section with bilingual support
* A portal for Live Updates focusing only on Bangladesh, fetching data from open-sourced data sources, Management Information System (MIS) unit of the Directorate General of Health Services (DGHS), Bangladesh and other leading news paper live portals - all with proper attributions, references and credits
* A portal for emergecncy support, one-tap  call features and developer contributor lists
* Other native android utilities such as Internet Connectivity check, Language and numeric utitilites, animations, transitions, etc

### Built With

* Android Studio 3.6.1. The latest version can be downloaded from [here](https://developer.android.com/studio/)
* Build gradle 3.6.1
* Android SDK 29
* Kotlin Version 1.3.70

### Install the apk

<a href="https://palaocorona.xyz/palaocorona.apk"><img alt="Get it on Google Play" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" width="185" height="70"/></a>

Head over to ```Releases``` in the repo and download the apk file from the latest release. Or, go to the following [link](https://palaocorona.xyz/palaocorona.apk) to download the app, if we continue the support for the domain and our movement. 

## Contributing

### Directory Structure

The following is a high level overview of relevant files and folders.

```
COVID-19-Bangladesh-Android/
├── app/
│   └── src/     
│       └── main/
│           ├── java/xyz/palaocorona
│           │	├── base
│           │	│ 	├── data
│           │	│	├── ui 
│           │	│	└── BaseApplication.kt
│           │	├── data
│           │	│ 	└── ...
│           │	├── di
│           │	│ 	├── annotations
│           │	│	├── components
│           │	│	└── modules
│           │	├── services
│           │	├── ui
│           │	│	├── dialogs 
│           │	│	└── features
│           │	│ 		└── ...
│           │	└── util
│           │	 	└── ...
│           ├── res/
│           └── AndroidManifest.xml
├── gradle/ 
│   └── wrapper/      
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── README.md
├── build.gradle
├── gradle.properties
└── settings.gradle
```


### Build Instructions

Step 1. Add a `build.gradle` (app-level) file in the path `app/build.gradle` like the following with all the libraries used in this project:

```
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'
apply plugin: 'kotlin-kapt'
apply plugin: 'com.google.gms.google-services'
apply plugin: 'com.google.firebase.crashlytics'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.1"

    defaultConfig {
        applicationId "com.yourdomain"
        minSdkVersion 21
        targetSdkVersion 29
        versionCode 3
        versionName "1.0.1"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    buildTypes {
        debug {
            buildConfigField "String", "BASE_URL", "\"http://yourdomain.com/api/base/v10/\""
        }
        release {
            buildConfigField "String", "BASE_URL", "\"http://yourdomain.com/api/base/v10/\""
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    def kotlin_version = '1.3.50'
    def constraintLayoutVersion = '1.1.3'
    def retrofitVersion = '2.5.0'
    def okhttpInterceptorVersion = '3.13.1'
    def sdpsspVersion = '1.0.6'
    def rxandroidVersion = '2.1.1'
    def rxjavaVersion = '2.2.8'
    def lifecycleVersion = '1.1.1'
    def ankoVersion = '0.10.8'
    def lottieVersion = '3.0.7'
    def dagger2Version = '2.25.2'
    def supportLibraryVersion = '1.2.0-alpha05'
    //test
    def junitVersion = '4.12'
    def espressoVersion = '3.2.0'
    def testrunnerVersion = '1.1.1'

    // firebase
    implementation 'com.google.firebase:firebase-analytics:17.2.3'
    implementation 'com.google.firebase:firebase-auth:19.3.0'
    implementation 'com.google.firebase:firebase-firestore-ktx:21.4.1'
    implementation 'com.google.firebase:firebase-database:19.2.1'
    implementation 'com.google.firebase:firebase-storage:19.1.1'
    implementation 'com.google.firebase:firebase-messaging:20.1.3'
    implementation 'com.google.firebase:firebase-crashlytics:17.0.0-beta02'
    implementation 'com.google.firebase:firebase-analytics:17.2.3'

    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.core:core-ktx:1.2.0'
    //ui
    implementation "com.google.android.material:material:$supportLibraryVersion"
    implementation "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    //retrofit
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    implementation "com.squareup.okhttp3:logging-interceptor:$okhttpInterceptorVersion"
    //scalable size unit
    implementation "com.intuit.sdp:sdp-android:$sdpsspVersion"
    implementation "com.intuit.ssp:ssp-android:$sdpsspVersion"
    //permissions
    implementation 'com.karumi:dexter:6.0.2'
    //reactive
    implementation "io.reactivex.rxjava2:rxandroid:$rxandroidVersion"
    implementation "io.reactivex.rxjava2:rxjava:$rxjavaVersion"
    //dagger
    implementation "com.google.dagger:dagger-android:$dagger2Version"
    implementation "com.google.dagger:dagger-android-support:$dagger2Version"
    kapt "com.google.dagger:dagger-compiler:$dagger2Version"
    kapt "com.google.dagger:dagger-android-processor:$dagger2Version"
    //livedata & viewmodel
    implementation "android.arch.lifecycle:extensions:$lifecycleVersion"
    //anko
    implementation "org.jetbrains.anko:anko:$ankoVersion"
    //lottie
    implementation "com.airbnb.android:lottie:$lottieVersion"
    //test
    testImplementation "junit:junit:$junitVersion"
    androidTestImplementation "androidx.test.ext:junit:$testrunnerVersion"
    androidTestImplementation "androidx.test.espresso:espresso-core:$espressoVersion"

    // slider
    implementation 'com.github.smarteist:autoimageslider:1.3.2-appcompat'

    // glide
    implementation 'com.github.bumptech.glide:glide:4.11.0'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'

    // logger
    implementation 'com.orhanobut:logger:2.2.0'

    // otpview
    implementation 'com.github.mukeshsolanki:android-otpview-pinview:2.1.0'

    // cicular imageview
    implementation 'de.hdodenhof:circleimageview:3.1.0'
}
```

Step 2. Connect your app to Firebase, Create a Database and add Firebase Android config files and the essentials (`google-services.json`). For more information, please visit [Installation & Setup on Android](https://firebase.google.com/docs/database/android/start)

Step 3. [Optional] Create a directory `drawable` in the path `app/src/main/res` and add your own demo graphics, which have been removed to protect the content privacy of this movement, Palao Corona.  Alternatively, comment out the codes where they have been used to run the demo app 

Step 4. [Optional] Create a directory `assests` in the path `app/src/main/` and put your animation files in it. Alternatively, comment out the codes where they have been used to run the demo app 


### File Uses

* ```<PROJECT_ROOT>\build.gradle``` : Top-level build file with configuration options common to all sub-projects/modules
* ```<PROJECT_ROOT>\app\build.gradle``` : Gradle specific for app module with libraries used
    * If you use another module in your project, as a local library, you would have another build.gradle file: ```<PROJECT_ROOT>\module\build.gradle```

### Create a branch

1.  `git checkout master` from any folder in your local `COVID-19-Bangladesh-Android`
    repository
1.  `git pull origin master` to ensure you have the latest main code
1.  `git checkout -b the-name-of-my-branch` (replacing `the-name-of-my-branch`
    with a suitable name) to create a branch

### Make the change

1.  Change/Add the codes
1.  Save the files and check the codes if it has successfl build config.

### Test the change

1.  If possible, test the codes the way you want.

### Push it

1.  `git add -A && git commit -m "My message"` (replacing `My message` with a
    commit message, such as `Fixed App Crash` or `Added App Crash 28 Fix`) to stage and commit
    your changes
1.  `git push my-fork-name the-name-of-my-branch`
1.  Go to the
    [`COVID-19-Bangladesh-Android`](https://github.com/SaadAAkash/COVID-19-Bangladesh-Android/)
    and you should see recently pushed branches.
1.  Follow GitHub's instructions and open up a pull request.
1.  If possible, include screenshots of visual changes.

## License
```
MIT License

Copyright (c) 2020 Palao Corona

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```