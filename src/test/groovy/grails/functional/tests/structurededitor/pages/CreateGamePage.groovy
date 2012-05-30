package grails.functional.tests.structurededitor.pages

import geb.Page

class CreateGamePage extends Page {
    static url = 'game/create'
    
    static content = {
        goalNameField { $('input', id: 'name') }
        goalOwnField { $('input', id: 'goal_own') }
        goalOtherField { $('input', id: 'goal_other') }
        createGameButton { $('input', id: 'create') }
    }
    
    static at = {
        title == 'Create Game'
    }
}
