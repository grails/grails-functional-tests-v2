package grails.functional.tests

import geb.spock.GebSpec
import grails.functional.tests.internal.GrailsExecutor

abstract class BaseSpec extends GebSpec{
    
	static PORT = 8184
	static PROCESS_TIMEOUT_MILLS = 1000 * 60 * 5 // 5 minutes
	static upgradedProjects = []
	
	static grailsHome = new File(requiredSysProp('grailsHome', "../grails-master")).canonicalPath
	static grailsWorkDir = requiredSysProp('grailsWorkDir', System.getProperty("java.io.tmpdir"))
	static projectWorkDir = requiredSysProp('projectWorkDir', findChildOfRoot("apps"))
	static outputDir = requiredSysProp('outputDir',System.getProperty("java.io.tmpdir"))
	
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
        
        return new File(current, "apps").absolutePath
    }

    
	def command
	def exitStatus
	def output
	def project
    def processes = []
    def cleanupDirectories = []
    def port = BaseSpec.PORT
    
    void cleanup() {
        for(Process p in processes) {
            p.destroy()
        }
        for(File dir in cleanupDirectories) {
            dir.deleteOnExit()
        }
        new File(grailsWorkDir).deleteOnExit()
        new File(outputDir).deleteOnExit()
    }
    
    def grails(Closure callable) {
        def executor = new GrailsExecutor(parent:this, project:project)
        callable.delegate = executor
        callable.resolveStrategy = Closure.DELEGATE_ONLY
        callable.call()
    }
    
	
	def execute(String project, CharSequence[] command) {
		this.command = command.toList() + "--plainOutput"
		def process = createProcess(project, *this.command)
		def outputBuffer = new StringBuffer()
		process.consumeProcessOutputStream(outputBuffer)

        process.waitForOrKill(PROCESS_TIMEOUT_MILLS)
        exitStatus = process.exitValue()
        output = outputBuffer.toString()
        println output
        return exitStatus
	}

    Process executeAsync(String project, CharSequence[] command) {
        this.command = command.toList() + "--plainOutput"
        def process = createProcess(project, *this.command)
        return process
    }    

	def upgradeProject(project) {
		upgradedProjects << project
		assert execute(project, 'upgrade', '--non-interactive') == 0
		assert output.contains('Project upgraded')
	}

	private Process createProcess(String project, CharSequence[] command) {
		if (project != null && !(project in upgradedProjects)) { upgradeProject(project) }
		
		def completeCommand = ["${grailsHome}/bin/grails", "-Dgrails.server.port.http=${port}", "-Dgrails.work.dir=${grailsWorkDir}", "--non-interactive"]
		completeCommand.addAll(command.toList()*.toString())

        def toExecute = completeCommand as String[]
        def dir
        if(project)
            dir = new File(projectWorkDir, project)
        else
            dir = new File(projectWorkDir)
        
        println "Running command: ${toExecute.join(" ")}"
        println "Base directory: $dir"

        (Process)new ProcessBuilder(toExecute).with {
			redirectErrorStream(true)
            directory(dir)
			environment()["GRAILS_HOME"] = grailsHome
			start()
		}
	}
    
}
