package grails.functional.tests.structurededitor.pages

import geb.Page
import groovy.lang.MetaClass;

class ShowGamePage extends Page {
    static at = {
        title == 'Show Game'
    }
}
