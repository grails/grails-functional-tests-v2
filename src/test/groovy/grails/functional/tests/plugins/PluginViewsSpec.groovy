package grails.functional.tests.plugins

import grails.functional.tests.BaseApplicationSpec

/**
 * Tests for plugin view rendering
 */
class PluginViewsSpec extends BaseApplicationSpec {


    void "Test plugin view renders correctly"() {
        when:"A request for a plugin that renders a view is executed"
            go "dbUtil/data"

        then:"The correct content is returned"
            title == "Database Dump"
            contains "<h1>Display Data</h1>"
            $("a", text:'Database Info')
            $("a", text: 'Display Data')
            $("a", text:'Execute SQL')
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags include
     * the plugin context path in plugin views.
     */
    void "Test that resource tags include the plugin context path in resource links"() {
        when:"A plugin view is requested"
            go "dbUtil/data"

        then:"The correct resource links are produced"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/dbUtil.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/standard.css\"/>"
            contains "<script src=\"/kitchen_sink_app/plugins/db-util-0.3/js/dojo.js\" type=\"text/javascript\">"
            contains "<script src=\"/kitchen_sink_app/plugins/db-util-0.3/js/application.js\" type=\"text/javascript\">"
            $('#pluginContext').text() == '/plugins/db-util-0.3'
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path for a plugin view that has been overridden
     * by the application. The customised view uses an application layout.
     */
    void "Test that no plugin context path is present if a plugin view is overriden by the application"() {
        when:"An application view that overrides a plugin view is requested"
            go "dbUtil/sql"
        then:"The correct resource links are produced"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/css/main.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/css/other.css\"/>"
            contains "<script src=\"/kitchen_sink_app/js/app-layout.js\" type=\"text/javascript\">"
            contains "<script src=\"/kitchen_sink_app/js/app.js\" type=\"text/javascript\">"
            contains "<script src=\"/kitchen_sink_app/js/application.js\" type=\"text/javascript\">"

    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path when they are in an application layout
     * that is used by a plugin view. The tags in the plugin view should
     * include the plugin context path.
     */
    void "Test that an application layout used by a plugin view does not include plugin context page in links"() {
        when:"A plugin view with an application layout is requested"
            go "dbUtil/info"
        then:"Then resource links in the layout don't include the plugin path whilst resource links in the plugin view do"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/css/main.css\"/>"

            contains "<script src=\"/kitchen_sink_app/js/app-layout.js\" type=\"text/javascript\">"
            contains "<script src=\"/kitchen_sink_app/js/application.js\" type=\"text/javascript\">"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/other.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/other-2.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/other-3.css\"/>"
            contains "<script src=\"/kitchen_sink_app/plugins/db-util-0.3/js/plugin-info.js\" type=\"text/javascript\">"
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path for a plugin view that has been overridden
     * by the application. The view uses a plugin layout, so the plugin
     * context path should be included in any layout-provided links.
     */
    void "Test that resource links are not rendered for a plugin view overridden by the application"() {
        when:"A plugin view overriden by the application that uses a plugin layout is rendered"
            go "dbUtil/testWithPluginLayout"
        then:"The view links don't include the plugin path but the layout links do"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/dbUtil.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/standard.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/plugins/db-util-0.3/css/oldstyle.css\"/>"
            contains "<link rel=\"stylesheet\" href=\"/kitchen_sink_app/css/other.css\"/>"
            contains "<script src=\"/kitchen_sink_app/js/prototype/prototype.js\" type=\"text/javascript\">"
            contains "<script src=\"/kitchen_sink_app/plugins/db-util-0.3/js/application.js\" type=\"text/javascript\">"

    }

    @Override
    String getApplication() {
        "kitchen_sink_app"
    }
}
