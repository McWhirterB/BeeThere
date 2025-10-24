# Setup
This document defines how to setup the initial project prior to implementation

# Maven installation and Project Initialization
1. Follow the instructions from Apache
   - https://maven.apache.org/install.html
2. Run the following command in the top-level directory to generate the initial Maven Project:
      - ```mvn archetype:generate "-DgroupId=com.beethere.app" "-DartifactId=BeeThere" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DarchetypeVersion=1.5" "-DinteractiveMode=false"```
      -  Rename the newly created folder to src
3. We want our jar to always compile with the dependencies included, so add the following plugin to pom.xml:
      ```
        <plugin>
            <artifactId>maven-assembly-plugin</artifactId>
            <version>3.7.1</version>
            <configuration>
              <descriptorRefs>
                <descriptorRef>jar-with-dependencies</descriptorRef>
              </descriptorRefs>
            </configuration>
        </plugin>
      ```
4. Run mvn package assembly:single and ensure that a file with a name like "sample-1.0-SNAPSHOT-jar-with-dependencies.jar" appears in the target directory
5. Run this file with java -jar target/filename.java

