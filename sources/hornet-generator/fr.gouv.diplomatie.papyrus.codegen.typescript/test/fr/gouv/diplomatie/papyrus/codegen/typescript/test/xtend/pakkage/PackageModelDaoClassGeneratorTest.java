package fr.gouv.diplomatie.papyrus.codegen.typescript.test.xtend.pakkage;

import static org.junit.Assert.*;

import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Property;
import org.junit.Test;

import fr.gouv.diplomatie.papyrus.codegen.core.generators.GeneratorUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.typescript.xtend.pakkage.PackageModelDaoClassGenerator;

public class PackageModelDaoClassGeneratorTest {

	@Test
	public void testGenerateModelImport() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String path = GeneratorUtils.getModelPath(class_, true);
		String expect = "import { maClasseModel } from \""+ path +"\";\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateModelImport(class_).toString());
	}

	@Test
	public void testGenerateAttributesImport() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String path = GeneratorUtils.getAttributesInterfacePath(class_, true);
		String expect = "import { maClasseAttributes } from \""+ path +"\";\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateAttributesImport(class_).toString());
	}

	@Test
	public void testGenerateAttributImport() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		Property prop = TestUtils.createAttribute(class_, "test", hmodel.stringPT, 0, -1);
		
		String path = GeneratorUtils.getModelPath(class_, true);
		String expect = "import { maClasseTestModel } from \""+ path +"\";\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateAttributImport(prop, class_).toString());
	}

	@Test
	public void testGenerateAssociationModelImport() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String path = GeneratorUtils.getModelPath(class_, true);
		String expect = "import { maClasseModel } from \""+ path +"\";\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateAssociationModelImport(class_).toString());
	}

	@Test
	public void testGenerateAssociationAttributesImport() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String path = GeneratorUtils.getAttributesInterfacePath(class_, true);
		String expect = "import { maClasseAttributes } from \""+ path +"\";\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateAssociationAttributesImport(class_).toString());
	}

	@Test
	public void testGenerateEntityDeclaration() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String expect = "\n@Entity(\"ma_classe\", maClasseModel)\n" + 
				"public maClasseEntity: HornetSequelizeInstanceModel<maClasseAttributes>;\n";
		assertEquals(expect, PackageModelDaoClassGenerator.generateEntityDeclaration(class_).toString());
	}

	@Test
	public void testGenerateMultivaluedAttributEntityDeclaration() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		String expect = "\n@Entity(\"ma_classe_test\", maClasseTestModel)\n" + 
				"public maClasseTestEntity: HornetSequelizeInstanceModel<any>;\n";
		assertEquals(expect, PackageModelDaoClassGenerator.generateMultivaluedAttributEntityDeclaration(prop, class_).toString());
	}

	@Test
	public void testGenerateAssociationClassDeclaration() {
		/*HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String expect = "\n@Entity(\"MA_CLASSE\", maClasseModel)\n" + 
				"public maClasseEntity: HornetSequelizeInstanceModel<maClasseAttributes>;\n";
		assertEquals(expect, PackageModelDaoClassGenerator.generateAssociationClassDeclaration(class_).toString());*/
	}

	@Test
	public void testGenerateEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String expect = "\nprivate initMaClasseEntity(): void {\n" + 
				"}\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateOneToManyRelation() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		
		class_.createAssociation(true, AggregationKind.NONE_LITERAL, "test", 0, -1, class2_, true, AggregationKind.NONE_LITERAL, "test2", 0, 1);
		
		Property prop = class2_.getAttribute("test2", class_);
		String expect = "SequelizeUtils.initRelationBelongsTo({\n    fromEntity: this.maClasseEntity,\n    toEntity: this.maClasseEntity,\n    alias: \"test2\",\n    foreignKey: \"id_test2\"});\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateOneToManyRelation(prop, class_).toString());
	}

	@Test
	public void testGenerateCallEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String expect = "this.initMaClasseEntity();\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateCallEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateEntityRelation() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "maClasse2", false);
		
		TestUtils.createAttribute(class_, "id", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);
		TestUtils.createAttribute(class2_, "id2", hmodel.integerPT, 0, 1).applyStereotype(hmodel.keyAttribute);

		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, 1);
		String expect = "SequelizeUtils.initRelationBelongsTo({\n    fromEntity: this.maClasseEntity,\n    toEntity: this.maClasse2Entity,\n    alias: \"test\",\n    foreignKey: \"id2_test\"});\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateEntityRelation(prop, class_, "", "").toString());
	}

	@Test
	public void testGenerateEnumEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);

		String expect = "\npublic initMaClasseEntity(): void {\n" + 
				"}\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateEnumEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateCallEnumEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "this.initMaClasseEntity();\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateCallEnumEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateACEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		
		String expect = "\npublic initMaClasseEntity(): void {\n" + 
				"}\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateACEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateCallACEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "maClasse", false);
		class_.applyStereotype(hmodel.nomenclature);
		
		String expect = "this.initMaClasseEntity();\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateCallACEntityGetter(class_).toString());
	}

	@Test
	public void testGenerateAttributEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "MaClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "\npublic initMaClasseTestEntity(): void {\n" + 
				"}\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateAttributEntityGetter(prop, class_, "").toString());
	}

	@Test
	public void testGenerateCallAttributEntityGetter() {
		HornetModel hmodel = HornetModel.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "MaClasse", false);
		Class class2_ = TestUtils.createClass(hmodel.pckage, "MaClasse2", false);
		
		Property prop = TestUtils.createAttribute(class_, "test", class2_, 0, -1);
		
		String expect = "this.initMaClasseTestEntity();\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateCallAttributEntityGetter(prop, class_, "").toString());
	}

	@Test
	public void testGenerateInitRelationBelongsTo() {
		String expect = "SequelizeUtils.initRelationBelongsTo({\n    fromEntity: ab,\n    toEntity: cd,\n    alias: ef,\n    foreignKey: gh});\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateInitRelationBelongsTo("ab", "cd", "ef", "gh").toString());
	}

	@Test
	public void testGenerateInitRelationBelongsToManyWithOtherKey() {
		String expect = "SequelizeUtils.initRelationBelongsToMany({\n    fromEntity: ab,\n    toEntity: cd,\n    alias: ef,\n    foreignKey: gh,\n    throughTable: ij,\n    otherKey: kl});\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateInitRelationBelongsToMany("ab", "cd", "ef", "gh", "ij", "kl").toString());
	}

	@Test
	public void testGenerateInitRelationBelongsToMany() {
		String expect = "SequelizeUtils.initRelationBelongsToMany({\n    fromEntity: ab,\n    toEntity: cd,\n    alias: ef,\n    foreignKey: gh,\n    throughTable: ij});\n";
		assertEquals(expect,PackageModelDaoClassGenerator.generateInitRelationBelongsToMany("ab", "cd", "ef", "gh", "ij").toString());
	}

}
