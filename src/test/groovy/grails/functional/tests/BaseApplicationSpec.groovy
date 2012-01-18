package grails.functional.tests

/**
 * Unlike BaseSpec targets an existing application in the apps/ directory
 */
abstract class BaseApplicationSpec extends BaseSpec {
    
    abstract String getApplication()

    boolean shouldStartApp() { true }
    
    void setup() {
        this.project = getApplication()
        if(shouldStartApp()) {
            grails {
                runApp()
            }
        }
    }
}