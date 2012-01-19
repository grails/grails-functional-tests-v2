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
            if(isDebug()) {
                grailsDebug {
                    runApp(app)
                }

            }
            else {
                grails {
                    runApp(app)
                }
            }
        }
    }

    void cleanupSpec() {
        def app = getApplication()
        silentDelete(new File("$projectsBaseDir/$app/target"))
    }
    void setup() {
        this.project = getApplication()
        this.browser.baseUrl = "http://localhost:${port}/${getApplication()}"
    }
}