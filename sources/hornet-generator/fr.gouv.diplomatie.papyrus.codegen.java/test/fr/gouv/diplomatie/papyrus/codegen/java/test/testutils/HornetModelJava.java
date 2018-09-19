package fr.gouv.diplomatie.papyrus.codegen.java.test.testutils;

import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

import fr.gouv.diplomatie.papyrus.codegen.core.test.HornetModel;
import fr.gouv.diplomatie.papyrus.codegen.core.test.TestUtils;
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils;
import fr.gouv.diplomatie.papyrus.codegen.java.utils.TypeUtils;

public class HornetModelJava  extends HornetModel{
	
	 public static final HornetModelJava instance = new HornetModelJava();
	 
	 public Stereotype naturalOrder;
	 public Stereotype booleanTyped;
	 public Stereotype dateTyped;
	 public Stereotype collection;
	 public Stereotype stringTyped;
	 public Stereotype numericTyped;
	 
	 public Property hornetTypeJavaType;
	 
	 public Property entitySchema;
	 
	 public Property attributeFetchType;
	 public Property attributeShouldBeNull;
	 
	 public Property associationTableFetchType;
	 public Property associationLinkFetchType;
	 
	 public Property booleanTypedAlwaysTrue;
	 public Property booleanTypedAlwaysFalse;
	 
	 public Property dateTypedFuture;
	 public Property dateTypedFutureOrPresent;
	 public Property dateTypedPast;
	 public Property dateTypedPastOrPresent;
	 
	 public Property collectionSizeMin;
	 public Property collectionSizeMax;
	 public Property collectionHasSizeMin;
	 public Property collectionHasSizeMax;
	 
	 public Property stringTypedCanBeEmpty;
	 public Property stringTypedPattern;
	 public Property stringTypedSizeMin;
	 public Property stringTypedHasSizeMin;
	 
	 public Property numericTypedHasMin;
	 public Property numericTypedMin;
	 public Property numericTypedHasMax;
	 public Property numericTypedMax;
	 public Property numericTypedNegative;
	 public Property numericTypedNegativeOrZero;
	 public Property numericTypedPositive;
	 public Property numericTypedPositiveOrZero;
	 public Property numericTypedDigits;
	 public Property numericTypedDigitsInteger;
	 public Property numericTypedDigitsFraction;
	 public Property numericTypedHasDecimalMin;
	 public Property numericTypedDecimalMin;
	 public Property numericTypedHasDecimalMax;
	 public Property numericTypedDecimalMax;
	 
	 public Enumeration fetchType;
	 public EnumerationLiteral fetchTypeLazy;
	 public EnumerationLiteral fetchTypeEager;

	 public HornetModelJava() {
		 
		 super();
		 
		 fetchType = TestUtils.createEnumeration(pckage, "fetchType");
		 fetchTypeLazy = TestUtils.createEnumerationLiteral(fetchType, "LAZY");
		 fetchTypeEager = TestUtils.createEnumerationLiteral(fetchType, "EAGER");
		 
		 naturalOrder = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_NATURALORDER, false);
		 TestUtils.createExtension(propmetaclass, naturalOrder, false);
		 
