package grails.functional.tests.internal

import grails.functional.tests.utils.Utils
import grails.functional.tests.BaseSpec
import org.junit.Assert

/**
 * Used to power the grails { } block within tests
 */
class GrailsExecutor {
    @Delegate BaseSpec parent
    def invokeMethod(String name, Object args) {
        "${parent.debug ? 'executeDebug' : 'execute'}"(
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

    def runApp(String app = null) {

        def port = parent.port ?: BaseSpec.PORT
        while(Utils.isServerRunningOnPort(port)) {
            port++
        }
        parent.port = port
        def project =  app ?: parent.project
        parent.project = project
        def process = debug ? executeDebugAsync( app ?: project, "run-app") : executeAsync( app ?: project, "run-app")
        def buffer = new StringBuffer()
        process.consumeProcessOutput(buffer, buffer)

        int timeout = 0
        // allow longer to attach a debugger
        def timeoutMax = parent.isDebug() ? 120000 : 60000
        while(true) {
            if(timeout > timeoutMax) {
                println buffer
                process.destroy()
                Assert.fail("Failed to start server after timeout")
            }
            if(Utils.isServerRunningOnPort(port)) {
                println buffer
                break
            }
            else {
                timeout += 100
                sleep( 100 )                
            }
        }
        parent.browser.baseUrl = "http://localhost:${port}/${ app ?: project}"
        parent.processes << process
    }

    def runWar() {

    }
}