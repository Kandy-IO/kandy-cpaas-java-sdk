# Get Started

In this quickstart, we will help you dip your toes in before you dive in. This guide will help you get started with the $KANDY$ Java SDK.

## Using the SDK

cpaassdk uses Maven. At present the jars are available from a public [maven](https://mvnrepository.com) repository.

Use the following dependency in your project to grab via Maven:

```xml
<dependency>
  <groupId>com.github.kandy-io</groupId>
  <artifactId>cpaassdk</artifactId>
  <version>1.1.0</version>
  <scope>compile</scope>
</dependency>
```

or Gradle:

```
implementation 'com.github.kandy-io:cpaassdk:1.1.0'
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

If you want to authenticate using CPaaS account's credentials, the configuration information required should be under:

+ `Home` -> `Personal Profile` (top right corner) -> `Details`
> + `Email` should be mapped to `email`
> + Your account password should be mapped to `password`
> + `Account client ID` should be mapped to `client_id`

Alternatively if you want to use your project's credentials, the configuration information required should be under:

+ `Projects` -> `{your project}` -> `Project info`/`Project secret`
> + `Private Project key` should be mapped to `client_id`
> + `Private Project secret` should be mapped to `client_secret`

Instantiating the library can be done by providing a configuration object to the library factory as shown below.

```java
import com.cpaassdk.Client;

// Initialize
const client = Client(
  "clientId <Private Project key>",
  "clientSecret <Private Project secret>",
  "https://$KANDYFQDN$"
)

// or

const client = Client(
  "clientId <Account Client ID>",
  "email <Account Email>",
  "password <Account Password>",
  "https://$KANDYFQDN$"
)
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