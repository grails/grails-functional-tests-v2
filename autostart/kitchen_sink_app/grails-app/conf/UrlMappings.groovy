class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?"{
            constraints {
                // apply constraints here
            }
        }
        "/path/$file.$ext"(controller:"urlMappingsTest", action:"testExtension")

        "/dynamic/variable" {
            controller = "urlMappingsTest"
            action = "dynaVariable"
            id = { params.id }
        }

        "/declared/params" {
            controller = "urlMappingsTest"
            action = "declaredParams"		
            var = 'foo'; var2='bar'
        }
        "/decode/$myparam?"(controller:"urlMappingsTest", action:"decode")
        "/post"(controller: "post", action: "index") {
            parseRequest = true
        }

        "/containsBean/$beanName"(controller: 'namespaceInspector', action: 'containsBean')
        "/beanType/$beanName"(controller: 'namespaceInspector', action: 'beanType')

        '/pluginOneFirstController' {
            controller = 'first'
            action = 'index'
            plugin = 'namespaceOne'
        }
        '/pluginTwoFirstController' {
            controller = 'first'
            action = 'index'
            plugin = 'namespaceTwo'
        }
        '/noPluginFirstController' {
            controller = 'first'
            action = 'index'
        }
        '/noPluginSecondController' {
            controller = 'second'
            action = 'index'
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(controller: 'i18nError', action: 'pageNotFound')
    }
}
