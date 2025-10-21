# Setup
This document defines how to setup the initial project prior to implementation

# Maven installation and Project Initialization
1. Follow the instructions from Apache
   - https://maven.apache.org/install.html
2. Run the following command in the top-level directory to generate the initial Maven Project:
   - ```mvn archetype:generate -DgroupId=com mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.5 -DinteractiveMode=false```
    - You must add quotes to run this command on windows, like so:
      - ```mvn archetype:generate "-DgroupId=com.mycompany.app" "-DartifactId=my-app" "-DarchetypeArtifactId=maven-archetype-quickstart" "-DarchetypeVersion=1.5 -DinteractiveMode=false"```



