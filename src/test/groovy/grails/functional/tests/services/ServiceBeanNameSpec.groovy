package grails.functional.tests.services

import grails.functional.tests.BaseApplicationSpec;

class ServiceBeanNameSpec extends BaseApplicationSpec {

    void 'Test service name defined in the app and in namespace-one plugin and in namespace-two plugin'() {
        when:
        go 'containsBean/gadgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'gadgetService'? true"))
        
        when:
        go 'beanType/gadgetService'

        then:
        statusCode == 200
        $(text: contains("gadgetService is an instance of namespace.GadgetService"))
        
        when:
        go 'containsBean/namespaceOneGadgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'namespaceOneGadgetService'? true"))
        
        when:
        go 'beanType/namespaceOneGadgetService'

        then:
        statusCode == 200
        $(text: contains("namespaceOneGadgetService is an instance of com.namespaceone.GadgetService"))
        
        when:
        go 'containsBean/namespaceTwoGadgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'namespaceTwoGadgetService'? true"))
        
        when:
        go 'beanType/namespaceTwoGadgetService'

        then:
        statusCode == 200
        $(text: contains("namespaceTwoGadgetService is an instance of com.namespacetwo.GadgetService"))
    }

    void 'Test service name defined in namespace-one plugin and in namespace-two plugin'() {
        when:
        go 'containsBean/fidgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'fidgetService'? false"))
        
        when:
        go 'containsBean/namespaceOneFidgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'namespaceOneFidgetService'? true"))
        
        when:
        go 'beanType/namespaceOneFidgetService'

        then:
        statusCode == 200
        $(text: contains("namespaceOneFidgetService is an instance of com.namespaceone.FidgetService"))
        
        when:
        go 'containsBean/namespaceTwoFidgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'namespaceTwoFidgetService'? true"))
        
        when:
        go 'beanType/namespaceTwoFidgetService'

        then:
        statusCode == 200
        $(text: contains("namespaceTwoFidgetService is an instance of com.namespacetwo.FidgetService"))
    }
    
    void 'Test service name defined in namespace-one plugin'() {
        when:
        go 'containsBean/widgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'widgetService'? true"))
        
        when:
        go 'beanType/widgetService'

        then:
        statusCode == 200
        $(text: contains("widgetService is an instance of com.namespaceone.WidgetService"))
        
        when:
        go 'containsBean/namespaceOneWidgetService'

        then:
        statusCode == 200
        $(text: contains("Contains bean 'namespaceOneWidgetService'? true"))
        
        when:
        go 'beanType/namespaceOneWidgetService'

        then:
        statusCode == 200
        $(text: contains("namespaceOneWidgetService is an instance of com.namespaceone.WidgetService"))
    }
    
    @Override
    String getApplication() {
        'kitchen_sink_app'
    }
}