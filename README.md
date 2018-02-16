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
    compile 'com.github.nayanda1:DroidEatr:v0.1.0'
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
    <version>v0.1.0</version>
</dependency>
```

### Manually
1. Clone this repository.
2. Added to your project.
3. Congratulation!

## Usage Example
## Synchronous
Build the object using HttpRequestBuilder and then execute  
If your response contains some Json, you can use RestResponse object and passing the model class when execute  

In Java :
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
    .addJsonBody(someObject).execute(Model.class);

Model responseObj = response.getParsedBody();
```
In Kotlin :
```kotlin
val response : Response = HttpRequestBuilder.httpGet().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value").execute()
    
val isSuccess : Boolean = response.isSuccess()
val hadException : Boolean = response.hadException()
val statusCode : Int = response.statusCode
val body : String? = response.rawBody

//HttpPost with Json response and Json body
val response : RestResponse<ResponseClass> = HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(Model.class);

val responseObj : Model? = response.parsedBody
```

### Simple Asynchronous
Everything is same like synchronous, but you need to pass finisher object into the execute method  
If your response contains some Json, you can use Finisher with RestResponse object and passing the model class when execute  

In Java :
```java
//Basic
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(new Finisher<Response>(){
        @Override
        public void onFinished(Response response){
            //YOUR CODE HERE
            // WILL BE EXECUTE AFTER REQUEST IS FINISHED
        }
    });

//Using RestResponse
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(new Finisher<RestResponse<Model>>(){
        @Override
        public void onFinished(RestResponse<Model> response){
            //YOUR CODE HERE
            // WILL BE EXECUTE AFTER REQUEST IS FINISHED
        }
    }, Model.class);
```

In Kotlin :
```kotlin
//Basic
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject)
    .execute{ response ->
        //YOUR CODE HERE
        // WILL BE EXECUTE AFTER REQUEST IS FINISHED
    }

//Using RestResponse
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject)
    .execute({ response ->
        //YOUR CODE HERE
        // WILL BE EXECUTE AFTER REQUEST IS FINISHED
    }, Model::java.class)
```

### Asynchronous using basic digester
Same like when using finisher, but instead using Digester. digester have 4 method:
- onBeforeSending which will run before sending
- onResponded which will **ONLY** run when you get response
- onTimeout which will **ONLY** run when you get no response after timeout
- onException which will **ONLY** run when you get unhandled exception  

If your response contains some Json, you can use Digester with RestResponse object and passing the model class when execute  

In Java :
```java
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(new Digester<Response>(){
    
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

In Kotlin :
```kotlin
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(object : Digester<Response>(){
    
        override fun onBeforeSending(connection : HttpURLConnection){
            //YOUR CODE HERE
        }
        
        override fun onResponded(response : RestResponse) {
            //YOUR CODE HERE
        }
        
        override fun onTimeout() {
            //YOUR CODE HERE
        }
        
        override fun onException(exception : Exception) {
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

In Java :
```java
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(new ProgressDigester<Response>(){
    
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

In Kotlin :
```kotlin
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).execute(object : ProgressDigester<Response>(){
    
        override fun onProgress(progress : Float) {
            //YOUR CODE HERE
        }
        
        override fun onBeforeSending(connection : HttpURLConnection){
            //YOUR CODE HERE
        }
        
        override fun onResponded(response : RestResponse) {
            //YOUR CODE HERE
        }
        
        override fun onTimeout() {
            //YOUR CODE HERE
        }
        
        override fun onException(exception : Exception) {
            //YOUR CODE HERE
        }
    });
```
### Asynchronous using executor
Same like any async http request, but you can set your consumer separately. you can set 5 consumer:
- setOnProgress which will run for every progress, it will give the progress in float start from 0.0f to 1.0f  
Because this method will called periodically, its better if you're not put object creation inside this method
- setOnBeforeSending which will run before sending
- setOnResponded which will **ONLY** run when you get response
- setOnTimeout which will **ONLY** run when you get no response after timeout
- setOnException which will **ONLY** run when you get unhandled exception 
- setOnFinished which will run after all request is complete  

You don't need to set all of the consumer. just the one you need.
If your response contains some Json, you can pass your model class into the executor  

In Java :
```java
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).usingExecutor(Model.class)
    .setOnTimeout(new Runnable() {
        @Override
        public void run() {
            //YOUR CODE HERE
        }
    })
    .setOnBeforeSending(new Consumer<HttpURLConnection>() {
        @Override
        public void onConsume(HttpURLConnection param) {
            //YOUR CODE HERE
        }
    })
    .setOnException(new Consumer<Exception>() {
        @Override
        public void onConsume(Exception param) {
            //YOUR CODE HERE
        }
    })
    .setOnProgress(new Consumer<Float>() {
        @Override
        public void onConsume(Float param) {
            //YOUR CODE HERE
        }
    })
    .setOnResponded(new Consumer<RestResponse<Model>>() {
        @Override
        public void onConsume(RestResponse<User> param) {
            //YOUR CODE HERE
        }
    })
    .setOnFinished(new Consumer<RestResponse<Model>>() {
        @Override
        public void onConsume(RestResponse<Model> param) {
            //YOUR CODE HERE
        }
    })
    .execute();
```

In Kotlin :
```kotlin
HttpRequestBuilder.httpPost().setUrl("http://your.url.here")
    .addHeaders("SOME-HEADER", "header_value").addParam("param_key", "param_value")
    .addJsonBody(someObject).usingExecutor(Model.class)
    .setOnTimeout({
        //YOUR CODE HERE
    })
    .setOnBeforeSending({ param : HttpURLConnection param ->
        //YOUR CODE HERE
    })
    .setOnException({ param : Exception ->
        //YOUR CODE HERE
    })
    .setOnProgress({ param : Float ->
        //YOUR CODE HERE
    })
    .setOnResponded({ param : RestResponse<User> ->
        //YOUR CODE HERE
    })
    .setOnFinished({ param : RestResponse<Model> ->
        //YOUR CODE HERE
    })
    .execute()
```
---
## Contribute
We would love you for the contribution to **DroidEatr**, just contact me to nayanda1@outlook.com or just pull request
