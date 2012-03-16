package grails.functional.tests.gorm

import grails.functional.tests.BaseApplicationSpec

/**
 * Tests the lazy loading functionality
 */
class LazyLoadingInLayoutSpec extends BaseApplicationSpec{
    @Override
    String getApplication() {
        "kitchen_sink_app"
    }
    
    void "Test that lazy loading in layouts works correctly"() {
        when:"A page that has a lazy association rendered in a layout"
            println "BROWSER BASE URL IS ${browser.baseUrl}"
            println "URL IS ${calculateUri('child/testLazyLoadInLayout', [:])}"
            go "child/testLazyLoadInLayout"

        then:"Then the response is returned correctly"
            statusCode == 200
            contains "<p>joy</p>"
    }
}
