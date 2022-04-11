<h1 align="center">Framework structure for API project</h1>
<h3 align="center"> This framework can be applied to enterprise projects</h3>

    - this is maven project based on the cucumber framework 
    -in the similar manner we could set up UI framework as well.
    -having the same type of frame work for UI and API will 
    reduse learding time,
    and spead up test skripts development.

<h3 align="left">Basic structure </h3>
<p align="left">
        This is maven project based on the cucumber framework,
in the similar manner we could set up UI framework
</p>
<h5>Main packages </h5>
<ul>
<li>
FEATURES stored in the resources folder, 
under features. Gherkin implementation of test cases
</li>
<li>
STEP_DEFINITIONS - Java implementation of test cases.
Assertions should be performed in step definitions  
</li>
<li>
RUNNERS - Java files, that trigger of the framework, 
Runners run the features depending on the tags, 
we could potentially specify different tags for different Jenkins job (smoke or regression).
If no tags were specified in CukesRunner, whole framework will be triggered.
FailedTestRunner can trigger only failed features, if there is any.
</li>
<li>
UTILITIES - all the utility classes including DB utilities.
JDBC dependency allows us to test API response against DB
in the same time and in the same framework.
</li>
<li>
POJOs - store support POJOs and Maps
</li>
</ul>
<h4> To run the framework on your machine make sure you have installed: </h4>
<ul>
<li>Java and JAVA_HOME</li>
<li>MAVEN and MAVEN_HOME</li>
</ul>
<h5> &#128512; Cucumber reports appear in the target folder after framework was ran </h5>
<p>In this particular structure I used my own simple API, since I didnt have access
to any company APIs, however, any API can be used because principles all the same.
Docs for API in this example can be found here <a href="https://trudova-jobs-api.herokuapp.com/api-docs/">DOCS</a></p>
<h5>Configuration.properties file I can send other email or MatterMost </h5>
<h3 align="left">Additional tools that can be used with frame work</h3>
<p align="left"> <a href="https://www.jenkins.io" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/jenkins/jenkins-icon.svg" alt="jenkins" width="40" height="40"/> </a> </p>
