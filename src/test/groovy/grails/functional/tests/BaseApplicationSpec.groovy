package grails.functional.tests

import spock.lang.Shared
import grails.functional.tests.internal.GrailsExecutor

/**
 * Unlike BaseSpec targets an existing application in the apps/ directory
 */
abstract class BaseApplicationSpec extends BaseSpec {
    
    abstract String getApplication()

    boolean shouldStartApp(app) {
        def existingPort = System.getProperty("grails.app.port.$app")
        println "EXISTING PORT = $existingPort"
        if(existingPort != null) {
            println "Application already started on port, waiting for availability..."
            port = existingPort.toInteger()
            try {
                GrailsExecutor.waitForPort debug, port,  {
                    throw new RuntimeException("Timeout waiting for started server")
                }, {
                    println "Server already running, not starting..."
                }
                return false
            } catch (e) {
                println "Exception occurred waiting for existing application: $e.message"
                return true
            }
        }
        true 
    }


    void setupSpec() {
        def app = getApplication()
        if(shouldStartApp(app)) {
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