package fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.test.testutils;

import org.eclipse.uml2.uml.Property;

import fr.gouv.diplomatie.papyrus.codegen.annotation.lombok.utils.LombokClassifierUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;

public class HornetModelLombok extends HornetModel{
	 public static final HornetModelLombok instance = new HornetModelLombok();
	 
	 public Property entityLombokGetter;
	 public Property entityLombokSetter;
	 public Property entityLombokNoArgsCons;
	 public Property entityLombokAllArgsCons;
	 public Property entityLombokToString;
	 public Property entityLombokEqAndHashc;
	 public Property attributeLombokDisplayWith;
	 public Property associationTableLombokDisplayWith;
	 public Property associationLinkLombokDisplayWith;
	 
	 public HornetModelLombok() {
		 super();
		 entityLombokGetter = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKGETTER, booleanPT, 0, 1);
		 entityLombokSetter = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKSETTER, booleanPT, 0, 1);
		 entityLombokNoArgsCons = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKNOARGSCONS, booleanPT, 0, 1);
		 entityLombokAllArgsCons = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKALLARGSCONS, booleanPT, 0, 1);
		 entityLombokToString = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKTOSTRING, booleanPT, 0, 1);
		 entityLombokEqAndHashc = TestUtils.createAttribute(entity, LombokClassifierUtils.MODEL_ENTITY_LOMBOKEQANDHASHC, booleanPT, 0, 1);
		 attributeLombokDisplayWith = TestUtils.createAttribute(attribute, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH, booleanPT, 0, 1);
		 associationTableLombokDisplayWith = TestUtils.createAttribute(associationTable, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH, booleanPT, 0, 1);
		 associationLinkLombokDisplayWith = TestUtils.createAttribute(associationLink, LombokClassifierUtils.MODEL_LOMBOKDISPLAYWITH, booleanPT, 0, 1);
		 
		 profile.define();
		 model.applyProfile(profile);
	 }
	 
	 public static HornetModelLombok initModel() {
		instance.clean();
	 	return instance;
	 }
}
