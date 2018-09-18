package fr.gouv.diplomatie.papyrus.codegen.core.test.generators;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;

public class GeneratorUtilsTest {

	@Test
	public void testClass() {
		GeneratorUtils gene = new GeneratorUtils();
		assertEquals(GeneratorUtils.class, gene.getClass());
	}
	
	@Test
	public void testGetModelPath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src-gen/models/models/test-model", GeneratorUtils.getModelPath(class_, true));
	}

	@Test
	public void testGetAttributesInterfacePath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src-gen/models/attributes/test-attributes", GeneratorUtils.getAttributesInterfacePath(class_, true));
	}

	@Test
	public void testGetMetierClassPath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src-gen/models/metier/test-metier", GeneratorUtils.getMetierClassPath(class_, true));
	}

	@Test
	public void testGetDtoClassPath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src-gen/models/dto/test-dto", GeneratorUtils.getDtoClassPath(class_, true));
	}

	@Test
	public void testGetDatabaseScriptPath() {
		Package pkg = TestUtils.getpackage("model", "pkg");
		assertEquals("src-gen/database/createTablesPostgres", GeneratorUtils.getDatabaseScriptPath(pkg));	}

	@Test
	public void testGetModelDaoPath() {
		Package pkg = TestUtils.getpackage("model", "pkg");
		assertEquals("src-gen/dao/model-dao", GeneratorUtils.getModelDaoPath(pkg, true));
	}

	@Test
	public void testGetEnumPath() {
		Classifier class_ = TestUtils.getClass("model", "pkg", "test", false);
		assertEquals("src-gen/models/test-enum", GeneratorUtils.getEnumPath(class_, true));
	}

}
