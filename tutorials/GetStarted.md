# Get Started

In this quickstart, we will help you dip your toes in before you dive in. This guide will help you get started with the $KANDY$ Java SDK.

## Using the SDK

cpaas-sdk uses Maven. At present the jars are available from a public [maven](https://mvnrepository.com) repository.

Use the following dependency in your project to grab via Maven:

```xml
<dependency>
  <groupId>com.cpaassdk</groupId>
  <artifactId>cpaassdk</artifactId>
  <version>1.X.X</version>
  <scope>compile</scope>
</dependency>
```

or Gradle:

```
implementation 'com.cpaassdk:cpaassdk:1.X.X'
```

In your application, you simply need to import the library to be able to make use of it.

```java
// Import client
import com.cpaassdk.Client;

// Initialize
Client client = new Client(args)
```

After you've created your instance of the SDK, you can begin playing around with it to learn its functionality and see how it fits in your application. The API reference documentation will help to explain the details of the available features.

## Configuration
Before starting, you need to learn following information from your CPaaS account, specifically from Developer Portal.

Log into your Developer Portal account and the configuration information required to be authenticated should be under:

+ `Projects` -> `{your project}` -> `Project info`/`Project secret`

> + `Private Project key` should be mapped to `clientId`
> + `Private Project secret` should be mapped to `clientSecret`

Instantiating the library can be done by providing a configuration object to the library factory as shown below.

```java
import com.cpaassdk.Client;

// Initialize
const client = createClient({
  "clientId <Private Project key>",
  "clientSecret <Private Project secret>",
  "https://$KANDYFQDN$"
})
```

## Usage

All modules can be accessed via the client instance, refer to [References](/developer/references/java) for details about all modules and it's methods. All method invocations follow the namespaced signature

`{clientInstance}.{moduleName}.{methodName}(params)`

Example:

```javascript
client.conversation.createMessage(params)
```

Every module method returns a class object of the module.

Example

```java
import org.json.JSONObject;

import com.cpaassdk.resources.Twofactor;

JSONObject params = new JSONObject();
params.put("destinationAddress", "+12065361739")
params.put("message", "Test {code}")

Twofactor response = client.twofactor.sendCode(params);

response.hasError() // true if error present, false if not
response.codeId // codeId of the 2FA sent.
```

## Default Error Response

### Format

```java
response.getErrorId() // error id/code
response.getErrorName() // error type
response.getErrorMessage() // error message
response.hasError() // true if error present, false if not
```