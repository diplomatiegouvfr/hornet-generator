/**
 * Copyright ou © ou Copr. Ministère de l'Europe et des Affaires étrangères (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * Ce logiciel est un programme informatique servant à faciliter la création
 * d'applications Web conformément aux référentiels généraux français : RGI, RGS et RGAA
 * <p/>
 * Ce logiciel est régi par la licence CeCILL soumise au droit français et
 * respectant les principes de diffusion des logiciels libres. Vous pouvez
 * utiliser, modifier et/ou redistribuer ce programme sous les conditions
 * de la licence CeCILL telle que diffusée par le CEA, le CNRS et l'INRIA
 * sur le site "http://www.cecill.info".
 * <p/>
 * En contrepartie de l'accessibilité au code source et des droits de copie,
 * de modification et de redistribution accordés par cette licence, il n'est
 * offert aux utilisateurs qu'une garantie limitée.  Pour les mêmes raisons,
 * seule une responsabilité restreinte pèse sur l'auteur du programme,  le
 * titulaire des droits patrimoniaux et les concédants successifs.
 * <p/>
 * A cet égard  l'attention de l'utilisateur est attirée sur les risques
 * associés au chargement,  à l'utilisation,  à la modification et/ou au
 * développement et à la reproduction du logiciel par l'utilisateur étant
 * donné sa spécificité de logiciel libre, qui peut le rendre complexe à
 * manipuler et qui le réserve donc à des développeurs et des professionnels
 * avertis possédant  des  connaissances  informatiques approfondies.  Les
 * utilisateurs sont donc invités à charger  et  tester  l'adéquation  du
 * logiciel à leurs besoins dans des conditions permettant d'assurer la
 * sécurité de leurs systèmes et ou de leurs données et, plus généralement,
 * à l'utiliser et l'exploiter dans les mêmes conditions de sécurité.
 * <p/>
 * Le fait que vous puissiez accéder à cet en-tête signifie que vous avez
 * pris connaissance de la licence CeCILL, et que vous en avez accepté les
 * termes.
 * <p/>
 * <p/>
 * Copyright or © or Copr. Ministry for Europe and Foreign Affairs (2017)
 * <p/>
 * pole-architecture.dga-dsi-psi@diplomatie.gouv.fr
 * <p/>
 * This software is a computer program whose purpose is to facilitate creation of
 * web application in accordance with french general repositories : RGI, RGS and RGAA.
 * <p/>
 * This software is governed by the CeCILL license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 * <p/>
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 * <p/>
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 * <p/>
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 *
 */

/**
 * fr.gouv.diplomatie.papyrus.codegen.java - Générateur de code java pour 
 * des applications Hornet JS
 *
 * @author MEAE - Ministère de l'Europe et des Affaires étrangères
 * @version v1.1.0
 * @license CECILL-2.1
 */
package fr.gouv.diplomatie.papyrus.codegen.java.xtend.classifier;

import org.eclipse.uml2.uml.Classifier
import org.eclipse.uml2.uml.Property
import fr.gouv.diplomatie.papyrus.codegen.core.utils.ClassifierUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.Utils
import fr.gouv.diplomatie.papyrus.codegen.java.transformations.ProjectJPAEntityElementsCreator
import org.eclipse.uml2.uml.NamedElement
import fr.gouv.diplomatie.papyrus.codegen.java.utils.TypeUtils
import fr.gouv.diplomatie.papyrus.codegen.core.utils.PropertyUtils
import org.eclipse.uml2.uml.Type
import fr.gouv.diplomatie.papyrus.codegen.java.generators.GeneratorUtils
import java.util.ArrayList
import org.eclipse.uml2.uml.AssociationClass
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaPluginUtils
import fr.gouv.diplomatie.papyrus.codegen.java.utils.JavaClassifierUtils

public class ClassifierJPAEntityGenerator {
	
	/**
	 * Génère les imports du générateur d'annotation associé
	 */
	static def generateAGImports(NamedElement elem){
		if(ProjectJPAEntityElementsCreator.annotationGenerator !== null){
			return ProjectJPAEntityElementsCreator.annotationGenerator.generateImports(elem)
		}
		
		return ''''''
	}
	
