package grails.functional.tests.structurededitor

import grails.functional.tests.BaseApplicationSpec
import grails.functional.tests.structurededitor.pages.CreateGamePage
import grails.functional.tests.structurededitor.pages.ShowGamePage

class StructuredEditorSpec extends BaseApplicationSpec {
    
    void 'Test structured editor'() {
        // Test related to GRAILS-8998
        
        when:
        to CreateGamePage
        
        then:
        at CreateGamePage
        
        when:
        goalNameField.value('My Goal Name')
        goalOwnField.value('9')
        goalOtherField.value('4')
        createGameButton.click()
        
        then:
        at ShowGamePage
    }
    
    @Override
    String getApplication() {
        'kitchen_sink_app'
    }
}
