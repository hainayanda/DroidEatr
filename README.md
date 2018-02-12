# DroidEatr
RESTful consumer for android

## Using Eatr
### Gradle
On build.gradle (Project)
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
On build.gradle (Module)
```
dependencies {
    compile 'com.github.nayanda1:DroidEatr:v0.0.6'
}
```
### Maven
Add on your maven repository
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```
Add on your maven dependency
```xml
<dependency>
    <groupId>com.github.nayanda1</groupId>
    <artifactId>DroidEatr</artifactId>
    <version>v0.0.6</version>
</dependency>
```
