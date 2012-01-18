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

Tests are executed using standard Gradle test command:

	./gradlew test
	
Running a single test can be done with:

	./gradlew testSingleRunApp
	
Where "RunApp" above is the test name