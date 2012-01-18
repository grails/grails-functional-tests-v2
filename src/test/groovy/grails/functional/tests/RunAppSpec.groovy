package grails.functional.tests

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