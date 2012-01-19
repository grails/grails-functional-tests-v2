package grails.functional.tests

import java.lang.annotation.*
import org.spockframework.runtime.extension.ExtensionAnnotation
import grails.functional.tests.internal.WebReportExtension

/**
 * Annotation used to indicate that a web response report should be dumped on failing tests
 */

@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@ExtensionAnnotation(WebReportExtension)
public @interface WebReport {

}