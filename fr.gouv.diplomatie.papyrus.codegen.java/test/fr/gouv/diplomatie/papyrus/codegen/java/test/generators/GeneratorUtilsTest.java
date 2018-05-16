package fr.gouv.diplomatie.papyrus.codegen.java.test.generators;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.generators.GeneratorUtils;

public class GeneratorUtilsTest {

	@Test
	public void testClass() {
		GeneratorUtils gene = new GeneratorUtils();
		assertEquals(GeneratorUtils.class, gene.getClass());
	}
	
	@Test
	public void testGetEntityPath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src/main/java/fr/gouv/diplomatie", GeneratorUtils.getEntityPath(class_, true));
		assertEquals("src-gen/main/java/fr/gouv/diplomatie", GeneratorUtils.getEntityPath(class_, false));
	}

	@Test
	public void testGetEntityPackage() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("fr/gouv/diplomatie", GeneratorUtils.getEntityPackage(class_));
	}

}
