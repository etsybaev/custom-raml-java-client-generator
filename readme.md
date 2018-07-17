# RAML Client Generator (Customized)
![alt text](https://travis-ci.org/mulesoft-labs/raml-java-client-generator.svg?branch=master "Logo Title Text 1")
  
This tool generates a java rest client for a raml based api using a resource api approach. 
Supports both 0.8 and 1.0 versions of Raml

This is based on origin Mulesoft client generator (https://github.com/mulesoft-labs/raml-java-client-generator), but:
1. Contains fix for stackoverflow error in raml contains recursive dependencies
2. Updated to generate Java8 date and time in POJO classes
3. Updated client for simpler getting urls from generated Client classes. It's useful for testing when you use your own rest client (ex. RestAssured)

## Example

For this api

```raml
#%RAML 0.8
title: Client API
version: 0.1
baseUri: http://mycompany.com/clientservice/api
documentation:
  - title : Users Platform
    content : This api describes how to access to the users platform
mediaType: application/json
/users:
  description: "Users in the platform"
  get:
    description: "Returns the list of all users"
    responses:
      200:
        body:
          application/json:
            example: |
             [{"user" : "Mariano"}]
```

Using the generated api 

```java
final List<UsersGETResponse> result = ClientAPIClient.create().users.get();
```

Customizing the client 

```java
final ClientAPIClient client = new ClientAPIClient() {
    @Override
    protected Client getClient() {
        final Client client = ClientBuilder.newClient();
        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
        client.property(ClientProperties.READ_TIMEOUT, 1000);
        return client;
    }
};
client.users.userId("luis").get();
```

## Using it from java

It can easily be embedded into your code just add de dependency

```xml
  <dependency>
        <groupId>org.mule.raml.codegen</groupId>
        <artifactId>raml-client-generator-core</artifactId>
        <version>0.2-SNAPSHOT</version>
  </dependency>
```

And then call the RamlJavaClientGenerator

```java
 new RamlJavaClientGenerator(
                "com.acme",
                targetFolder).generate(this.getClass().getClassLoader().getResource("simple/basic.raml"));
```

## Using it from maven

For maven just add this plugin 


```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.mule.raml.codegen</groupId>
                <artifactId>raml-client-generator-maven-plugin</artifactId>
                <version>0.2-SNAPSHOT</version>
                <executions>
                    <execution>
                        <goals>
                            <goal>generate-client</goal>
                        </goals>
                        <configuration>
                            <basePackage>org.mule.example</basePackage>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
```    
