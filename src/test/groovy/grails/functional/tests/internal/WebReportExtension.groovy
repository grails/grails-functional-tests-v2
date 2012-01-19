package grails.functional.tests.internal

import grails.functional.tests.WebReport
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import com.sun.xml.internal.bind.v2.TODO

/**
 * The web report extension guts
 */
class WebReportExtension extends AbstractAnnotationDrivenExtension<WebReport>{

    boolean specEnabled
    @Override
    void visitSpecAnnotation(WebReport annotation, SpecInfo spec) {
        specEnabled = true
    }

    @Override
    void visitSpec(SpecInfo spec) {
        if(specEnabled)
            spec.addListener(new FailureReportListener(spec))
    }

    class FailureReportListener extends AbstractRunListener {
        SpecInfo spec

        FailureReportListener(SpecInfo spec) {
            this.spec = spec
        }

        @Override
        void error(ErrorInfo error) {
            // TODO: figure out how to dump the error from the web response here, problematic because we need a reference to the sepc instance
        }


    }
}
