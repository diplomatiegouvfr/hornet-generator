package fr.gouv.diplomatie.papyrus.codegen.core.utils;

import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Type;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class ModelUtils {
  /**
   * retourne les classes du modele
   */
  public static Iterable<Type> getAllClasses(final Model model) {
    final Function1<Type, Boolean> _function = (Type type) -> {
      return Boolean.valueOf((type instanceof org.eclipse.uml2.uml.Class));
    };
    final Iterable<Type> classes = IterableExtensions.<Type>filter(model.getOwnedTypes(), _function);
    return classes;
  }
}
