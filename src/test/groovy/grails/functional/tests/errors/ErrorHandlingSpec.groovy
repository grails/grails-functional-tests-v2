package grails.functional.tests.errors

import grails.functional.tests.BaseApplicationSpec
import spock.lang.Ignore
import spock.lang.Issue

/**
 * Tests the errors produced for various scenarios using the old Grails 1.3.x and below errors view. Grails 2.0 has wider
 * unit test coverage of the errors view hence this has not been ported over
 */
class ErrorHandlingSpec extends BaseApplicationSpec{

    void "Test standard exception handling"() {
        when:"An action that throws an exception is called"
            go '/errors/errors/standard'

        then:"The right status code and content is returned"
            assertStatus 500
            contains 'Grails Runtime Exception'
            $('#spinner')
            $('div', class:'logo')
    }

    void "Test declarative exception handling"() {
        when:"An action is called that throws a user exception"
            go '/errors/errors/declarative'

        then:"The declarative exception handler is invoked instead of the standard one"
            assertStatus 500
            contains 'Exception was org.codehaus.groovy.grails.web.errors.GrailsWrappedRuntimeException'
    }

    void "Test that an error view with a layout is rendered correctly"() {
        when:"An error view that applies a layout is requested"
            go '/errors/errors/layout'

        then:"The layout is applied"
            assertStatus 500
            contains '<h1>Test Layout</h1>'
            contains 'Grails Runtime Exception'
            contains '<strong>Class:</strong> ErrorsController'
            contains '<strong>At Line:</strong> [12]'
            contains 'throw new IllegalArgumentException(&quot;bad&quot;)'
    }



    void "Test an error that emerges from a tag library"() {
        when:"A tag library throws an error"
            go '/errors/errors/gstringTagError'
        
        then:"The error is correctly displayed"
            assertStatus 500

            contains 'Grails Runtime Exception'
            contains '<strong>Class:</strong> MyTagLib'
            contains '<strong>Exception Message:</strong> No such property: fooo for class: MyTagLib'
            contains '<strong>At Line:</strong>'
    }

    void "Test an error that emerges from a GSP expression"() {
        given:
            go '/errors/errors/warDeployed'
            if (contains('war=true')) {
                return
            }

        when:"A GSP expression throws an error"
            go '/errors/errors/tagExpressionError'


        then:"The error is correctly displayed"
            assertStatus 500

            contains 'Grails Runtime Exception'
            contains 'tagExpressionError.gsp '
            contains '<strong>Exception Message:</strong> Cannot get property &#39;bar&#39; on null object'
            contains '<strong>Caused by:</strong> Error evaluating expression [foo.bar] on line [14]: Cannot get property &#39;bar&#39; on null object'
            contains '<strong>At Line:</strong> [14]'
            contains '&lt;g:each var=&quot;c&quot; in=&quot;${foo.bar}&quot;&gt;'

    }

    void "Test an error that emerges from a regular expression"() {
        given:
            go '/errors/errors/warDeployed'
            if (contains('war=true')) {
                return
            }

        when:"A regex expression throws an error"
            go '/errors/errors/regularExpressionError'

        then:"The error is correctly displayed"
            assertStatus 500
            contains 'Grails Runtime Exception'
            contains 'regularExpressionError.gsp '
            contains '<strong>Exception Message:</strong> No signature of method: org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass.dummy() is applicable for argument types: () values: []'
            contains '<strong>Caused by:</strong> Error evaluating expression [c.dummy()] on line [15]: groovy.lang.MissingMethodException: No signature of method: org.codehaus.groovy.grails.commons.DefaultGrailsControllerClass.dummy() is applicable for argument types: () values: []'
            contains '<strong>At Line:</strong> [15]'
            contains '&lt;li class=&quot;controller&quot;&gt;This will throw MPE ${c.dummy()}&lt;/li&gt;'

    }

    void "Test error from internal tag"() {
        given:
            go '/errors/errors/warDeployed'
            if (contains('war=true')) {
                return
            }

        when:"An internal tag throws an error"
            go '/errors/errors/internalTagError'

        then:"The error is correctly displayed"
            contains 'Grails Runtime Exception'
            contains 'internalTagError.gsp '
            contains '<strong>Exception Message:</strong> Tag [submitButton] is missing required attribute [name] or [field] '
            contains '<strong>Caused by:</strong> Error processing GroovyPageView: Tag [submitButton] is missing required attribute [name] or [field]'
            contains '<strong>At Line:</strong> [16]'
            contains '&lt;g:submitButton&gt;&lt;/g:submitButton&gt;'


    }


    void "Test error from invalid dynamic finder"() {
        when:"A dynamic finder throws an error"
            go('/errors/errors/invalidGormMethod')
        then:"The error is correctly reported"
            assertStatus 500

            contains 'Grails Runtime Exception'
            contains 'ErrorsController'
            contains '<strong>Exception Message:</strong> No property found for name [rubbish] for class [class Book]'
            contains '<strong>Caused by:</strong> No property found for name [rubbish] for class [class Book] <br />'
            contains '<strong>At Line:</strong> [32]'
            contains '[books:Book.findAllByRubbish(&quot;yes&quot;)]'
    }

    void "Test 404 error with a redirect"() {
        when:"A request is issues for a 404 that redirects"
            go('/errors/errors/bad')
        then:"200 due to the client side redirect"
            assertStatus 200
            contains 'not there'
    }

    /**
     * Test for GRAILS-6627
     *
     * The 'renderView' action renders the 'index' view directly. The
     * associated error page should be displayed ('errors/forwardError')
     * but the above bug means that an exception is thrown.
     */
    void "Test forwarding error with render view"() {
        when:"an error occurs and a view to render has been specified"
            go('/errors/home/renderView')

        then:"The errors view is rendered and not the view that was specified previously"
            assertStatus 500
            contains "Something went wrong on the server!"
    }


    /**
     * Test for GRAILS-6628
     *
     * Check that URL parameters are not passed on to the error handler.
     */
    @Ignore
    @Issue('GRAILS-6628')
    void "Test URL parameters are not passed to error handler"() {
        when:"An request has parameters"
            go('/errors/home/index?aabbccdd=true')

        then:"the parameters are not passed to the error handler"
            assertStatus 500
            contains "Something went wrong on the server!"
            notContains"aabbccdd"
    }

    @Ignore
    @Issue('GRAILS-6650')
    void testWithStaticGspContentInErrorPage() {
        // this one throws a NullPointerException which is mapped in UrlMappings
        when:"a page that has no body throws a NPE is rendered"
            go '/errors/home/renderError'

        then:"The correct error is returned"
            assertStatus 500
            contains 'Something went wrong on the server!'
            notContains '<h2>Hello world!</h2>'

        when:"this one throws an UnsupportedOperationException which is not mapped in UrlMappings"
            go'/errors/home/throwUoe'
        then:"the correct response is returned"
            assertStatus 500
            notContains '<h2>Hello World</h2>'
    }

    @Override
    String getApplication() {
        return "errors"
    }
}
