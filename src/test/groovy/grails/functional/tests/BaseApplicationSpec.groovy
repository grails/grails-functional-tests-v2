package grails.functional.tests

import spock.lang.Shared
import grails.functional.tests.internal.GrailsExecutor

/**
 * Unlike BaseSpec targets an existing application in the apps/ directory
 */
abstract class BaseApplicationSpec extends BaseSpec {
    
    abstract String getApplication()

    boolean shouldStartApp() {
        def existingPort = System.getProperty("grails.app.port.${getApplication()}")

        if(existingPort != null) {
            println "Application already started on port, waiting for availability..."
            def port = existingPort.toInteger()

            try {
                GrailsExecutor.waitForPort debug, port,  {
                    throw new RuntimeException("Timeout waiting for started server")
                }, {
                    def testName = this.getClass().name
                    println "Server already running, $testName not startingnew server..."
                    System.setProperty("${testName}.port", existingPort)
                    System.setProperty("${testName}.project", getApplication())
                }
                return false
            } catch (e) {
                println "Exception occurred waiting for existing application: $e.message"
                return true
            }
        }
        true 
    }

    @Override
    String getProject() {
        return getApplication()
    }

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
        def existingPort = System.getProperty("grails.app.port.$app")

        if(existingPort == null) {
            silentDelete(new File("$projectsBaseDir/$app/target"))
        }

    }
    void setup() {
        this.project = getApplication()
        def url = "http://localhost:${getPort()}/${getApplication()}/"
        this.browser.baseUrl = url
    }
}