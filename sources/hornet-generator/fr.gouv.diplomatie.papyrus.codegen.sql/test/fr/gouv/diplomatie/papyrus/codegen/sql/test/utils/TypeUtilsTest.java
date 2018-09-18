package fr.gouv.diplomatie.papyrus.codegen.sql.test.utils;

import static org.junit.Assert.*;

import org.junit.Test;
import org.eclipse.uml2.uml.Class;

import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.sql.test.testutils.HornetModelSql;
import fr.gouv.diplomatie.papyrus.codegen.sql.utils.TypeUtils;

public class TypeUtilsTest {

	@Test
	public void testClass() {
		TypeUtils type = new TypeUtils();
		 assertEquals(TypeUtils.class,type.getClass());
	}
	
	@Test
	public void testGetEnumType() {
		 Class class_ = TestUtils.getClass("model", "package", "class", false);
		 assertEquals("integer",TypeUtils.getEnumType(class_));
	}

	@Test
	public void testGetDatabaseType() {
		HornetModelSql hmodel = HornetModelSql.initModel();
		Class class_ = TestUtils.createClass(hmodel.pckage, "class", false);
		class_.applyStereotype(hmodel.hornetType);
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeDatabaseType, null);
		assertEquals("character", TypeUtils.getDatabaseType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeDatabaseType, "");
		assertEquals("character", TypeUtils.getDatabaseType(class_));
		
		TestUtils.setStereotypePropertyValue(class_, hmodel.hornetType, hmodel.hornetTypeDatabaseType, "test");
		assertEquals("test", TypeUtils.getDatabaseType(class_));
	}

}
