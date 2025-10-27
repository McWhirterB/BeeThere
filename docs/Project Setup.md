# Setup
This document defines how to setup the initial project prior to implementation

# Maven installation and Project Initialization
1. Follow the instructions from Apache
   - https://maven.apache.org/install.html
2. Get a default SpringBoot project from Spring Initializer at [https://start.spring.io/](https://start.spring.io/)
   - Set the group as **com.beethere**
   - Set the artifact as **app**
3. Unzip the project into /src, which should now contain /src, /target, and /om.xml
4. Run the default project using mvn spring-boot:run in /src folder
   - Alternatively, obtain a .jar file with mvn package, will will output a fat jar at /src/target
      - This can be run with java -jar ./target/JAR_NAME.jar
5. With the server running, navigate to localhost:8080 in a web browser
   - At this point, a **Whitelabel Error Page** should show up, which means the server works