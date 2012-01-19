package grails.functional.tests.web

import grails.functional.tests.BaseApplicationSpec

/**
 *
 */
class TaglibOverrideSpec extends BaseApplicationSpec {
    @Override
    String getApplication() {
        return "tag-libraries"
    }

    void "Test override core tag"() {
        when:"A request is sent to a page that uses a tag library that overrides a core tag"
            go ''
        then:"The overriden tag library is correctly used"
            contains '<p>Message: overriden</p>'
    }
}
