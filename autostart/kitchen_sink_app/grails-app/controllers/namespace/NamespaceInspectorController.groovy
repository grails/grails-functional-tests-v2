package namespace

class NamespaceInspectorController {
    
    def dataSource
    def containsBean(String beanName) {
        render "Contains bean '${beanName}'? ${grailsApplication.mainContext.containsBean(beanName)}"
    }
	
    def beanType(String beanName) {
        render "${beanName} is an instance of ${grailsApplication.mainContext.getBean(beanName).class.name}"
    }
    
	def showTableNames() {
        def conn = dataSource.connection
        def metaData = conn.metaData
        def tableResultSet = metaData.getTables(null, null, '%', null)
        def tableNames = []
        while(tableResultSet.next()) {
            tableNames << tableResultSet.getString('TABLE_NAME')
        }
		[tableNames: tableNames]
	}

    def redirectToNoPlugin() {
        redirect action: 'index', controller: 'first'
    }

    def redirectToPluginOne() {
        redirect action: 'index', controller: 'first', plugin: 'namespaceOne'
    }

    def redirectToPluginTwo() {
        redirect action: 'index', controller: 'first', plugin: 'namespaceTwo'
    }

    def chainToNoPlugin() {
        chain controller: 'first', action: 'index'
    }

    def chainToPluginOne() {
        chain controller: 'first', action: 'index', plugin: 'pluginOne'
    }

    def chainToPluginTwo() {
        chain controller: 'first', action: 'index', plugin: 'pluginTwo'
    }

    def generateLinksToNamespacedControllers() {}
}
