# Infsci 2510 Final Project

by `Luke Cheng (lukecheng@pitt.edu) | Yongxiang Zhang (yongxiang.zhang@pitt.edu)`

## Demo for Grading

The projects are zipped and submitted via Canvas, but you may also view it on Github: https://github.com/Lujia-Cheng/infsci2150-final-project

All markdown files are exported as pdf with the same name for better canvas viewing experience.

See [Project1-Readme](/Lab%20Project%201/README.md) and [Project2-Readme](/Lab%20Project%202/README.md) for corresponding project details.

## Environment Setup

> TL;DR
>
> - Android Studio Arctic Fox | 2020.3.1 Patch 4
> - JDK 1.8 Amazon Corretto (download via Android Studio setting)

The original skeleton code base is using Gradle 4.4. Luckily it's just a course project, so I don't have to worry about the compatibility issue.

### Android Studio

There's no mention of the dev environment in the original skeleton code base. But we know it was using `Gradle 4.4`, thus it needs `Android Gradle plugin (AGP) <= 3.1.0` by checking this [android studio dev page](https://developer.android.com/build/releases/gradle-plugin#expandable-1). On the same page [different section](https://developer.android.com/build/releases/gradle-plugin#expandable-2), we can also find the `Arctic Fox | 2020.3.1` is the latest version of Android Studio that supporting `AGP == 3.1`

### JDK

Finding the compatible JDK version is easier. We can find the compatibility matrix on the [Gradle official website](https://docs.gradle.org/current/userguide/compatibility.html#java). Anything under `jdk <= 9` should be fine. So we'll use the good old `JDK 1.8`.
