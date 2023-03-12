<h1 align="center" style="font-weight:bold" >
 This test automation framework is focused on the validation of various test scenarios using different test APIs, as well as WireMock
</h1>

## What this is framework made of:
In this framework we used different components, which we break them down below with its respective descriptions:
```
1. Java: The programing language to coding and build the test logic.
2. Rest-Assured: A Java library used for testing RESTful APIs.
3. testNG: Is the framework to create, organize and execute test.
4. Maven: Software applications that allows packaging and manage the dependencies.
5. WireMock: Tool for building mock APIs.
```

## Requirements:
* JDK 19
* The rest of the requirements will be set automatically by Maven when you load the project,
  so, please wait a moment while Maven loads and sets dependencies.
    - You can see the loading progress in the status bar of you IDE at the bottom.

## Framework Structure
The main folders of this project are:
* src/test/java/org/example: Contains tests corresponding to test APIs, independent of wiremock.
* src/test/java/WireMock: Contains tests corresponding to wiremock APIs.
* wiremock: Contains the resources to run the mock API service.
>The purposes and contents of the other folders and files, are explained by their own names

## Instructions to run the tests:
### To start the WireMock service:
1. Through the terminal, go to the wiremock folder
2. Run the command:
    ```sh
   java -jar .\wiremock-jre8-standalone-2.35.0.jar
   ````
3. By default, the service runs on the port 8080 under http (no secure protocol)\
   If we want to change this, we can do it by passing the corresponding arguments. e.g.:
    ```sh
   java -jar .\wiremock-jre8-standalone-2.35.0.jar --port 9999 --https-port 2424
   ````

### To run all tests together at once (option 1):
1. Go to: src/test/java/TestRunner.java
2. Run the TestRunner class according your IDE's capabilities
>This option runs all tests together as long as the invoked files within testng.xml are configured as appropriate. 

### To run all tests together at once (option 2):
1. Go to `testng_selected_packages.xml` file and uncomment the line:\
   `<package name=".*" />`
2. Comment any other line that reference any specific package name, if applicable.
3. Right click on te file and select: Run '...testng_selected_packages.xml'

### To run a specific class or test (option 1):
1. Go to the desired class that you want to run
2. Run this class via the 'Execute option/button' provided either by testNG or your IDE.\
    If you're using IntelliJ IDE, next to the left of the statement "public class {Class Name}" you can locate the run symbol which works as a button.
3. Pay attention while test runs in the browser.
4. Once test finish to running, in the panel and dashboard of testNG in the IDE, you can see the testing summary report.
>Note: The same is applicable for specific test, but in this case the 'Execute option/button' would be next to each method with the @test tag.

### To run a specific class or test (option 2):
1. Go to `testng_selected_classes.xml` file .
2. Follow the instructions in comments to run a specific class or test.
3. Right click on te file and select: Run '...testng_selected_classes.xml'

### To run tests using Maven:
>As prerequisite, you need to have maven installed or an IDE with any maven capability
1. Go the corresponding place where to run a maven command, it can be a terminal or maven panel if available.
2. Run the command:
   ```sh
   mvn clean test
   ````
3. If you want to change the testng.xml (that is executed by default) by another one, you should go to the pom.xml and replace the property suite-xml accordingly.
4. As a second option, without doing the step above you can run the following command to replace the testng file on the fly:
    ```sh
   mvn clean test -Dsuite-xml=testng.xml
   ````

### To omit a specific test while running a set of tests as the two first way mentioned above:
1. Go to the test you want to skip within its corresponding class.
2. Below the tag "@Test()" add "@Ignore"
3. Run your set of tests as you want

## Instructions to see the test report
### option 1:
1. Go to: target/surefire-reports
2. Open with any internet browser any of these files to see different type of reports:
   - index.html
   - emailable-report.html

### option 2:
1. Go to: target/surefire-reports/html
2. Open the `index.html` file using any internet browser.

### option 3:
1. Go to the test-output folder
2. Open with any internet browser any of these files to see different type of reports:
   - index.html
   - emailable-report.html


#### <p align="center"> For further information about the author, please consult [Victor Caminero LinkedIn profile](https://www.linkedin.com/in/victor-caminero/) </p>
