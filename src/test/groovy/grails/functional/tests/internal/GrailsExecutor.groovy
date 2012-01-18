package grails.functional.tests.internal

import grails.functional.tests.Utils
import grails.functional.tests.BaseSpec
import org.junit.Assert

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
        parent.cleanupDirectories << new File(BaseSpec.projectWorkDir, name)
        BaseSpec.upgradedProjects << name
    }

    def runApp() {
       parent.browser.baseUrl = "http://localhost:${BaseSpec.PORT}/$project"
       def process = executeAsync(project, "run-app")
        def buffer = new StringBuffer()
        process.consumeProcessOutput(buffer, buffer)

        int timeout = 0
        while(true) {
            if(timeout > 15000) break
            try {
                def ss = new ServerSocket(BaseSpec.PORT)
                ss.reuseAddress = true
                ss.close()
                timeout += 100
                sleep( 100 )
                continue
            }
            catch(IOException ie) {
                println buffer
                break
            }
        }
        parent.processes << process
    }

    def runWar() {

    }
}