	/**
	 * Génère les annotations du générateur d'annotation associé
	 */
	static def generateAGAnnotations(NamedElement elem){
		if(ProjectJPAEntityElementsCreator.annotationGenerator !== null){
			return ProjectJPAEntityElementsCreator.annotationGenerator.generateAnnotations(elem)
		}
		
		return ''''''
	}
	
	static def generateCode(Classifier clazz){
		
		'''
		package «Utils.toImport(GeneratorUtils.getEntityPackage(clazz))»;
		
		import java.io.Serializable;
		import java.util.Date;
		import java.util.Set;
		
		import javax.persistence.AssociationOverride;
		import javax.persistence.AssociationOverrides;
		import javax.persistence.AttributeOverride;
		import javax.persistence.AttributeOverrides;
		import javax.persistence.CascadeType;
		import javax.persistence.CollectionTable;
		import javax.persistence.Column;
		import javax.persistence.Convert;
		import javax.persistence.ElementCollection;
		import javax.persistence.Embeddable;
		import javax.persistence.Embedded;
		import javax.persistence.Enumerated;
		import javax.persistence.Entity;
		import javax.persistence.FetchType;
		import javax.persistence.GeneratedValue;
		import javax.persistence.GenerationType;
		import javax.persistence.Inheritance;
		import javax.persistence.Id;
		import javax.persistence.JoinColumn;
		import javax.persistence.JoinTable;
		import javax.persistence.ManyToMany;
		import javax.persistence.ManyToOne;
		import javax.persistence.OneToMany;
		import javax.persistence.OneToOne;
		import javax.persistence.SequenceGenerator;
		import javax.persistence.Table;
		import javax.validation.constraints.AssertTrue;
		import javax.validation.constraints.AssertFalse;
		import javax.validation.constraints.DecimalMax;
		import javax.validation.constraints.DecimalMin;
		import javax.validation.constraints.Digits;
		import javax.validation.constraints.Future;
		import javax.validation.constraints.FutureOrPresent;
		import javax.validation.constraints.Min;
		import javax.validation.constraints.Max;
		import javax.validation.constraints.Negative;
		import javax.validation.constraints.NegativeOrZero;
		import javax.validation.constraints.NotEmpty;
		import javax.validation.constraints.NotNull;
		import javax.validation.constraints.Null;
		import javax.validation.constraints.Past;
		import javax.validation.constraints.PastOrPresent;
		import javax.validation.constraints.Pattern;
		import javax.validation.constraints.Positive;
		import javax.validation.constraints.PositiveOrZero;
		import javax.validation.constraints.Size;
		«clazz.generateImports»
		
		«Utils.generateComments(clazz)»«clazz.generateInheritAnnotation»
		«clazz.generateTypeAnnotation»
		«clazz.generateAGAnnotations»
		public class «clazz.name» «clazz.generateExtends» «clazz.generateImplements» {
			
		    /** The Constant serialVersionUID. */
		    private static final long serialVersionUID = 1L;
		    «clazz.generateAttributes(clazz)»
		    «clazz.generateCompareTo»
		}
		'''
	}
	
	/**
	 * génère les attributs issus des associations
	 */
	static def generateAssociationsAttributes(Classifier clazz){
		val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		
		'''
		«associationsClasses.fold("")[acc, asso |
			acc + '''«(asso as AssociationClass).generateAssociationAttributes(clazz)»'''
		]»
		'''
	}
	
	static def generateTypeAnnotation(Classifier clazz){
		if(Utils.isValueObject(clazz)){
			return '''@Embeddable'''
		}
		var schema = JavaClassifierUtils.getSchema(clazz)
		return 
		'''
		@Entity
		@Table(name = "«Utils.toDbName(ClassifierUtils.getTableName(clazz))»"«IF schema !== null», schema = "«schema»"«ENDIF»)
		'''
	}
	
	static def generateInheritAnnotation(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		if(interfaces.length > 0 ){
			return '''
			@Inheritance
			'''
		}
	}
	
	/**
	 * génère les imports de la classe
	 */
	static def generateImports(Classifier clazz){
		
		var imports = clazz.generateAllImports(newArrayList())
		'''
		«imports.fold("")[acc, type|
 			acc + '''
 			
 			«type.generateImport()»'''	
 		]»
		
		«clazz.generateAGImports»
		'''
	}
	
