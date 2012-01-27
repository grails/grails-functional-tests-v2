package grails.functional.tests.web

import grails.functional.tests.BaseApplicationSpec

/**
 * Tests the support for hyphenated URLs
 */
class HyphenatedUrlsSpec extends BaseApplicationSpec{


    void "Test default action execution"() {
        when:"The default action is executed"
            go 'first-demo'
        then:"The index action is executed correctly"
            contains 'FirstDemoController index action'
    }

    void "Test the default action is called directly"() {
        when:"The default index action is called directly"
            go 'first-demo/index'
        then:"The index action is executed correctly"
            contains 'FirstDemoController index action'
    }

    void "Test executed a method style action with hyphenated URLs"() {
        when:"A method action that maps to a hyphenated URL is executed"
            go 'first-demo/some-method-action'
        then:"The correct action is executed"
            contains 'FirstDemoController someMethodAction action'
    }

    void "Test execute a closure style action with hyphenated URLs"() {
        when:"A closure action mapped with a hypenated URL is executed"
            go 'first-demo/some-closure-action'
        then:"The correct action is executed"
            contains 'FirstDemoController someClosureAction action'
    }

    void "Test default action execution 2"() {
        when:"The default action is executed"
        go 'second-demo'
        then:"The index action is executed correctly"
        contains 'SecondDemoController index action'
    }

    void "Test the default action is called directly 2"() {
        when:"The default index action is called directly"
            go 'second-demo/index'
        then:"The index action is executed correctly"
            contains 'SecondDemoController index action'
    }

    void "Test executed a method style action with hyphenated URLs 2"() {
        when:"A method action that maps to a hyphenated URL is executed"
            go 'second-demo/some-method-action'
        then:"The correct action is executed"
            contains 'SecondDemoController someMethodAction action'
    }

    void "Test execute a closure style action with hyphenated URLs 2"() {
        when:"A closure action mapped with a hypenated URL is executed"
            go 'second-demo/some-closure-action'
        then:"The correct action is executed"
            contains 'SecondDemoController someClosureAction action'
    }

    void "Test reverse mappped link generation"() {
        when:"A view with hyphenated URLs is requested"
            go 'second-demo/render-some-view'
        then:
            contains '''Click <a href="/hyphenatedurls/second-demo">here</a> To execute the default action in SecondDemoController.'''
            contains '''Click <a href="/hyphenatedurls/second-demo/index">here</a> To execute the index action in SecondDemoController.'''
            contains '''Click <a href="/hyphenatedurls/second-demo/some-method-action">here</a> To execute the someMethodAction action in SecondDemoController.'''
            contains '''Click <a href="/hyphenatedurls/second-demo/some-closure-action">here</a> To execute the someClosureAction action in SecondDemoController.'''
            contains '''Click <a href="/hyphenatedurls/second-demo/render-some-view">here</a> To execute the renderSomeView action in SecondDemoController.'''
    }

    void "Test scaffolding"() {
        when:"The default scaffolded action is requested"
            go 'my-scaffolded'
        then:"The list page is returned"
            contains 'Person List'

        when:"The list scaffolded action is requested"
            go 'my-scaffolded/list'
        then:"The list page is returned"
            contains 'Person List'

        when:"The create scaffolded page is requested"
            go 'my-scaffolded/create'
        then:"The create page is returned"
            contains 'Create Person'
    }


    void "Test default action execution 3"() {
        when:"The default action is executed"
            go 'third-demo'
        then:"The index action is executed correctly"
            contains 'This is grails-app/views/thirdDemo/myDefaultAction.gsp'
    }

    void "Test the default action is called directly 3"() {
        when:"The default index action is called directly"
            go 'third-demo/my-default-action'
        then:"The index action is executed correctly"
            contains 'This is grails-app/views/thirdDemo/myDefaultAction.gsp'
    }

    
    void "Test index action 3"() {
        when:"The index action is called directly"
            go 'third-demo/index'
        then:"The correct response is returned"
            contains 'This is grails-app/views/thirdDemo/index.gsp'
    }    

    @Override
    String getApplication() {
        "hyphenatedurls"
    }


}
