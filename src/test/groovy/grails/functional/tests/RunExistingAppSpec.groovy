package grails.functional.tests

/**
 * Tests running an application in the "apps" directory
 */
class RunExistingAppSpec extends BaseApplicationSpec{


    void "Test run-app starts correctly"() {
        when:"The home page is requested"
            go ""
        then:"The Grails welcome page is shown"
            title == "Welcome to Grails"
    }

    @Override
    String getApplication() {
        "dependency-resolution"
    }
}