	/**
	 * génère les imports de la classes liés aux attributs
	 */
	static def generateAllImports(Classifier clazz, ArrayList<Type> types){
		var attributes = ClassifierUtils.getOwnedAttributes(clazz).filter[attribut | 
			Utils.isEntity(attribut.type) || (Utils.isValueObject(attribut.type)) || (Utils.isNomenclature(attribut.type))
		]
		
		/**
		 * attributs venant des interfaces
		 */
		val interfaces = clazz.directlyRealizedInterfaces
		
		for(interface : interfaces){
		 	var interfaceAtteributes = ClassifierUtils.getOwnedAttributes(interface).filter[attribut | 
				Utils.isEntity(attribut.type) || (Utils.isValueObject(attribut.type)) || (Utils.isNomenclature(attribut.type))
			]
			
			for(attribut : interfaceAtteributes){
				if(!types.contains(attribut.type)){
					types.add(attribut.type)
				}
			}
		}
		
		/**
		 * attributs venant des tables d'association
		 */
		 
		 if(Utils.isEntity(clazz)){
		 	val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		 	for(asso: associationsClasses){
		 		val end = (asso as AssociationClass).ownedEnds.filter[property | property.type !== clazz].get(0)
		 		if(!types.contains(end.type)){
					types.add(end.type)
				}
		 	}
		 }
		 	
		
		/**
		 * classes étendues
		 */
		val extends = clazz.generalizations
		
		for(extend : extends){
			if(!types.contains(extend.general)){
				types.add(extend.general)
			}
		}
		
		for(attribut : attributes){
			if(!types.contains(attribut.type)){
				types.add(attribut.type)
			}
		}
		
		return types;
		
	}
	
	static def generateImport(Type type){
		'''import «Utils.toImport(GeneratorUtils.getEntityPackage(type as Classifier))».«type.name»;'''
	}
	
	/**
	 * génère les attributs simples de l'entité
	 */
	static def generateAttributes(Classifier clazz, Classifier fromClass){
		val attributes = ClassifierUtils.getOwnedAttributes(clazz)
		'''
		«attributes.fold("")[acc, attribut|
 			acc + '''
 			«attribut.generateAttribut("", "", fromClass)»'''	
 		]»
		«clazz.generateInterfaceAttributes»
		«IF Utils.isEntity(clazz)»«clazz.generateATAttributes»«ENDIF»
 		'''
	}
	
	static def generateATAttributes(Classifier clazz){
		val associationsClasses = ClassifierUtils.getLinkedAssociationClass(clazz)
		
		'''
		«associationsClasses.fold("")[acc, asso |
			acc + '''«(asso as AssociationClass).generateAssociationAttributes(clazz)»'''
		]»
		'''
	}
	
	static def generateAssociationAttributes(AssociationClass clazz, Classifier fromClass){
		val end = clazz.ownedEnds.filter[property | property.type !== fromClass].get(0)
		val fromEnd = clazz.ownedEnds.filter[property | property.type == fromClass].get(0)
		val idFrom = ClassifierUtils.getId(fromClass).get(0)
		val idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "")
		
