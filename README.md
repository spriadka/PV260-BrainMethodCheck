# PV260-BrainMethodCheck
Checkstyle check for detecting "Brain method" disharmonies

## What is "Brain Method"?

![Alt text](https://preview.ibb.co/dzECtF/brain_method.png)

## Usage

```shell
mvn clean install
java -cp target/checker-1.0-SNAPSHOT.jar:checkstyle-7.7-all.jar com.puppycrawl.tools.checkstyle.Main -c <your-checkstyle-configuration.xml> <path/to/your/file.java>
```

## Example usage

```shell
java -cp target/checker-1.0-SNAPSHOT.jar:checkstyle-7.7-all.jar com.puppycrawl.tools.checkstyle.Main -c src/main/resources/checkstyle.xml src/test/java/BrainMethodTest.java 
```
## Configutation file

```xml
<module name="Checker">
    <module name="TreeWalker">
        <!-- Need to be TreeWalker Child -->
        <module name="cz.muni.fi.pv260.brainmethod.BrainMethodCheck">
            <!-- Property specifying maximal cyclomatic complexity of brain method --> 
            <property name="cyclomaticComplexityMax" value="2"></property>
            <!-- Property specifying maximum nested control allowed -->
            <property name="nestedControlDepthMax" value="2"></property>
            <!-- Property indicating maximum lines of code per method -->
            <property name="methodLengthMax" value="50"></property>
            <!-- Property defining maximum count of variables used per method -->
            <property name="variableCountMax" value="3"></property>
        </module>
    </module>
</module>
```
