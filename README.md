# PV260-BrainMethodCheck
Checkstyle check for detecting "Brain method" disharmonies

## What is "Brain Method"?

![Alt text](https://preview.ibb.co/dzECtF/brain_method.png)

## Usage

```shell
mvn clean install
java -cp target/checker-1.0-SNAPSHOT.jar:checkstyle-7.7-all.jar com.puppycrawl.tools.checkstyle.Main -c src/main/resources/checkstyle.xml <path/to/your/file.java>
```

## Example usage

```shell
java -cp target/checker-1.0-SNAPSHOT.jar:checkstyle-7.7-all.jar com.puppycrawl.tools.checkstyle.Main -c src/main/resources/checkstyle.xml src/test/java/BrainMethodTest.java 
```
