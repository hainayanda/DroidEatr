<p align="center">
  <img width="128" height="192" src="droid.eatr.png"/>
</p>

# DroidEatr
RESTful web service consumer for android with builder
[![](https://jitpack.io/v/nayanda1/DroidEatr.svg)](https://jitpack.io/#nayanda1/DroidEatr)

---
## Changelog
for changelog check [here](CHANGELOG.md)

---
## Features

- [x] Builder pattern
- [x] Auto encode param and form url encoded body
- [x] Auto parsed raw body from stream object to String
- [x] Auto parsed JSON using GSON library
- [x] Support synchronous or asynchronous operation
- [x] Support progress observer

---
## Requirements

- Android API 21 and higher

---
## Installation
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
    compile 'com.github.nayanda1:DroidEatr:v0.0.9-Beta'
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
    <version>v0.0.9-Beta</version>
</dependency>
```

### Manually
1. Clone this repository.
2. Added to your project.
3. Congratulation!

## Usage Example
### Synchronous
Build the object using HttpRequestBuilder and then execute  
If your response contains some Json, you can use RestResponse object and passing the model class when execute

```java
//HttpGet
Response response = HttpRequestBuilder.httpGet().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value").execute();
    
boolean isSuccess = response.isSuccess();
boolean hadException = response.hadException();
int statusCode = response.getStatusCode();
String body = response.getRawBody();

//HttpPost with Json response and Json body
RestResponse<ResponseClass> response = HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody<JsonModel>(someObject).execute(Model.class);

Model responseObj = response.getParsedBody();
```

### Simple Asynchronous
Everything is same like synchronous, but you need to pass finisher object into the execute method  
If your response contains some Json, you can use Finisher with RestResponse object and passing the model class when execute

```java
//Basic
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody<JsonModel>(someObject).execute(new Finisher<Response>(){
        @Override
        public void onFinished(Response response){
            //YOUR CODE HERE
            // WILL BE EXECUTE AFTER REQUEST IS FINISHED
        }
    });

//Using RestResponse
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody<JsonModel>(someObject).execute(new Finisher<RestResponse<Model>>(){
        @Override
        public void onFinished(RestResponse<Model> response){
            //YOUR CODE HERE
            // WILL BE EXECUTE AFTER REQUEST IS FINISHED
        }
    }, Model.class);
```

### Asynchronous using basic digester
Same like when using finisher, but instead using Digester. digester have 4 method:
- onBeforeSending which will run before sending
- onResponded which will **ONLY** run when you get response
- onTimeout which will **ONLY** run when you get no response after timeout
- onException which will **ONLY** run when you get unhandled exception  

If your response contains some Json, you can use Digester with RestResponse object and passing the model class when execute

```java
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody<JsonModel>(someObject).execute(new Digester<Response>(){
    
        @Override
        public void onBeforeSending(HttpURLConnection connection){
            //YOUR CODE HERE
        }
        
        @Override
        public void onResponded(RestResponse response) {
            //YOUR CODE HERE
        }
        
        @Override
        public void onTimeout() {
            //YOUR CODE HERE
        }
        
        @Override
        public void onException(Exception exception) {
            //YOUR CODE HERE
        }
    });
```

### Asynchronous using progress digester
Same like when using finisher, but instead using ProgressDigester. progress digester have 4 method:
- onProgress which will run for every progress, it will give the progress in float start from 0.0f to 1.0f  
Because this method will called periodically, its better if you're not put object creation inside this method
- onBeforeSending which will run before sending
- onResponded which will **ONLY** run when you get response
- onTimeout which will **ONLY** run when you get no response after timeout
- onException which will **ONLY** run when you get unhandled exception  

If your response contains some Json, you can use ProgressDigester with RestResponse object and passing the model class when execute

```java
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody<JsonModel>(someObject).execute(new ProgressDigester<Response>(){
    
        @Override
        public void onProgress(float progress) {
            //YOUR CODE HERE
        }
        
        @Override
        public void onBeforeSending(HttpURLConnection connection){
            //YOUR CODE HERE
        }
        
        @Override
        public void onResponded(RestResponse response) {
            //YOUR CODE HERE
        }
        
        @Override
        public void onTimeout() {
            //YOUR CODE HERE
        }
        
        @Override
        public void onException(Exception exception) {
            //YOUR CODE HERE
        }
    });
```

---
## Contribute
We would love you for the contribution to **DroidEatr**, just contact me to nayanda1@outlook.com or just pull request
