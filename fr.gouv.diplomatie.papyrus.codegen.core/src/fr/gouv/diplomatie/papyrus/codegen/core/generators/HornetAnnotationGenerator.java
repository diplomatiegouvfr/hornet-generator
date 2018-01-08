package fr.gouv.diplomatie.papyrus.codegen.core.generators;

import org.eclipse.uml2.uml.NamedElement;

public abstract class HornetAnnotationGenerator{

		public abstract String generateAnnotations(NamedElement elem);
		public abstract String generateImports(NamedElement elem);
	
}
