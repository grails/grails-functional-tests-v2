# Grails Functional Test Suite v2

New version of the functional test suite for Grails based on Gradle, Spock and Geb.

Tests are found within the <code>src/test/groovy</code> directory.

A basic test looks like:

	class RunAppSpec extends BaseSpec {
	    void "Test run-app starts correctly"() {
	        given:"A new project"
	            grails {
	                createApp "test"
	                runApp()
	            }
            
	        when:"The home page is requested"
	            go ""
	        then:"The Grails welcome page is shown"
	            title == "Welcome to Grails"
	    }
	}
	
The above example creates a new project and tests it, deleting it on completion. To test an existing project you can instead use <code>BaseApplicationSpec</code>:

	class RunAppSpec extends BaseApplicationSpec {
		String application = "foo"
		void "Test run-app starts correctly"() {
	        when:"The home page is requested"
	            go ""
	        then:"The Grails welcome page is shown"
	            title == "Welcome to Grails"			
		}
	}
	
The application needs to be created in the "apps" directory of the project before this test can be run.

Tests are executed using standard Gradle test command:

	./gradlew test
	
Running a single test can be done with:

	./gradlew testSingleRunApp
	
Where "RunApp" above is the test name.

Tests can also be run from the IDE. Just generate Eclipse or Intellij project files:

	./gradlew eclipse
	./gradlew idea
	
Import the project into your IDE and run the specs directly.

