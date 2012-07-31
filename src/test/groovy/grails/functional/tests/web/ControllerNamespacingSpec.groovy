package grails.functional.tests.web

import grails.functional.tests.BaseApplicationSpec
import spock.lang.Ignore

class ControllerNamespacingSpec extends BaseApplicationSpec {
    
    void 'Test link generation'() {
        when:
        go 'namespaceInspector/generateLinksToNamespacedControllers'
        
        then:
        statusCode == 200
        
        when:
        $('a', text:contains('Link To FirstController In namespaceTwo')).click()
        
        then:
        statusCode == 200
        $(text: contains('com.namespacetwo.FirstController index action'))
        
        when:
        go 'namespaceInspector/generateLinksToNamespacedControllers'
        
        then:
        statusCode == 200
        
        when:
        $('a', text:contains('Link To FirstController In namespaceOne')).click()
        
        then:
        statusCode == 200
        $(text: contains('com.namespaceone.FirstController index action'))
        
        when:
        go 'namespaceInspector/generateLinksToNamespacedControllers'
        
        then:
        statusCode == 200
        
        when:
        $('a', text:contains('Link To FirstController In Application')).click()
        
        then:
        statusCode == 200
        $(text: contains('namespace.FirstController index action'))
    }
    
	void 'Test controller defined in app and multiple plugins'() {
		when:
		go 'first/index'

		then:
		statusCode == 200
		$(text: contains('namespace.FirstController index action'))
		
		when:
		go 'first'

		then:
		statusCode == 200
		$(text: contains('namespace.FirstController index action'))
		
		when:
		go 'noPluginFirstController'

		then:
		statusCode == 200
		$(text: contains('namespace.FirstController index action'))
		
		when:
		go 'pluginOneFirstController'
		
		then:
		statusCode == 200
		$(text: contains('com.namespaceone.FirstController index action'))
		
		when:
		go 'pluginTwoFirstController'
		
		then:
		statusCode == 200
		$(text: contains('com.namespacetwo.FirstController index action'))
	}

	void 'Test controller defined only in a plugin'() {
		when:
		go 'second/index'

		then:
		statusCode == 200
		$(text: contains('com.namespaceone.SecondController index action'))
		
		when:
		go 'second'

		then:
		statusCode == 200
		$(text: contains('com.namespaceone.SecondController index action'))
		
		when:
		go 'noPluginSecondController'

		then:
		statusCode == 200
		$(text: contains('com.namespaceone.SecondController index action'))
	}
    
    void 'Test redirects'() {
        when:
        go 'namespaceInspector/redirectToNoPlugin'
        
        then:
        statusCode == 200
        $(text: contains('namespace.FirstController index action'))
        
        when:
        go 'namespaceInspector/redirectToPluginOne'
        
        then:
        statusCode == 200
        $(text: contains('com.namespaceone.FirstController index action'))
        
        when:
        go 'namespaceInspector/redirectToPluginTwo'
        
        then:
        statusCode == 200
        $(text: contains('com.namespacetwo.FirstController index action'))
    }
    
    @Ignore
    void 'Test chaining'() {
        when:
        go 'namespaceInspector/chainToNoPlugin'
        
        then:
        statusCode == 200
        $(text: contains('namespace.FirstController index action'))
        
        when:
        go 'namespaceInspector/chainToPluginOne'
        
        then:
        statusCode == 200
        $(text: contains('com.namespaceone.FirstController index action'))
        
        when:
        go 'namespaceInspector/chainToPluginTwo'
        
        then:
        statusCode == 200
        $(text: contains('com.namespacetwo.FirstController index action'))
    }

	@Override
	public String getApplication() {
		'kitchen_sink_app'
	}

}
