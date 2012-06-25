package grails.functional.tests.gorm

import grails.functional.tests.BaseApplicationSpec;
import groovy.lang.MetaClass;

class IdBindingSpec extends BaseApplicationSpec {

	void 'Test a non bindable id'() {
		when:
		go 'databinding/createWidgetWithNonBindableId?name=Joe&id=42'
		
		then:
		statusCode == 200
		$(text: contains('Name is Joe and id is null.'))
	}
	
	void 'Test a bindable id'() {
		when:
		go 'databinding/createWidgetWithBindableId?name=Joe&id=42'
		
		then:
		$(text: contains('Name is Joe and id is 42.'))
	}
	
	@Override
	public String getApplication() {
		'kitchen_sink_app'
	}
}
