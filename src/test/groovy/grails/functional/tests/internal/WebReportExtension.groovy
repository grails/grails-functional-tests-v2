package grails.functional.tests.internal

import grails.functional.tests.WebReport
import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension
import org.spockframework.runtime.model.SpecInfo
import org.spockframework.runtime.AbstractRunListener
import org.spockframework.runtime.model.ErrorInfo
import com.sun.xml.internal.bind.v2.TODO
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation

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
        if(specEnabled) {
            
           spec.addInterceptor(new FailureReportListener(spec))
        }
    }

}
class FailureReportListener extends AbstractMethodInterceptor {
    SpecInfo spec

    FailureReportListener(SpecInfo spec) {
        this.spec = spec
    }

    @Override
    void interceptSetupMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptCleanupMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptSetupSpecMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptCleanupSpecMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) {
        println "FEATURE METHOD!"
        invocation.proceed()
    }

    @Override
    void interceptDataProviderMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptDataProcessorMethod(IMethodInvocation invocation) {
        invocation.proceed()
    }


    @Override
    void interceptSpecExecution(IMethodInvocation invocation) {
        invocation.proceed()
    }

    @Override
    void interceptFeatureExecution(IMethodInvocation invocation) {
        try {
            invocation.proceed()
        } catch (e) {
            try {
                def content = invocation.target?.browser?.page?.downloadContent()
                println "########################################################################################################"
                println "                              Web Content"
                println "########################################################################################################"
                println content
                println "########################################################################################################"
            } catch (e2) {
                // ignore
            }
            throw e
        }

    }

}