		 booleanTyped = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_BOOLEANTYPED, false);
		 TestUtils.createExtension(propmetaclass, booleanTyped, false);
		 
		 dateTyped = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_DATETYPED, false);
		 TestUtils.createExtension(propmetaclass, dateTyped, false);
		 
		 collection = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_COLLECTION, false);
		 TestUtils.createExtension(propmetaclass, collection, false);
		 
		 stringTyped = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_STRINGTYPED, false);
		 TestUtils.createExtension(propmetaclass, stringTyped, false);
		 
		 numericTyped = TestUtils.createStereotype(profile, JavaPluginUtils.MODEL_NUMERICTYPED, false);
		 TestUtils.createExtension(propmetaclass, numericTyped, false);
		 
		 hornetTypeJavaType = TestUtils.createAttribute(hornetType, TypeUtils.MODEL_HORNETTYPE_JAVATYPE, stringPT, 0, 1);
		 
		 entitySchema = TestUtils.createAttribute(entity, JavaPluginUtils.MODEL_SCHEMA, stringPT, 0, 1);
		 
		 attributeFetchType = TestUtils.createAttribute(attribute, Utils.MODEL_FETCHTYPE, fetchType, 0, 1);
		 attributeShouldBeNull = TestUtils.createAttribute(attribute, JavaPluginUtils.MODEL_ATTRIBUTE_SHOULDBENULL, booleanPT, 0, 1);
		 
		 associationTableFetchType = TestUtils.createAttribute(associationTable, Utils.MODEL_FETCHTYPE, fetchType, 0, 1);
		 associationLinkFetchType = TestUtils.createAttribute(associationLink, Utils.MODEL_FETCHTYPE, fetchType, 0, 1);
		 
		 booleanTypedAlwaysTrue = TestUtils.createAttribute(booleanTyped, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSTRUE, booleanPT, 0, 1);
		 booleanTypedAlwaysFalse = TestUtils.createAttribute(booleanTyped, JavaPluginUtils.MODEL_BOOLEANTYPED_ALWAYSFALSE, booleanPT, 0, 1);
			
		 dateTypedFuture = TestUtils.createAttribute(dateTyped, JavaPluginUtils.MODEL_DATETYPED_FUTURE, booleanPT, 0, 1);
		 dateTypedFutureOrPresent = TestUtils.createAttribute(dateTyped, JavaPluginUtils.MODEL_DATETYPED_FUTUREORPRESENT, booleanPT, 0, 1);
		 dateTypedPast = TestUtils.createAttribute(dateTyped, JavaPluginUtils.MODEL_DATETYPED_PAST, booleanPT, 0, 1);
		 dateTypedPastOrPresent = TestUtils.createAttribute(dateTyped, JavaPluginUtils.MODEL_DATETYPED_PASTORPRESENT, booleanPT, 0, 1);
		 
		 collectionSizeMin = TestUtils.createAttribute(collection, JavaPluginUtils.MODEL_COLLECTION_SIZEMIN, integerPT, 0, 1);
		 collectionSizeMax = TestUtils.createAttribute(collection, JavaPluginUtils.MODEL_COLLECTION_SIZEMAX, integerPT, 0, 1);
		 collectionHasSizeMin = TestUtils.createAttribute(collection, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMIN, booleanPT, 0, 1);
		 collectionHasSizeMax = TestUtils.createAttribute(collection, JavaPluginUtils.MODEL_COLLECTION_HASSIZEMAX, booleanPT, 0, 1);
		 
		 stringTypedCanBeEmpty = TestUtils.createAttribute(stringTyped, JavaPluginUtils.MODEL_STRINGTYPED_CANBEEMPTY, booleanPT, 0, 1);
		 stringTypedPattern = TestUtils.createAttribute(stringTyped, JavaPluginUtils.MODEL_STRINGTYPED_PATTERN, stringPT, 0, 1);
		 stringTypedSizeMin = TestUtils.createAttribute(stringTyped, JavaPluginUtils.MODEL_STRINGTYPED_SIZEMIN, integerPT, 0, 1);
		 stringTypedHasSizeMin = TestUtils.createAttribute(stringTyped, JavaPluginUtils.MODEL_STRINGTYPED_HASSIZEMIN, booleanPT, 0, 1);
		 
		 numericTypedHasMin = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_HASMIN, booleanPT, 0, 1);
		 numericTypedMin = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_MIN, integerPT, 0, 1);
		 numericTypedHasMax = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_HASMAX, booleanPT, 0, 1);
		 numericTypedMax = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_MAX, integerPT, 0, 1);
		 numericTypedNegative = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVE, booleanPT, 0, 1);
		 numericTypedNegativeOrZero = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_NEGATIVEORZERO, booleanPT, 0, 1);
		 numericTypedPositive = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVE, booleanPT, 0, 1);
		 numericTypedPositiveOrZero = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_POSITIVEORZERO, booleanPT, 0, 1);
		 numericTypedDigits = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITS, booleanPT, 0, 1);
		 numericTypedDigitsInteger = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITSINTEGER, integerPT, 0, 1);
		 numericTypedDigitsFraction = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_DIGITSFRACTION, integerPT, 0, 1);
		 numericTypedHasDecimalMin = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMIN, booleanPT, 0, 1);
		 numericTypedHasDecimalMax = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_HASDECIMALMAX, booleanPT, 0, 1);
		 numericTypedDecimalMin = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMIN, integerPT, 0, 1);
		 numericTypedDecimalMax = TestUtils.createAttribute(numericTyped, JavaPluginUtils.MODEL_NUMERICTYPED_DECIMALMAX, integerPT, 0, 1); 
		 
		 profile.define();
		 model.applyProfile(profile);
	 }
	 
	 public static HornetModelJava initModel() {
		instance.clean();
	 	return instance;
	 }
}
