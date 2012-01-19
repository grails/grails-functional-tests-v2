package grails.functional.tests.spring

import grails.functional.tests.BaseApplicationSpec

/**
 * Tests for Spring integration
 */
class SpringBeanSpec extends BaseApplicationSpec{
    @Override
    String getApplication() {
        return "spring"
    }


    void "Test override existing Spring bean"() {
        when:"A request for an overridden bean is exectued"
            go 'test/testOverridenBean'

        then:"The overriden bean is loaded"
            contains 'Resolver class = org.springframework.web.servlet.i18n.FixedLocaleResolver'
    }

    void "Test overriding existing Spring bean"() {
        when:"A request for an overridden bean is exectued"
            go ('test/testOverridenBeanXml')
        then:"The bean is correctly overriden"
            contains 'MessageSource class = org.springframework.context.support.StaticMessageSource'
    }

    void "Test Spring alias"() {
        when:"A request that uses a spring bean alias is issued"
            go 'test/testSpringBeanAlias'
        then:"The alias works correctly"
            contains 'Aliased bean = org.springframework.context.support.StaticMessageSource'

    }

    void "Test use Spring namespace configuration"() {
        when:"A request that uses Spring namespace configuration is issued"
            go '/test/testNamespaceConfig'
        then:"The namespace configuration works correctly"
            contains 'Component class = beans.TestComponent'

    }

    void "Test use Spring request scope"() {
        when:"A request is issued for a Spring request scoped bean"
            go 'test/testRequestScopedBean'
        then:"The request scoped bean works correctly"
            contains 'Scoped bean = true'
    }

    void "Test lookup ServletContext via Spring"() {
        when:"The ServletContext is looked up"
            go '/test/testAppCtxInServletContextA'
        then:"The ServletContext is available"
            contains 'Resolver class = org.springframework.web.servlet.i18n.FixedLocaleResolver'
    }

    void "Test lookup ServletContext via Spring alternate"() {
        when:"The ServletContext is looked up"
            go '/test/testAppCtxInServletContextB'
        then:"The ServletContext is available"
            contains 'Resolver class = org.springframework.web.servlet.i18n.FixedLocaleResolver'
    }
}
