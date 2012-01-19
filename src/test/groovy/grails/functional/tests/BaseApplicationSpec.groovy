package grails.functional.tests

import spock.lang.Shared

/**
 * Unlike BaseSpec targets an existing application in the apps/ directory
 */
abstract class BaseApplicationSpec extends BaseSpec {
    
    abstract String getApplication()

    boolean shouldStartApp() { true }


    void setupSpec() {
        def app = getApplication()
        if(shouldStartApp()) {
            grails {
                runApp(app)
            }
        }
    }

    void setup() {
        this.project = getApplication()
    }
}