		val fetchType = JavaPluginUtils.getFetchType(clazz)
		if(Utils.isEntity(end.type)){
			val tableName = Utils.toDbName( clazz.name)
			
			val idFromName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "")+ "_" + Utils.toDbName(fromEnd.name)
			
			
			val idTo = ClassifierUtils.getId(end.type as Classifier).get(0)
			//val idToName = Utils.toDbName(idTo.name + Utils.getFirstToUpperCase(end.type.name))
			val idToName = PropertyUtils.getDatabaseName(idTo, idTo.name, "")+ "_" + Utils.toDbName(end.name)
			val idToDbName = PropertyUtils.getDatabaseName(idTo, idTo.name, "")
			'''
			
			@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
			@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«idFromDbName»"),
				inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«idToDbName»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(end.name)»;
			'''
		}else if(Utils.isValueObject(end.type)){
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toDbName(fromClass.name)»_«Utils.toDbName(clazz.name)»")
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}else /*if(Utils.isNomenclature(end.type))*/{
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toDbName(fromClass.name)»_«Utils.toDbName(clazz.name)»", joinColumns = @JoinColumn(name = "«idFromDbName»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}/*else{
			
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(clazz.name)»", joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(idFrom.name)»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}*/
		
	}
	
	static def generateInterfaceAttributes(Classifier clazz){
		val interfaces = clazz.directlyRealizedInterfaces
		'''
		«interfaces.fold("")[acc, interface |
			acc + '''«interface.generateAttributes(clazz)»'''
		]»
		'''
	}
	
	/**
	 * génère un attribut de l'entité
	 */
	static def generateAttribut(Property property, String additionnalName, String additionnalDBName, Classifier fromClass){
		if(Utils.isValueObject(property.type)){
			'''«property.generateVOAttribute(additionnalName, additionnalDBName, fromClass)»'''
		}else{
			'''«property.generateBasicAttribute(additionnalName, fromClass)»'''
		}
	}
	
	/**
	 * génère un attribut de l'entité
	 */
	static def generateBasicAttribute(Property property, String additionnalName, Classifier fromClass){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		val fetchType = JavaPluginUtils.getFetchType(property)
		val nullable = PropertyUtils.isNullable(property)
		var array =""
		var link=""
		var endArray =""
		if(property.isMultivalued){
			array= "Set<"
			endArray =">"	
		}
		
		if(Utils.isEntity(property.type)){
			if(property.isMultivalued){
				if(ClassifierUtils.getId(property.type as Classifier) !== null){
					val tableName = Utils.toDbName( fromClass.name+ Utils.getFirstToUpperCase(property.name))
					
					val idFrom = ClassifierUtils.getId(fromClass).get(0)
					val idFromName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "") + "_" + Utils.toDbName(fromClass.name)
					val idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "")
					
					val idTo = ClassifierUtils.getId(property.type as Classifier).get(0)
					//val idToName = idTo.name + Utils.getFirstToUpperCase(property.name)
					val idToName = PropertyUtils.getDatabaseName(idTo, idTo.name, "") + "_" + PropertyUtils.getDatabaseName(property, property.name, "")
					val idToDbName = PropertyUtils.getDatabaseName(idTo, idTo.name, "")
					
					link = 
					'''
					@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
					@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«idFromDbName»"),
						inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«idToDbName»"))
					'''
				}
			}else{
				val idTo = ClassifierUtils.getId(property.type as Classifier).get(0)
				
				link = 
				'''
				@ManyToOne«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				//@JoinColumn(name = "«PropertyUtils.getDatabaseName(idTo, idTo.name, null)»_«PropertyUtils.getDatabaseName(property, property.name, null)»", nullable = «nullable»)
				'''
			}
		}
		
		var typeName = ""
		if(Utils.isEntity(property.type) || Utils.isNomenclature(property.type)){
			typeName = property.type.name
		}else{
			typeName = TypeUtils.getJavaType(property.type)
		}
		
		val isEnum = Utils.isNomenclature(property.type)
		val isId = PropertyUtils.isID(property)
		val columnName = PropertyUtils.getDatabaseName(property, property.name, additionnalName)
		
		var isAsso = property.association !== null && Utils.isEntity(property.type)
		
		'''
		
		«property.generateAGAnnotations»
		«Utils.generateComments(property)»«property.generateAGAnnotations»
		«property.generateTypeAnnotation»
		«IF property.isMultivalued && !Utils.isEntity(property.type)»
		@ElementCollection
«««		@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»"«IF ! Utils.isValueObject(fromClass)», joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(id.name)»")«ENDIF»)
		«ELSE»
		«IF isId»@Id«ENDIF»«IF Utils.isSequence(property)»
		«property.generateSequence(name, fromClass)»
		«ENDIF»
		«IF isEnum»
		@Enumerated
		«ENDIF»«IF Utils.isEntity(property.type) && !isAsso»
		«link»«ELSEIF isAsso»
		«property.generateAssociationAnnotation(fromClass)»
		«ELSE»
		«IF nullable == false»@NotNull«ENDIF»
		@Column(name = "«columnName»")
		«ENDIF»«ENDIF»
		«property.visibility.getName» «array»«typeName»«endArray» «name»;
		'''
	}
	
	static def generateSequence(Property property, String name, Classifier fromClass){
		val startWith = Utils.getSequenceStartWith(property)
		val incrementBy = Utils.getSequenceIncrementBy(property)
		
		'''
		//@SequenceGenerator(name="«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»_generator", initialValue=«startWith», allocationSize=«incrementBy», sequenceName="«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»_sequence")
		//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»_generator")
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		'''
	}
	
	static def generateAssociationAnnotation(Property property, Classifier fromClass){
		val members = property.association.ownedEnds
		val nullable = PropertyUtils.isNullable(property)
		val fetchType = JavaPluginUtils.getFetchType(property)
		val member = members.get(0)
		val fromMultiplicity = member.isMultivalued
		val toMultiplicity = property.isMultivalued
		val id = ClassifierUtils.getId(property.type as Classifier).get(0)
		val tableName = Utils.toDbName( fromClass.name+ Utils.getFirstToUpperCase(property.name))
		
		val idFrom = ClassifierUtils.getId(fromClass).get(0)
		//val idFromName = Utils.toDbName(idFrom.name + Utils.getFirstToUpperCase((fromClass as Classifier).name))
		val idFromName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "") + "_" + Utils.toDbName(fromClass.name)
		val idFromDbName = PropertyUtils.getDatabaseName(idFrom, idFrom.name, "")
		
		//val idToName = Utils.toDbName(id.name + Utils.getFirstToUpperCase(property.name))
		val idToName = PropertyUtils.getDatabaseName(id, id.name, "") + "_" + PropertyUtils.getDatabaseName(property, property.name, "")
		val idToDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		
		if(fromMultiplicity){
			if(toMultiplicity){
				
				'''
				@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«idFromDbName»"),
					inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«idToDbName»"))
				'''
			}else{
				'''
				«IF nullable == false»@NotNull«ENDIF»
				@ManyToOne«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				@JoinColumn(name = "«PropertyUtils.getDatabaseName(id, id.name, null)»_«PropertyUtils.getDatabaseName(property, property.name, null)»")
				'''
			}
		}else{
			if(toMultiplicity){
				'''
				@OneToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«idFromDbName»"),
					inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«idToDbName»", unique=true))
				'''
			}else{
				'''
				«IF nullable == false»@NotNull«ENDIF»
				@OneToOne(cascade = {CascadeType.REMOVE})
				@JoinColumn(name = "«PropertyUtils.getDatabaseName(id, id.name, null)»_«PropertyUtils.getDatabaseName(property, property.name, null)»")
				'''
			}
		}
	}
	
	static def generateVOAttribute(Property property, String additionnalName, String additionnalDBName, Classifier fromClass){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		var dbName = PropertyUtils.getDatabaseName(property, property.name, additionnalDBName)
		val nullable = PropertyUtils.isNullable(property)
		var array =""
		var endArray =""
		if(property.isMultivalued){
			array= "Set<"
			endArray =">"
		}
		var typeName = property.type.name;
		
		val id = ClassifierUtils.getId(fromClass).get(0)
		
		val idDbName = PropertyUtils.getDatabaseName(id, id.name, "")
		'''
		
		«Utils.generateComments(property)»«property.generateAGAnnotations»
		«IF property.isMultivalued »
		@ElementCollection
		@CollectionTable(name = "«Utils.toDbName(fromClass.name)»_«Utils.toDbName(name)»", joinColumns = @JoinColumn(name = "«idDbName»"))
		«ELSE»
		«IF nullable == false»@NotNull«ENDIF»
		@Embedded
		«ENDIF»
		«IF !property.isMultivalued»
		«property.generateVOOverrides(dbName)»
		«ENDIF»
		«property.visibility.getName» «array»«typeName»«endArray» «name»;
		'''
	}
	
	static def generateTypeAnnotation(Property property){
		val shouldbeNull = JavaPluginUtils.getShouldBeNull(property)
		var retour =
		'''
		«IF shouldbeNull == true»@Null«ENDIF»
		'''
		
		if (Utils.hasStereotype(property, JavaPluginUtils.MODEL_BOOLEANTYPED)){
			retour += 
			'''
			«property.generateBooleanTypedAnnotation»
			'''
		}
		if(Utils.hasStereotype(property, JavaPluginUtils.MODEL_DATETYPED)){
			retour += 
			'''
			«property.generateDateTypedAnnotation»
			'''
		}
		if(Utils.hasStereotype(property, JavaPluginUtils.MODEL_STRINGTYPED)){
			retour += 
			'''
			«property.generatStringTypedAnnotation»
			'''
		}
		if(Utils.hasStereotype(property, JavaPluginUtils.MODEL_NUMERICTYPED)){
			retour += 
			'''
			«property.generateNumericTypedAnnotation»
			'''
		}
		if(Utils.hasStereotype(property, JavaPluginUtils.MODEL_COLLECTION)){
			retour += 
			'''
			«property.generateCollectionAnnotation»
			'''
		}
		
		return retour
	}
	
	static def generateBooleanTypedAnnotation(Property property){
		val alwaysTrue = JavaPluginUtils.getAlwaysTrue(property)
		val alwaysFalse = JavaPluginUtils.getAlwaysFalse(property)
		'''
		«IF alwaysTrue == true»
		@AssertTrue
		«ELSEIF alwaysFalse == true»
		@AssertFalse
		«ENDIF»
		'''
	}
	
	static def generateDateTypedAnnotation(Property property){
		val future = JavaPluginUtils.getFuture(property)
		val futureorPresent = JavaPluginUtils.getFutureOrPresent(property)
		val past = JavaPluginUtils.getPast(property)
		val pastOrPresent = JavaPluginUtils.getPastOrPresent(property)
		
		'''
		«IF future == true»
		@Future
		«ELSEIF futureorPresent == true»
		@FutureOrPresent
		«ELSEIF past == true»
		@Past
		«ELSEIF pastOrPresent == true»
		@PastOrPresent
		«ENDIF»
		'''
	}
	
	static def generatStringTypedAnnotation(Property property){
		val canBeEmpty = JavaPluginUtils.getCanBeEmpty(property)
		val pattern = JavaPluginUtils.getPattern(property)
		val sizeMin = JavaPluginUtils.getSizeMin(property)
		val length = Utils.getAttributeLength(property)
		'''
		«IF canBeEmpty == false»
		@NotEmpty
		«ENDIF»
		«IF pattern !== null»
		@Pattern(regexp = "«pattern»")
		«ENDIF»
		«IF sizeMin !== null && length !== null»
		@Size( min = «sizeMin», max = «length»)
		«ELSEIF sizeMin !== null»
		@Size( min = «sizeMin»)
		«ELSEIF length !== null»
		@Size( max = «length»)
		«ENDIF»
		'''
	}
	
	static def generateNumericTypedAnnotation(Property property){
		val min = JavaPluginUtils.getMin(property)
		val max = JavaPluginUtils.getMax(property)
		val negative = JavaPluginUtils.getNegative(property)
		val negativeOrZero = JavaPluginUtils.getNegativeOrZero(property)
		val positive = JavaPluginUtils.getPositive(property)
		val positiveOrZero = JavaPluginUtils.getPositiveOrZero(property)
		val digits = JavaPluginUtils.getDigits(property)
		val digitsInteger = JavaPluginUtils.getDigitsInteger(property)
		val digitsFraction = JavaPluginUtils.getDigitsFraction(property)
		val decimalMin = JavaPluginUtils.getDecimalMin(property)
		val decimalMax = JavaPluginUtils.getDecimalMax(property)
		
		'''
		«IF min !== null»
		@Min(«min»)
		«ENDIF»
		«IF max !== null»
		@Max(«max»)
		«ENDIF»
		«IF negative == true»
		@Negative
		«ELSEIF negativeOrZero == true»
		@NegativeOrZero
		«ELSEIF positive == true»
		@Positive
		«ELSEIF positiveOrZero == true»
		@PositiveOrZero
		«ENDIF»
		«IF digits == true»
		«IF digitsInteger !== null && digitsFraction !== null»
		@Digits(integer = «digitsInteger», fraction = «digitsFraction»)
		«ELSEIF digitsInteger !== null»
		@Digits(integer = «digitsInteger»)
		«ELSEIF digitsFraction !== null»
		@Digits(fraction = «digitsFraction»)
		«ENDIF»
		«ENDIF»
		«IF decimalMin !== null»
		@DecimalMin("«decimalMin»")
		«ENDIF»
		«IF decimalMax !== null»
		@DecimalMax("«decimalMax»")
		«ENDIF»
		'''
	}
	
	static def generateCollectionAnnotation(Property property){
		val sizeMin = JavaPluginUtils.getCollectionSizeMin(property)
		val sizeMax = JavaPluginUtils.getCollectionSizeMax(property)
		
		'''
		«IF sizeMin !== null && sizeMax !== null»
		@Size( min = «sizeMin», max = «sizeMax»)
		«ELSEIF sizeMin !== null»
		@Size( min = «sizeMin»)
		«ELSEIF sizeMax !== null»
		@Size( max = «sizeMax»)
		«ENDIF»
		'''
	}
	
	
	/**
	 * génère les override pour les classe embeddable dans une autre classe
	 */
	static def generateVOOverrides(Property property, String name){
		val basicAttributes = ClassifierUtils.getOwnedAttributes(property.type as Classifier).filter[attr|
			!Utils.isEntity(attr.type ) && !Utils.isValueObject(attr.type)
		]
		
		val entityAttributes = ClassifierUtils.getOwnedAttributes(property.type as Classifier).filter[attr|
			Utils.isEntity(attr.type )
		]
		
		'''
		@AttributeOverrides({
			«basicAttributes.fold("")[acc, attribut |
				if(acc == ""){
					acc + '''«attribut.generateAttributeOverride(name)»'''
				}else{
					acc + ''',
	«attribut.generateAttributeOverride(name)»'''
				}
		]»
		})
		@AssociationOverrides({
			«entityAttributes.fold("")[acc, attribut |
					if(acc == ""){
						acc + '''«attribut.generateAssociationOverride(name)»'''
					}else{
						acc + ''',
	«attribut.generateAssociationOverride(name)»'''
					}
			]»
		})'''
	}
	
	static def generateAttributeOverride(Property property, String attributName){
		if(!Utils.isEntity(property.type ) && !Utils.isValueObject(property.type)){
			val propName = PropertyUtils.getDatabaseName(property, property.name, "")
			'''@AttributeOverride(name="«property.name»",column = @Column(name = "«attributName»_«propName»"))'''
		}else{
			return ''''''
		}
	}
	
	static def generateAssociationOverride(Property property, String attributName){
		val type = property.type 
		if(Utils.isEntity(type)){
			val id = ClassifierUtils.getId(type as Classifier).get(0)
			val propName = PropertyUtils.getDatabaseName(property, property.name, "")
			val idName = PropertyUtils.getDatabaseName(id, id.name, "")
			'''@AssociationOverride(name="«property.name»",joinColumns = @JoinColumn(name = "«attributName»_«idName»_«propName»"))'''
		}else{
			return ''''''
		}
	}
	

	static def generateCompareTo(Classifier clazz){
		var naturalOrder = JavaClassifierUtils.getNaturalOrderField(clazz)
		if(naturalOrder === null){
			return ''''''
		}else{
			return 
			'''
			
			@Override
			public int compareTo(«clazz.name» other) {
				return this.«naturalOrder.name».compareTo(other.«naturalOrder.name»);
			}
			'''
		}
	}

	/**
	 * génère les extends
	 */
	static def generateExtends(Classifier clazz){
		val parents = clazz.generalizations
		if(parents.length > 0){
			val parent = parents.get(0)
			'''extends «parent.general.name»'''
		}else{
			''''''
		}
	}
	
	/**
	 * génère les implements
	 */
	static def generateImplements(Classifier clazz){
		val extends = clazz.generalizations
		val naturalOrder = JavaClassifierUtils.getNaturalOrderField(clazz)
		var comparable = ""
		if (naturalOrder !== null){
			comparable = ''' Comparable<«clazz.name»>'''
		}
		if(extends.length > 0){
			'''«IF comparable !== ""»implements «comparable»«ENDIF»'''
		}else{
			'''implements Serializable«IF comparable !== ""»,«comparable»«ENDIF»'''
		}
	}
}