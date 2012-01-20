package grails.functional.tests.plugins

import grails.functional.tests.BaseApplicationSpec

/**
 * Tests for plugin view rendering
 */
class PluginViewsSpec extends BaseApplicationSpec {

    void "Test plugin view renders correctly"() {
        when:"A request for a plugin that renders a view is executed"
            go "/test-plugins/dbUtil/data"

        then:"The correct content is returned"
            title == "Database Dump"
            contains "<h1>Display Data</h1>"
            $("a", text:'Database Info') != null
            $("a", text: 'Display Data') != null
            $("a", text:'Execute SQL') != null
    }

    void "Test resource load correctly"() {
        when:
            go "/test-plugins/home/index"
        then:
            contains 'OK'
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags include
     * the plugin context path in plugin views.
     */
    void "Test that resource tags include the plugin context path in resource links"() {
        when:"A plugin view is requested"
            go "/test-plugins/dbUtil/data"

        then:"The correct resource links are produced"
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/dbUtil.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/standard.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/standard.css')
            $('script', type:'text/javascript', src:'/test-plugins/plugins/db-util-0.3/js/dojo.js')
            $('script', type:'text/javascript', src:'/test-plugins/plugins/db-util-0.3/js/application.js')
            $('#pluginContext').text() == '/plugins/db-util-0.3'
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path for a plugin view that has been overridden
     * by the application. The customised view uses an application layout.
     */
    void "Test that no plugin context path is present if a plugin view is overriden by the application"() {
        when:"An application view that overrides a plugin view is requested"
            go "/test-plugins/dbUtil/sql"
        then:"The correct resource links are produced"
            $('link', rel:'stylesheet', href:'/test-plugins/css/main.css')
            $('link', rel:'stylesheet', href:'/test-plugins/css/other.css')
            $('script', type:'text/javascript', src:'/test-plugins/js/app-layout.js')
            $('script', type:'text/javascript', src:'/test-plugins/js/app.js')
            $('script', type:'text/javascript', src:'/test-plugins/js/application.js')

    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path when they are in an application layout
     * that is used by a plugin view. The tags in the plugin view should
     * include the plugin context path.
     */
    void "Test that an application layout used by a plugin view does not include plugin context page in links"() {
        when:"A plugin view with an application layout is requested"
            go "/test-plugins/dbUtil/info"
        then:"Then resource links in the layout don't include the plugin path whilst resource links in the plugin view do"
            $('link', rel:'stylesheet', href:'/test-plugins/css/main.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/other.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/other-2.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/other-3.css')
            $('script', type:'text/javascript', src:'/test-plugins/js/app-layout.js')
            $('script', type:'text/javascript', src:'/test-plugins/js/application.js')
            $('script', type:'text/javascript', src:'/test-plugins/plugins/db-util-0.3/js/plugin-info.js')
    }

    /**
     * Tests that the &lt;g:resource> and &lt;g:javascript> tags exclude
     * the plugin context path for a plugin view that has been overridden
     * by the application. The view uses a plugin layout, so the plugin
     * context path should be included in any layout-provided links.
     */
    void "Test that resource links are not rendered for a plugin view overridden by the application"() {
        when:"A plugin view overriden by the application that uses a plugin layout is rendered"
            go "/test-plugins/dbUtil/testWithPluginLayout"
        then:"The view links don't include the plugin path but the layout links do"
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/dbUtil.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/standard.css')
            $('link', rel:'stylesheet', href:'/test-plugins/plugins/db-util-0.3/css/oldstyle.css')
            $('link', rel:'stylesheet', href:'/test-plugins/css/other.css')
            $('script', type:'text/javascript', src:'/test-plugins/js/prototype/prototype.js')
            $('script', type:'text/javascript', src:'/test-plugins/plugins/db-util-0.3/js/application.js')


    }

    @Override
    String getApplication() {
        "test-plugins"
    }
}
