package grails.functional.tests

/**
 * Various tests for dependency resolution
 */
class DependencyResolutionSpec extends BaseApplicationSpec{
    @Override
    String getApplication() {
        "dependency-resolution"    
    }

    @Override
    boolean shouldStartApp() {
        false
    }

    void "Test that a excluding a jar from a plugin works correctly"() {
        given:"An initial compile"
            grails {
                compile()
            }
        
        when:"A script is run that checks to see if a class from an excluded jar is present"
            grails {
                pluginDependencyExcludes()
            }
        then:"The class is not present"
            exitStatus == 0
            output.contains 'http-builder not present'
    }
}
