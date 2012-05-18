package namespace

class NamespaceInspectorController {
    
    def dataSource
    def containsBean(String beanName) {
        render "Contains bean '${beanName}'? ${grailsApplication.mainContext.containsBean(beanName)}"
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
}