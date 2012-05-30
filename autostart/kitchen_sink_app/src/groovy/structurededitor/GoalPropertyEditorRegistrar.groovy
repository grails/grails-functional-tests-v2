package structurededitor

import org.springframework.beans.PropertyEditorRegistrar
import org.springframework.beans.PropertyEditorRegistry

/**
 * Sample PropertyEditorRegistrar for Goal Type
 *
 * @author Carsten Block
 * @version 1.0 - 05.04.12
 */
class GoalPropertyEditorRegistrar implements PropertyEditorRegistrar {
  void registerCustomEditors(PropertyEditorRegistry propertyEditorRegistry) {
    propertyEditorRegistry.registerCustomEditor(Goal, new GoalPropertyEditor())
  }
}
