# Automation Exercise

### Instructions

1. Copy all files into a workspace's folder

2. Install

 * Java Platform [JDK](http://www.oracle.com/technetwork/es/java/javase/downloads/index.html)
 * [Eclipse](http://www.eclipse.org/downloads/) for Java EE
 * TestNG plugin for eclipse. [Download](http://www.testng.org/doc/download) [See](http://stackoverflow.com/questions/5230702/how­-to-­install-­testng-­plug-­in-­for-­eclipse)
 * [Apache maven](http://maven.apache.org/install.html)
 * [Firefox 47](https://ftp.mozilla.org/pub/firefox/releases/47.0.2/) as we will work with selenium 2

3. Run maven commands:

 ```
 mvn -U dependency:resolve
 mvn clean install
 mvn eclipse:eclipse
 ```

4. Import the existing project into eclipse

5. Execute the suite by right-clicking on the suite.xml file and selecting run as TestNG suite in eclipse.

6. You can find the test cases in a text file added called 'Automation Test Cases' and the bug reports in a text file called 'Automation Bugs'.