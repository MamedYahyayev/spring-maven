##Profiles
Normally when we run mvn package, the unit tests are executed as well.
But what if we want to quickly package the artifact and run it to see if it works?
First, we'll create a no-tests profile which sets the maven.test.skip property to true:

```xml
<profile>
     <id>no-tests</id>
     <properties>
         <maven.test.skip>true</maven.test.skip>
     </properties>
</profile>
```

Next, we'll execute the profile by running the
 `mvn package -Pno-tests` command. Now the artifact is created 
 and the tests are skipped.
In this case the `mvn package -Dmaven.test.skip` command would have been easier.


##Declaring Profiles
We can configure as many profiles as we want by giving them unique ids.
Let's say we wanted to create a profile that only ran our integration tests
and another for a set of mutation tests
We would begin by specifying an id for each one in our pom.xml file:
```xml

<profiles>
	<profile>
	    <id>integration-tests</id>
	</profile>
	<profile>
        <id>mutation-tests</id>
    </profile>
</profiles>

```
Within each profile element, we can configure many elements such as dependencies, 
plugins, resources, finalName.So, for the example above, we could add plugins 
and their dependencies separately for integration-tests and mutation-tests.

Separating tests into profiles can make the default build faster by having it focus, say, on just the unit tests.

##Activating Profiles
After we create one or more profiles we can start using them, or in other words, activating them.

Seeing Which Profiles Are Active
Let's use the help:active-profiles goal to see which profiles are active in our default build:
```shell script
  mvn help:active-profiles
```

We'll activate them in just a moment. But quickly, another way to see what is activated is to include the maven-help-plugin in our pom.xml and tie the active-profiles goal to the compile phase:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-help-plugin</artifactId>
            <version>3.2.0</version>
            <executions>
                <execution>
                    <id>show-profiles</id>
                    <phase>compile</phase>
                    <goals>
                        <goal>active-profiles</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
    </plugins>
</build>
```

##Using -P
we already saw one way at the beginning, which is that we can activate profiles with the -P argument.
```shell script
  mvn package -P integration-tests
```

##Active by Default
If we always want to execute a profile, we can make one active by default:
```xml
    <profile>
        <id>integration-tests</id>
        <activation>
            <activeByDefault>true</activeByDefault>
        </activation>
    </profile>
```
Then, we can run mvn package without specifying the profiles, and we can verify that the integration-test profile is active.

##Based on the JDK Version
Another option is to enable a profile based on the JDK running on the machine. In this case, we want to enable the profile if the JDK version starts with 11
```xml
<profile>
    <id>active-on-jdk-11</id>
    <activation>
        <jdk>11</jdk>
    </activation>
</profile>
```

##Based on the Operating System
```xml
<profile>
    <id>active-on-windows-10</id>
    <activation>
        <os>
            <name>windows 10</name>
            <family>Windows</family>
            <arch>amd64</arch>
            <version>10.0</version>
        </os>
    </activation>
</profile>
```

##Based on a File
Another option is to run a profile if a file exists or is missing.
So, let's create a test profile that only executes if the testreport.html is not yet present:
```xml
    <activation>
        <file>
            <missing>target/testreport.html</missing>
        </file>
    </activation>
```

##Deactivating a Profile
We've seen many ways to activate profiles, but sometimes we need to disable one as well.

To disable a profile we can use the `‘!' or ‘-‘`.

So, to disable the active-on-jdk-11 profile we execute the `mvn compile -P -active-on-jdk-11` command.