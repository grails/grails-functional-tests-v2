package grails.functional.tests.gorm

import grails.functional.tests.BaseApplicationSpec
import geb.Page

/**
 * Tests for validation handling
 */
class ValidationHandlingSpec extends BaseApplicationSpec{

    void "Test binding error handling"() {
        when:"A form is requested"
            to CreatePage

        then:"The page is obtained successfully"
            statusCode == 200
            at CreatePage

        when:"A form is populated with valid data"
            createForm.name = "test"
            createForm.age = 25
            createButton.click()

        then:"The domain class is persisted and the show page rendered"
            statusCode == 200
            at ShowPage

        when:"The edit button is clicked"
            editButton.click()

        then:"We're at the edit page"
            at EditPage

    }

    @Override
    String getApplication() {
        "kitchen_sink_app"
    }
}

class CreatePage extends Page{
    static url = 'test/create'
    static at = {
        title == 'Create Test'
    }
    static content = {
        createForm { $('form') }
        createButton(to:ShowPage) { $('input', type:'submit') }
    }
}
class ShowPage extends Page {
    static at = {
        contains "Test 2 created"
    }
    static content = {
        editButton(to: EditPage) { $('input', name:'_action_Edit') }
    }
}
class EditPage extends Page {

    static at = {
        contains "Edit Test"
        $('form').name == 'test'
        $('form').age == '25'
    }

    
    static content = {
        editForm { $('form') }
        updateButton(to: EditPage) { $('input', '_action_Update') }
    }
}