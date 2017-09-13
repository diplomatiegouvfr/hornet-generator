package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import org.eclipse.uml2.uml.AssociationClass;

@SuppressWarnings("all")
public class AssociationClassUtils {
  /**
   * retourne le nom de la classe model
   */
  public static String getModelName(final AssociationClass clazz) {
    String _name = clazz.getName();
    return (_name + "Model");
  }
  
  /**
   * retourne le nom de la classe model
   */
  public static String getDtoName(final AssociationClass clazz) {
    String _name = clazz.getName();
    return (_name + "Dto");
  }
}
