package grails.functional.tests.web

import grails.functional.tests.BaseApplicationSpec

/**
 *
 */
class DefaultCodecSpec extends BaseApplicationSpec{

    // GRAILS-8405
    void "Test resource tag with default codec enabled"() {
        when:"A page is requested that using the request tag"
            go 'demo'
        then:"The resource tag renders correctly"
            contains 'Image: <img src="/html-default-codec/static/images/grails_logo.png" alt="Grails"/>'
    }

    @Override
    String getApplication() {
        "html-default-codec"
    }
}
