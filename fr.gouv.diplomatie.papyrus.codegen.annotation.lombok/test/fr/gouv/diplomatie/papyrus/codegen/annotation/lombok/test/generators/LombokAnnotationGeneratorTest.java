package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.generators;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.generators.LombokAnnotationGenerator;
import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.testutils.HornetModelLombok;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;

public class LombokAnnotationGeneratorTest {

	@Test
	public void testClass() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		assertEquals(LombokAnnotationGenerator.class, gene.getClass());
	}
	
	@Test
	public void testNoEntityGenerateAnnotations() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		
		assertEquals(null, gene.generateAnnotations(class_));
	}
	
	@Test
	public void testGenerateAnnotations() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		class_.applyStereotype(hmodel.entity);
		
		assertNotNull(gene.generateAnnotations(class_));
	}
	
	@Test
	public void testNoEntityGenerateImports() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		
		assertEquals(null, gene.generateImports(class_));
	}

	@Test
	public void testGenerateImports() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		class_.applyStereotype(hmodel.entity);
		
		assertNotNull(gene.generateImports(class_));
	}

	@Test
	public void testGenerateEntityImports() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		class_.applyStereotype(hmodel.entity);
		
		assertNotNull(gene.generateEntityImports(class_));
	}

	@Test
	public void testGenerateEntityAnnotations() {
		LombokAnnotationGenerator gene = new LombokAnnotationGenerator();
		HornetModelLombok hmodel = new HornetModelLombok();
		Classifier class_ = TestUtils.createClass(hmodel.pckage, "test", false);
		class_.applyStereotype(hmodel.entity);
		
		assertNotNull(gene.generateEntityAnnotations(class_));
	}

}
