package grails.functional.tests

import geb.spock.GebSpec
import grails.functional.tests.internal.GrailsExecutor
import geb.Browser
import org.apache.commons.io.FileUtils
import geb.spock.GebReportingSpec
import org.openqa.selenium.htmlunit.HtmlUnitDriver

@WebReport
abstract class BaseSpec extends GebReportingSpec{
    
	static PORT = 8184
	static PROCESS_TIMEOUT_MILLS = 1000 * 60 * 5 // 5 minutes
	static upgradedProjects = []
	
	static grailsHome = new File(requiredSysProp('grailsHome', "../grails-master")).canonicalPath
	static grailsWorkDir = requiredSysProp('grailsWorkDir', new File("build/grails-work").canonicalPath)
    static projectsBaseDir = requiredSysProp('projectsBaseDir', findChildOfRoot("apps"))
    static autostartBaseDir = findChildOfRoot("autostart")
	static projectWorkDir = requiredSysProp('projectWorkDir', new File("build/project-work").canonicalPath)
	static outputDir = requiredSysProp('outputDir',new File("build/output").canonicalPath)
    
    static {
        if(!System.getProperty("geb.build.reportsDir")) {
            System.setProperty("geb.build.reportsDir",outputDir)
        }
    }
	
	@Lazy static grailsVersion = {
		new File(grailsHome, "build.properties").withReader { def p = new Properties(); p.load(it); p.'grails.version' }
	}()

	static requiredSysProp(prop, defaultValue = null) {
        def value = System.getProperty(prop) ?: defaultValue
		assert  value != null
        return value
	}

    static findChildOfRoot(String name) {
        def current = new File(".").canonicalFile
        while(current != null && !new File(current, "build.gradle").exists()) {
            current = current.parentFile
        }

        return new File(current, name).absolutePath
    }

    /**
     * Whether to start the servlet container in debug mode
     */
    boolean debug
    def command
    def exitStatus
    def output
    def projectDir
    static processes = []
    static cleanupDirectories = []
    protected portInternal
    protected projectInternal

    String getProject() {
        def classProject = System.getProperty("${this.getClass().name}.project")
        if(classProject) {
            return classProject
        }
        else if(this.projectInternal != null) {
            return this.projectInternal
        }
    }
    void setProject(String p) {
        this.projectInternal = p
    }
    def getPort() {
        def classPort = System.getProperty("${this.getClass().name}.port")
        if(this.portInternal != null) {
            return this.portInternal
        }
        else if(classPort) {
            return classPort
        }
        else {
            return BaseSpec.PORT
        }
    }
    void setPort(p) {
        this.portInternal = p
    }

    void setup() {
        if(getPort() != null && getProject() != null)    {

            def url = "http://localhost:${getPort()}/${getProject()}/"
            browser.baseUrl = url
        }
    }
    void cleanup() {
        // workaround for Geb bug which fails with NPE on cleanup if no browser created
        if(browser == null) {
            browser = new Browser()
        }
    }
    void cleanupSpec() {

        for(Process p in processes) {
            p.destroy()
        }
        processes.clear()
        
        for(File dir in cleanupDirectories) {
            silentDelete(dir)
        }
        cleanupDirectories.clear()
        silentDelete(new File(grailsWorkDir))
        silentDelete(new File(outputDir))
    }

    protected silentDelete(File dir) {
        if(dir.exists()) {
            try {
                FileUtils.deleteDirectory(dir)
            } catch (e) {
                // ignore
            }
        }
    }

    def grails(Closure callable) {
        def executor = new GrailsExecutor(parent:this)
        callable.delegate = executor
        callable.resolveStrategy = Closure.DELEGATE_ONLY
        callable.call()
    }

    def grailsDebug(Closure callable) {
        def executor = new GrailsExecutor(parent:this, debug:true)
        callable.delegate = executor
        callable.resolveStrategy = Closure.DELEGATE_ONLY
        callable.call()
    }
    
	
	def execute(String project, CharSequence[] command) {
        return executeInternal(false, command, project)
	}

    def executeDebug(String project, CharSequence[] command) {
        return executeInternal(true, command, project)
    }

    private int executeInternal(boolean debug, CharSequence[] command, String project) {
        this.command = command.toList() + "--plainOutput"
        def process = createProcess(debug,project, * this.command)
        def outputBuffer = new StringBuffer()
        process.consumeProcessOutputStream(outputBuffer)

        process.waitForOrKill(PROCESS_TIMEOUT_MILLS)
        exitStatus = process.exitValue()
        output = outputBuffer.toString()

        return exitStatus
    }

    Process executeAsync(String project, CharSequence[] command) {
        this.command = command.toList() + "--plainOutput"
        def process = createProcess(false, project, *this.command)
        return process
    }

    Process executeDebugAsync(String project, CharSequence[] command) {
        this.command = command.toList() + "--plainOutput"
        def process = createProcess(true, project, *this.command)
        return process
    }

	def upgradeProject(project) {
		upgradedProjects << project
        def result = execute(project, 'upgrade', '--non-interactive')
        assert output?.contains('Project upgraded')
        assert result == 0
    }

    void assertStatus(code) {
        if(browser.driver instanceof HtmlUnitDriver) {
            statusCode == code
        }
    }
    int getStatusCode() {
        browser.driver.webClient.webWindows.first().enclosedPage.webResponse.statusCode
    }

	private Process createProcess(boolean debug,String project, CharSequence[] command) {
		if (project != null && !(project in upgradedProjects)) { upgradeProject(project) }
		
		def completeCommand = ["${grailsHome}/bin/${debug ? 'grails-debug' : 'grails'}", "-Dgrails.work.dir=${grailsWorkDir}/${project} -Dgrails.project.work.dir=${projectWorkDir}/${project}", "--non-interactive", '--stacktrace']
        if(port != null) {
            completeCommand << "-Dgrails.server.port.http=${port}"
        }
		completeCommand.addAll(command.toList()*.toString())

        def toExecute = completeCommand as String[]
        def dir
        
        if(project)
            dir = new File(projectsBaseDir, project)
        else
            dir = new File(projectsBaseDir)
        
        if(!dir.exists()) {
            dir = new File(autostartBaseDir, project)
        }
        
        println "Running command: ${toExecute.join(" ")}"
        println "Base directory: $dir"

        projectDir = dir
        (Process)new ProcessBuilder(toExecute).with {
			redirectErrorStream(true)
            directory(dir)
			environment()["GRAILS_HOME"] = grailsHome
			start()
		}
	}
    
}
