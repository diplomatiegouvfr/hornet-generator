package fr.gouv.diplomatie.papyrus.codegen.java.test.xtend.classifier;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.test.testutils.HornetModelJava;
import fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier.ClassifierJPAEntityGenerator;

public class ClassifierJPAEntityGeneratorTest {

	@Test
	public void testClass() {
		ClassifierJPAEntityGenerator gene = new ClassifierJPAEntityGenerator();
		assertEquals(ClassifierJPAEntityGenerator.class, gene.getClass());
	}
	
	@Test
	public void testValueObjectGenerateTypeAnnotationClassifier() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Classifier class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.valueObject);
		
		String expect = "@Embeddable";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateTypeAnnotation(class_).toString());
	}
	
	@Test
	public void testGenerateTypeAnnotationClassifier() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Classifier class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		String expect = "@Entity\n" + 
				"@Table(name = \"ma_classe\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateTypeAnnotation(class_).toString());
		
		expect = "@Entity\n" + 
				"@Table(name = \"ma_classe\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateTypeAnnotation(class_).toString());
	}
	
	@Test
	public void testGenerateInheritAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		Interface inter = hmodel.pckage.createOwnedInterface("interf");
		class_.createInterfaceRealization("real", inter);
		assertEquals("@Inheritance\n", ClassifierJPAEntityGenerator.generateInheritAnnotation(class_).toString());
	}

	@Test
	public void testNoInterfaceGenerateInheritAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Classifier class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals(null, ClassifierJPAEntityGenerator.generateInheritAnnotation(class_));
	}

	@Test
	public void testGenerateImport() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Classifier class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		assertEquals("import fr.gouv.diplomatie.maClasse;", ClassifierJPAEntityGenerator.generateImport(class_).toString());
	}

	@Test
	public void testEntityGenerateAssociationAttributes() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att1", "att2", "asso");
		
		asso.applyStereotype(hmodel.associationTable);
		TestUtils.setStereotypePropertyValue(asso, hmodel.associationTable, hmodel.associationTableFetchType, hmodel.fetchTypeEager);
		
		String expect = "\n@ManyToMany(fetch=FetchType.EAGER)\n" + 
				"@JoinTable(name=\"asso\", joinColumns=@JoinColumn(name=\"id_att2\", referencedColumnName=\"id\"),\n" + 
				"	inverseJoinColumns=@JoinColumn(name=\"id_att1\", referencedColumnName=\"id\"))\n" + 
				"public Set<maClasse2> att1;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAttributes(asso, class_).toString());
	}
	
	@Test
	public void testValueObjectGenerateAssociationAttributes() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		class2_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "att", hmodel.integerPT, 0, 1);

		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att1", "att2", "asso");
		
		String expect = "\n@ElementCollection\n" + 
				"@CollectionTable(name = \"ma_classe_asso\")\n" + 
				"public Set<maClasse2> asso;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAttributes(asso, class_).toString());
	}
	
	@Test
	public void testAnyGenerateAssociationAttributes() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "att", hmodel.integerPT, 0, 1);

		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "att1", "att2", "asso");
		
		String expect = "\n@ElementCollection\n" + 
				"@CollectionTable(name = \"ma_classe_asso\", joinColumns = @JoinColumn(name = \"id\"))\n" + 
				"public Set<maClasse2> asso;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAttributes(asso, class_).toString());
	}

	@Test
	public void testGenerateBasicAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		String expect = "\n@Column(name = \"test\")\n" + 
				"public String test;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateBasicAttribute(prop, "", class_).toString());
	}
	
	@Test
	public void testMultivaluedGenerateBasicAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		String expect = "\n@ElementCollection\n" + 
				"public Set<String> test;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateBasicAttribute(prop, "", class_).toString());
	}
	
	@Test
	public void testEntityGenerateBasicAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		String expect = "\n@ManyToOne\n" + 
				"@JoinColumn(name = \"id_test\")\n" + 
				"public maClasse2 test;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateBasicAttribute(prop, "", class_).toString());
	}
	
	@Test
	public void testMultivaluedEntityGenerateBasicAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		prop.applyStereotype(hmodel.attribute);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeFetchType, hmodel.fetchTypeLazy);
		String expect = "\n@ManyToMany(fetch=FetchType.LAZY)\n" + 
				"@JoinTable(name=\"ma_classe_test\", joinColumns=@JoinColumn(name=\"id_ma_classe\", referencedColumnName=\"id\"),\n" + 
				"	inverseJoinColumns=@JoinColumn(name=\"id_test\", referencedColumnName=\"id\"))\n" + 
				"public Set<maClasse2> test;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateBasicAttribute(prop, "", class_).toString());
	}

	@Test
	public void testGenerateSequence() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		id.applyStereotype(hmodel.sequence);
		
		String expect = "//@SequenceGenerator(name=\"ma_classe_id_generator\", initialValue=0, allocationSize=0, sequenceName=\"ma_classe_id_sequence\")\n" + 
				"//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator=\"ma_classe_id_generator\")\n" + 
				"@GeneratedValue(strategy=GenerationType.IDENTITY)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateSequence(id, "id", class_).toString());
	}

	@Test
	public void testOneToOneGenerateAssociationAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		Property prop2  = class2_.getAttribute("test2", class_);
		asso.getOwnedEnds().add(prop);
		asso.getOwnedEnds().add(prop2);
		prop.setAssociation(asso);
		String expect = "@OneToOne(cascade = {CascadeType.REMOVE})\n" + 
				"@JoinColumn(name = \"id_test2\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAnnotation(prop2, class_).toString());
	}
	
	@Test
	public void testManyToOneGenerateAssociationAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property prop = class_.getAttribute("test", class2_);
		Property prop2  = class2_.getAttribute("test2", class_);
		asso.getOwnedEnds().add(prop2);
		asso.getOwnedEnds().add(prop);
		prop.setAssociation(asso);
		String expect = "@ManyToOne\n" + 
				"@JoinColumn(name = \"id_test\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAnnotation(prop, class_).toString());
	}
	
	@Test
	public void testOneToManyGenerateAssociationAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		Property prop2  = class2_.getAttribute("test2", class_);
		asso.getOwnedEnds().add(prop2);
		asso.getOwnedEnds().add(prop);
		prop.setAssociation(asso);
		String expect = "@OneToMany\n" + 
				"@JoinTable(name=\"ma_classe_test\", joinColumns=@JoinColumn(name=\"id_ma_classe\", referencedColumnName=\"id\"),\n" + 
				"	inverseJoinColumns=@JoinColumn(name=\"id_test\", referencedColumnName=\"id\", unique=true))\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAnnotation(prop, class_).toString());
	}
	
	@Test
	public void testManyToManyGenerateAssociationAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property id2 = TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1);
		id2.applyStereotype(hmodel.keyAttribute);
		
		Association asso = class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, -1);
		Property prop = class_.getAttribute("test", class2_);
		Property prop2  = class2_.getAttribute("test2", class_);
		asso.getOwnedEnds().add(prop2);
		asso.getOwnedEnds().add(prop);
		prop.setAssociation(asso);
		String expect = "@ManyToMany\n" + 
				"@JoinTable(name=\"ma_classe_test\", joinColumns=@JoinColumn(name=\"id_ma_classe\", referencedColumnName=\"id\"),\n" + 
				"	inverseJoinColumns=@JoinColumn(name=\"id_test\", referencedColumnName=\"id\"))\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationAnnotation(prop, class_).toString());
	}

	@Test
	public void testGenerateVOAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);
		String expect = "\n@Embedded\n" + 
				"@AttributeOverrides({\n" + 
				"	@AttributeOverride(name=\"att\",column = @Column(name = \"prop_att\"))\n" + 
				"})\n" + 
				"@AssociationOverrides({\n" + 
				"})\n" + 
				"public maClasse2 prop;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateVOAttribute(prop, "", "", class_).toString());
	}
	
	@Test
	public void testMultivaluedGenerateVOAttribute() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, -1);
		String expect = "\n@ElementCollection\n" + 
				"@CollectionTable(name = \"ma_classe_prop\", joinColumns = @JoinColumn(name = \"id\"))\n" + 
				"public Set<maClasse2> prop;\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateVOAttribute(prop, "", "", class_).toString());
	}

	@Test
	public void testAlwaysFalseGenerateBooleanTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.booleanTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysFalse, true);
		assertEquals("@AssertFalse\n", ClassifierJPAEntityGenerator.generateBooleanTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testAlwaysTrueGenerateBooleanTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.booleanTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.booleanTyped, hmodel.booleanTypedAlwaysTrue, true);
		assertEquals("@AssertTrue\n", ClassifierJPAEntityGenerator.generateBooleanTypedAnnotation(prop).toString());
	}

	@Test
	public void testPastGenerateDateTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedPast, true);
		assertEquals("@Past\n", ClassifierJPAEntityGenerator.generateDateTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testPastOrPresentGenerateDateTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedPastOrPresent, true);
		assertEquals("@PastOrPresent\n", ClassifierJPAEntityGenerator.generateDateTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testFutureGenerateDateTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedFuture, true);
		assertEquals("@Future\n", ClassifierJPAEntityGenerator.generateDateTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testFutureOrPresentGenerateDateTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.booleanPT, 0, 1);
		prop.applyStereotype(hmodel.dateTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.dateTyped, hmodel.dateTypedFutureOrPresent, true);
		assertEquals("@FutureOrPresent\n", ClassifierJPAEntityGenerator.generateDateTypedAnnotation(prop).toString());
	}

	@Test
	public void testEmptyGeneratStringTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedCanBeEmpty, true);
		assertEquals("", ClassifierJPAEntityGenerator.generatStringTypedAnnotation(prop).toString());
		
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedCanBeEmpty, false);
		assertEquals("@NotEmpty\n", ClassifierJPAEntityGenerator.generatStringTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testPatternGeneratStringTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedPattern, "test");
		String expect = "@NotEmpty\n" + 
				"@Pattern(regexp = \"test\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generatStringTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testSizeMinGeneratStringTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedHasSizeMin, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.stringTyped, hmodel.stringTypedSizeMin, 2);
		
		String expect = "@NotEmpty\n" + 
				"@Size( min = 2)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generatStringTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testLengthGeneratStringTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		prop.applyStereotype(hmodel.stringTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeLength, 3);
		TestUtils.setStereotypePropertyValue(prop, hmodel.attribute, hmodel.attributeHasLength, true);
		
		String expect = "@NotEmpty\n" + 
				"@Size( max = 3)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generatStringTypedAnnotation(prop).toString());
	}

	@Test
	public void testMinGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedMin, 3);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMin, true);
		String expect = "@Min(3)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testMaxGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedMax, 3);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasMax, true);
		String expect = "@Max(3)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testNegativeGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedNegative, true);
		String expect = "@Negative\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testNegativeOrZeroGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedNegativeOrZero, true);
		String expect = "@NegativeOrZero\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testPositiveGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedPositive, true);
		String expect = "@Positive\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testPositiveOrZeroGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedPositiveOrZero, true);
		String expect = "@PositiveOrZero\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testDigitsGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigits, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigitsInteger, 2);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDigitsFraction, 3);

		String expect = "@Digits(integer = 2, fraction = 3)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}
	
	@Test
	public void testDecimalGenerateNumericTypedAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, 1);
		prop.applyStereotype(hmodel.numericTyped);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDecimalMax, 4);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMax, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedDecimalMin, 2);
		TestUtils.setStereotypePropertyValue(prop, hmodel.numericTyped, hmodel.numericTypedHasDecimalMin, true);

		String expect = "@DecimalMin(\"2\")\n" + 
				"@DecimalMax(\"4\")\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateNumericTypedAnnotation(prop).toString());
	}

	@Test
	public void testGenerateCollectionAnnotation() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.integerPT, 0, -1);
		prop.applyStereotype(hmodel.collection);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionSizeMax, 4);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionSizeMin, 2);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMax, true);
		TestUtils.setStereotypePropertyValue(prop, hmodel.collection, hmodel.collectionHasSizeMin, true);

		String expect = "@Size( min = 2, max = 4)\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateCollectionAnnotation(prop).toString());
	}

	@Test
	public void testGenerateVOOverrides() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		Class class3_  = TestUtils.createClass(hmodel.pckage, "maClasse3", false);
		class3_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class3_, "id", hmodel.stringPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class2_, "att2", class3_, 0, 1);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);

		String expect = "@AttributeOverrides({\n" + 
				"	@AttributeOverride(name=\"att\",column = @Column(name = \"prop_att\"))\n" + 
				"})\n" + 
				"@AssociationOverrides({\n" + 
				"	@AssociationOverride(name=\"att2\",joinColumns = @JoinColumn(name = \"prop_id_att2\"))\n" + 
				"})";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateVOOverrides(prop, "prop").toString());
	}
	
	@Test
	public void testGenerateAttributeOverride() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "prop", hmodel.stringPT, 0, 1);
		prop.applyStereotype(hmodel.attribute);
		String expect = "@AttributeOverride(name=\"prop\",column = @Column(name = \"prop_prop\"))";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAttributeOverride(prop, "prop").toString());
	}

	@Test
	public void testEntityGenerateAttributeOverride() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);

		String expect = "";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAttributeOverride(prop, "prop").toString());
	}

	@Test
	public void testGenerateAssociationOverride() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class2_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "prop", class2_, 0, 1);

		String expect = "@AssociationOverride(name=\"prop\",joinColumns = @JoinColumn(name = \"prop_id_prop\"))";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateAssociationOverride(prop, "prop").toString());
	}

	@Test
	public void testGenerateCompareTo() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);
		
		assertEquals("", ClassifierJPAEntityGenerator.generateCompareTo(class_).toString());
		
		prop.applyStereotype(hmodel.naturalOrder);
		String expect = "\n@Override\n" + 
				"public int compareTo(maClasse other) {\n" + 
				"	return this.id.compareTo(other.id);\n" + 
				"}\n";
		assertEquals(expect, ClassifierJPAEntityGenerator.generateCompareTo(class_).toString());
	}

	@Test
	public void testGenerateExtends() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		assertEquals("", ClassifierJPAEntityGenerator.generateExtends(class_).toString());
		
		TestUtils.createGeneralization(class_, class2_);
		assertEquals("extends maClasse2", ClassifierJPAEntityGenerator.generateExtends(class_).toString());
	}

	@Test
	public void testGenerateImplements() {
		HornetModelJava hmodel = HornetModelJava.initModel();
		Class class_  = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Class class2_  = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		assertEquals("implements Serializable", ClassifierJPAEntityGenerator.generateImplements(class_).toString());
		
		Property prop = TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1);

		TestUtils.createGeneralization(class_, class2_);
		prop.applyStereotype(hmodel.naturalOrder);
		assertEquals("implements  Comparable<maClasse>", ClassifierJPAEntityGenerator.generateImplements(class_).toString());
	}

}
