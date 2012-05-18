package grails.functional.tests.gorm

import grails.functional.tests.BaseApplicationSpec
import groovy.lang.MetaClass;

class NamespacedDomainClassesSpec extends BaseApplicationSpec {

    void 'Test table names'() {
        when:
        go 'namespaceInspector/showTableNames'

        then:
        statusCode == 200
        title == 'Table Names'
        $('li', text: 'PHOTO_COLLECTION')
        $('li', text: 'NAMESPACE_ONE_PHOTO_COLLECTION')
        $('li', text: 'NAMESPACE_TWO_PHOTO_COLLECTION')
        $('li', text: 'SCRAP_BOOK')
        !$('li', text: 'NAMESPACE_ONE_SCRAP_BOOK')
    }

    @Override
    String getApplication() {
        'kitchen_sink_app'
    }
}
