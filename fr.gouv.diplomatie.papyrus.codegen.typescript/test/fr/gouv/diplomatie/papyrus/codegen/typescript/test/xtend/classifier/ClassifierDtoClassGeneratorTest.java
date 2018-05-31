package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.classifier;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.AssociationClass;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.test.testutils.HornetModelTypescript;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.classifier.ClassifierDtoClassGenerator;

public class ClassifierDtoClassGeneratorTest {

	@Test
	public void testGenerateManyToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\n@Map(maClasse2DTO)\n" + 
				"test: Array<maClasse2DTO>;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateManyToManyAttribute(prop, class_).toString());
	}
	
	@Test
	public void testGenerateManyToManyAttributesPropertyClassifier() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		class_.createAssociation(true, AggregationKind.NONE_LITERAL	, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		
		String expect = ClassifierDtoClassGenerator.generateManyToManyAttribute(prop, class_).toString();
		assertEquals(expect, ClassifierDtoClassGenerator.generateManyToManyAttributes(prop, class_).toString());
	}

	@Test
	public void testGenerateOneToManyAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\n@Map(maClasseDTO)\n" + 
				"maClasseTest: maClasseDTO;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateOneToManyAttribute(prop, class_).toString());
		
	}


	@Test
	public void testGenerateImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String path = ClassifierUtils.getDtoClassPath(class2_);
		String expect = "import { maClasse2DTO } from \""+ path + "\";\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateImports(class_).toString());
	}

	@Test
	public void testGenerateExtendsImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		TestUtils.createGeneralization(class_, class2_);
		
		String path = ClassifierUtils.getDtoClassPath(class2_);
		String expect = "import { maClasse2DTO } from \""+ path + "\";\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateExtendsImports(class_).toString());
	}

	@Test
	public void testGenerateAttributesImports() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		ArrayList<Type> expect = new ArrayList<Type>();
		expect.add(class2_);
		
		ArrayList<Type> list = new ArrayList<Type>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateAttributesImports(class_, list));
	}

	@Test
	public void testGenerateExtends() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();	
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "extends maClasse2DTO";
		assertEquals(expect, ClassifierDtoClassGenerator.generateExtends(class_).toString());
	}

	@Test
	public void testGenerateExtendsAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		TestUtils.createGeneralization(class_, class2_);
		
		String expect = "\n@Map(maClasse2DTO)\nmaClasse2: maClasse2DTO;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateExtendsAttributes(class_).toString());
	}
	
	@Test
	public void testGenerateAssociationClassAtributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect = "\n@Map(assoDTO)\nasso: Array<assoDTO>;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateAssociationClassAtributes(asso, class_).toString());
	}

	@Test
	public void testGenerateAssociationAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		AssociationClass asso = TestUtils.createAssociationClass(class_, class2_, "test", "test2", "asso");
		
		String expect = ClassifierDtoClassGenerator.generateAssociationClassAtributes(asso, class_).toString();
		assertEquals(expect, ClassifierDtoClassGenerator.generateAssociationAttributes(class_).toString());
	}
	
	@Test
	public void testGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, 1);
		
		String expect = "\n@Map()\ntest: string;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateBasicAttribute(prop, list).toString());
		
		expect = "\n@Map()\n@Alias('add.other.test', 'addOtherTest')\naddOtherTest: string;\n";
		ArrayList<String> list2 =new ArrayList<String>();
		list2.add("add");
		list2.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateBasicAttribute(prop, list2).toString());
	}
	
	@Test
	public void testMultivaluedGenerateBasicAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		
		String expect = "";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateBasicAttribute(prop, list).toString());
	}

	@Test
	public void testGenerateInterfaceAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Interface class2_ = TestUtils.createInterface("inter");
		Property prop = class2_.createOwnedAttribute("test", hmodel.stringPT, 0, 1);
		class_.createInterfaceRealization("test", class2_);
		
		ArrayList<String> list =new ArrayList<String>();
		String expect = ClassifierDtoClassGenerator.generateAttribut(prop, list, class_).toString();
		assertEquals(expect, ClassifierDtoClassGenerator.generateInterfaceAttributes(class_).toString());
		
	}

	@Test
	public void testGenerateAttributesClassifierClassifier() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class2_, "test", hmodel.stringPT, 0, 1);
		
		ArrayList<String> list =new ArrayList<String>();
		String expect = ClassifierDtoClassGenerator.generateAttribut(prop, list, class_).toString();
		assertEquals(expect, ClassifierDtoClassGenerator.generateAttributes(class2_, class_).toString());
	}

	@Test
	public void testGenerateAttributesClassifierArrayListOfStringClassifier() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
				
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		Property prop = TestUtils.createAttribute(class2_, "test", hmodel.stringPT, 0, 1);
		
		ArrayList<String> list =new ArrayList<String>();
		list.add("test");
		String expect = ClassifierDtoClassGenerator.generateAttribut(prop, list, class_).toString();
		assertEquals(expect, ClassifierDtoClassGenerator.generateAttributes(class2_, list, class_).toString());
	}

	@Test
	public void testGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		ArrayList<String> list =new ArrayList<String>();
		list.add("test");
		String expect = ClassifierDtoClassGenerator.generateAttributes(class2_, list, class_).toString();
		
		ArrayList<String> list2 =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateValueObjectAttribute(prop, list2, class_).toString());
	}
	
	@Test
	public void testMultivaluedGenerateValueObjectAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		TestUtils.createAttribute(class2_, "att", hmodel.stringPT, 0, 1);
		
		String expect = "\n@Map()\ntest: Array<MaClasseTestDTO>;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateValueObjectAttribute(prop, list, class_).toString());
	}

	@Test
	public void testGenerateEntityAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		String expect = "\n@Map(maClasse2DTO)\ntest: maClasse2DTO;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttributes(prop, list).toString());
		
		expect = "\n@Map(maClasse2DTO)\n@Alias('addOtherTest', 'add.other.test')\naddOtherTest: maClasse2DTO;\n";
		ArrayList<String> list2 =new ArrayList<String>();
		list2.add("add");
		list2.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttributes(prop, list2).toString());
	}
	
	@Test
	public void testMultivaluedGenerateEntityAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		String expect = "\n@Map(maClasse2DTO)\ntest: Array<maClasse2DTO>;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttributes(prop, list).toString());
		
		expect = "\n@Map(maClasse2DTO)\n@Alias('addOtherTest', 'add.other.test')\naddOtherTest: Array<maClasse2DTO>;\n";
		ArrayList<String> list2 =new ArrayList<String>();
		list2.add("add");
		list2.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttributes(prop, list2).toString());
	}

	@Test
	public void testGenerateEnumAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		
		String expect = "\n@Map()\n" + 
				"testCode: number;\n" + 
				"\n" + 
				"@Map(maClasse2DTO)\n" + 
				"test: maClasse2DTO;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEnumAttributes(prop, list).toString());
		
		expect = "\n@Map()\n@Alias('add.other.test.code')\n" + 
				"addOtherTestCode: number;\n" + 
				"\n" + 
				"@Map(maClasse2DTO)\n@Alias('addOtherTest', 'add.other.test')\n" + 
				"addOtherTest: maClasse2DTO;\n";
		ArrayList<String> list2 =new ArrayList<String>();
		list2.add("add");
		list2.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateEnumAttributes(prop, list2).toString());
	}
	
	@Test
	public void testMultivaluedGenerateEnumAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "@Map()\n" + 
				"testCode: Array<number>;\n" + 
				"\n" + 
				"@Map(maClasse2DTO)\n" + 
				"test: Array<maClasse2DTO>;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEnumAttributes(prop, list).toString());
		
		expect = "@Map()\n@Alias('add.other.test.code')\n" + 
				"addOtherTestCode: Array<number>;\n" + 
				"\n" + 
				"@Map(maClasse2DTO)\n@Alias('addOtherTest', 'add.other.test')\n" + 
				"addOtherTest: Array<maClasse2DTO>;\n";
		ArrayList<String> list2 =new ArrayList<String>();
		list2.add("add");
		list2.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateEnumAttributes(prop, list2).toString());
	}

	@Test
	public void testGenerateEntityAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect="\n@Map()\n" + 
				"@Alias('idTest', 'test.id')\n" + 
				"idTest: string;\n";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttribute(prop, id, list).toString());
		
		expect="\n@Map()\n" + 
				"@Alias('addOtherIdTest', 'add.other.test.id')\n" + 
				"addOtherIdTest: string;\n";
		list =new ArrayList<String>();
		list.add("add");
		list.add("other");
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttribute(prop, id, list).toString());
	}
	
	@Test
	public void testMultivaluedGenerateEntityAttribute() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		Property id = TestUtils.createAttribute(class2_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect="";
		ArrayList<String> list =new ArrayList<String>();
		assertEquals(expect, ClassifierDtoClassGenerator.generateEntityAttribute(prop, id, list).toString());
	
	}

	@Test
	public void testGenerateMultiValuedPrimitiveTypeDto() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nexport class MaClasseTestDTO {\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	test: string;\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	id: string;\n" + 
				"	\n" + 
				"	@Map(maClasseDTO)\n" + 
				"	maClasse: maClasseDTO;\n" + 
				"}\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateMultiValuedPrimitiveTypeDto(prop, class_).toString());

	}

	@Test
	public void testGenerateMultiValuedPrimitiveTypeDtoIdAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		
		String expect ="\n@Map()\nid: string;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateMultiValuedPrimitiveTypeDtoIdAttributes(prop, id).toString());
	}

	@Test
	public void testGenerateNPTypeAssociationDto() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, 1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		Property prop = class_.getAttribute("test", class2_);
		prop.getAssociation().createOwnedEnd("test", class_);
		prop.getAssociation().createOwnedEnd("test2", class2_);
		prop.getAssociation().getMemberEnds().add(prop);

		TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		
		String expect = "\nexport class MaClasseTestDTO{\n" + 
				"	\n" + 
				"	@Map(maClasseDTO)\n" + 
				"	maClasse: maClasseDTO;\n" + 
				"	\n" + 
				"	@Map(maClasse2DTO)\n" + 
				"	maClasse2: maClasse2DTO;\n" + 
				"}\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateNPTypeAssociationDto(prop, class_).toString());
	}

	@Test
	public void testGenerateNPTDtoIdAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property id = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		
		String expect = "\n@Map()\n" + 
				"id2MaClasse2: string;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateNPTDtoIdAttributes(prop, id).toString());
	}

	@Test
	public void testGenerateNPTypeDto() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.entity);
					
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		Property id2 = TestUtils.createAttribute(class2_, "id2", hmodel.stringPT, 0, 1);
		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		
		id.applyStereotype(hmodel.keyAttribute);
		id2.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\nexport class MaClasseTestDTO{\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	idMaClasse: string;\n" + 
				"	\n" + 
				"	@Map(maClasseDTO)\n" + 
				"	maClasse: maClasseDTO;\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	id2MaClasse2: string;\n" + 
				"	\n" + 
				"	@Map(maClasse2DTO)\n" + 
				"	maClasse2: maClasse2DTO;\n" + 
				"}\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateNPTypeDto(prop, class_).toString());
	}

	@Test
	public void testGenerateNPTModelIdAttributes() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);

		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		String expect = "\n@Map()\n" + 
				"idMaClasse: string;\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateNPTModelIdAttributes(id).toString());
	}

	@Test
	public void testGenerateMultiValuedValueObjectDto() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.valueObject);

		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0 , -1);
		
		TestUtils.createAttribute(class2_, "test2", hmodel.stringPT, 0 , 1);
		
		String expect = "\nexport class MaClasseTestDTO{\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	idMaClasse: string;\n" + 
				"	\n" + 
				"	@Map(maClasseDTO)\n" + 
				"	maClasse: maClasseDTO;\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	@Alias('test.test2', 'testTest2')\n" + 
				"	testTest2: string;\n" + 
				"	\n" + 
				"}\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateMultiValuedValueObjectDto(prop, class_).toString());
	}

	@Test
	public void testGenerateMultiValuedEnumDto() {
		HornetModelTypescript hmodel = HornetModelTypescript.initModel();
		
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.entity);

		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		class2_.applyStereotype(hmodel.nomenclature);

		Property id = TestUtils.createAttribute(class_, "id", hmodel.stringPT, 0, 1);
		id.applyStereotype(hmodel.keyAttribute);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0 , -1);
				
		String expect = "\nexport class MaClasseTestDTO{\n" + 
				"	\n" + 
				"	@Map()\n" + 
				"	idMaClasse: string;\n" + 
				"	\n" + 
				"	@Map(maClasseDTO)\n" + 
				"	maClasse: maClasseDTO;\n" + 
				"	\n" + 
				"	@Map(maClasse2DTO)\n" + 
				"	test: maClasse2DTO;\n" + 
				"}\n";
		assertEquals(expect, ClassifierDtoClassGenerator.generateMultiValuedEnumDto(prop, class_).toString());
	}

}
