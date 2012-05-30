package structurededitor

import java.beans.PropertyEditorSupport
import org.codehaus.groovy.grails.web.binding.StructuredPropertyEditor

/**
 * Sample property editor for goal class
 *
 * @author Carsten Block
 * @version 1.0 - 05.04.12
 */
class GoalPropertyEditor extends PropertyEditorSupport implements StructuredPropertyEditor{
  List getRequiredFields() {
    return ['own', 'other']
  }

  List getOptionalFields() {
    return []
  }

  Object assemble(Class type, Map fieldValues) {
    if (!fieldValues.containsKey("own")) throw new IllegalArgumentException("Can`t populate gaol property without an [own] field");
        if (!fieldValues.containsKey("other")) throw new IllegalArgumentException("Can`t populate goal property without an [other] field");
    return new Goal(own: fieldValues.get('own') as Integer, other: fieldValues.get('other') as Integer)
  }
}
