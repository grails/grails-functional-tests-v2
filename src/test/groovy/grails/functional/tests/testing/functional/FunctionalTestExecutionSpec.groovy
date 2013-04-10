package grails.functional.tests.testing.functional

import grails.functional.tests.BaseSpec
import grails.functional.tests.BaseApplicationSpec
import spock.lang.Issue

/**
 * Tests for Grails functional testing feature
 */
class FunctionalTestExecutionSpec extends BaseApplicationSpec{

    @Issue('GRAILS-8683')
    void "Test that domain class wiring constructor is not used while the application is initializing"() {
        when:"Functional tests are executed"
            grails {
                compile()
                testApp()
            }
        then:"Functional tests execute correctly"
            output.contains  "Tests PASSED"
            exitStatus == 0

    }

    @Override
    boolean shouldStartApp() {
        false
    }



    @Override
    String getApplication() {
        "functional-testing"
    }
}
