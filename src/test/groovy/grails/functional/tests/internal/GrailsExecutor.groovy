package grails.functional.tests.internal

import grails.functional.tests.utils.Utils
import grails.functional.tests.BaseSpec
import org.junit.Assert
import grails.functional.tests.utils.PortPool

/**
 * Used to power the grails { } block within tests
 */
class GrailsExecutor {
    @Delegate BaseSpec parent
    def invokeMethod(String name, Object args) {
        execute(
                project,
                Utils.getCommandName(name),
                *args)
    }

    def createApp(String name) {
        BaseSpec.projectWorkDir = System.getProperty("java.io.tmpdir")
        execute(
                project,
                "create-app"
                ,
                name)
        project = name
        BaseSpec.cleanupDirectories << new File(BaseSpec.projectsBaseDir, name)
        parent.browser.baseUrl = "http://localhost:${parent.port}/${parent.project}"
        BaseSpec.upgradedProjects << name
    }

    def runApp() {

        while(Utils.isServerRunningOnPort(parent.port)) {
            parent.port++
        }
        def process = executeAsync(project, "run-app")
        def buffer = new StringBuffer()
        process.consumeProcessOutput(buffer, buffer)

        int timeout = 0
        while(true) {
            if(timeout > 30000) {
                process.destroy()
                Assert.fail("Failed to start server after timeout")
            }
            if(Utils.isServerRunningOnPort(parent.port)) {
                println buffer
                break
            }
            else {
                timeout += 100
                sleep( 100 )                
            }
        }
        parent.browser.baseUrl = "http://localhost:${parent.port}/$project"
        parent.processes << process
    }

    def runWar() {

    }
}