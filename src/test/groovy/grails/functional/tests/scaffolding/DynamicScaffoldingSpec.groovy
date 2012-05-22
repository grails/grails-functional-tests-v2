package grails.functional.tests.scaffolding

import grails.functional.tests.BaseSpec
import geb.download.DownloadException

/**
 * 
 */
class DynamicScaffoldingSpec extends BaseSpec{
    
    void "Test enable dynamic scaffolding whilst container is running"() {
        when:"A new application is created with a domain class and a controller"
            grails {
                createApp "dynamic-scaffold-test"
                createDomainClass "book"
                createController "book"
                runApp()
            }

            def controllerFile = new File("${projectDir}/grails-app/controllers/dynamic/scaffold/test/BookController.groovy")


        then:"The controller file is created"
            controllerFile.exists()

        when:"The home page is requested"
            go "/dynamic-scaffold-test"

        then:"The welcome page is shown"
            title == "Welcome to Grails"
            
        when:"A scaffolded page is requested"
            go "/dynamic-scaffold-test/book/list"
            browser.page.downloadContent()
        then:"The page doesn't exist"
            thrown DownloadException
        
        when:"The controller is modified to enable dynamic scaffolding"
            controllerFile.text = '''
package dynamic.scaffold.test

class BookController {
    static scaffold = Book
}
'''
            sleep 5000
            go "/dynamic-scaffold-test/book/list"

        then:"The page exists"
            title == "Book List"
    }
}
