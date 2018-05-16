package fr.gouv.diplomatie.papyrus.codegen.sql.test.generators;

import static org.junit.Assert.*;

import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.sql.generators.PackageGenerator;

public class PackageGeneratorTest {

	@Test
	public void testClass() {
		PackageGenerator gene = new PackageGenerator();
		assertEquals(PackageGenerator.class, gene.getClass());
	}

}
