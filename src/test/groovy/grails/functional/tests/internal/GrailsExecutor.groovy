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
                getProject(),
                Utils.getCommandName(name),
                *args)
    }

    def createApp(String name) {
        parent.projectWorkDir = new File("build/project-work").canonicalPath
        execute(
                getProject(),
                "create-app"
                ,
                name)
        setProject(name)
        parent.cleanupDirectories << new File(parent.projectsBaseDir, name)
        parent.browser.baseUrl = "http://localhost:${getPort()}/${getProject()}"
        parent.upgradedProjects << name
    }

    def runApp(String app = null) {

        def port = getPort() ?: BaseSpec.PORT
        while(Utils.isServerRunningOnPort(port)) {
            port++
        }
        setPort(port)
        def project =  app ?: getProject()
        setProject(project)
        Process process = debug ? executeDebugAsync( app ?: project, "run-app") : executeAsync( app ?: project, "run-app")
        def buffer = new StringBuffer()
        process.consumeProcessOutput(buffer, buffer)

        def isDebug = parent.isDebug()
        waitForPort isDebug, port, {
            println buffer
            try {
                new URL("http://localhost:${port}").text // activate kill switch
            } catch (e) {
                // ignore
            }
            process.destroy()
            Assert.fail("Failed to start server after timeout")
        }, {
            println buffer
        }
        def url = "http://localhost:${port}/${ app ?: project}/"

        parent.browser.baseUrl = url 
        parent.processes << process
    }

    static int waitForPort(boolean isDebug, int port, Closure onFailure, Closure onSuccess) {
        int timeout = 0

        def timeoutMax = BaseSpec.PROCESS_TIMEOUT_MILLS
        while (timeout < timeoutMax) {
            if (Utils.isServerRunningOnPort(port)) {
                onSuccess()
                return timeout
            }
            else {
                timeout += 100
                sleep(100)
            }
        }
		onFailure()
        return timeout
    }

    def runWar() {

    }
}