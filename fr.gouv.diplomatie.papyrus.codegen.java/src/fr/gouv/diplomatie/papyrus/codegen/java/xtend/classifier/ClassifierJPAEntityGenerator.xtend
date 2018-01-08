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
 * @version v1.0.0
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
		import javax.persistence.Table;
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
	
	static def generateTypeAnnotation(Classifier clazz){
		if(Utils.isValueObject(clazz)){
			return '''@Embeddable'''
		}
		return 
		'''
		@Entity
		@Table(name = "«ClassifierUtils.getTableName(clazz)»")
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
 			«attribut.generateAttribut("", fromClass)»'''	
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
		val idFrom = ClassifierUtils.getId(fromClass).get(0)
		
		val fetchType = JavaPluginUtils.getFetchType(clazz)
		if(Utils.isEntity(end.type)){
			val tableName = Utils.toSnakeCase( clazz.name)
			val idFromName = Utils.toSnakeCase(idFrom.name + Utils.getFirstToUpperCase((fromClass as Classifier).name))
			val idTo = ClassifierUtils.getId(end.type as Classifier).get(0)
			val idToName = Utils.toSnakeCase(idTo.name + Utils.getFirstToUpperCase(end.type.name))
			'''
			
			@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
			@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«Utils.toSnakeCase(idFrom.name)»"),
				inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«Utils.toSnakeCase(idTo.name)»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}else if(Utils.isValueObject(end.type)){
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(clazz.name)»", joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(idFrom.name)»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}else if(Utils.isNomenclature(end.type)){
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(clazz.name)»", joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(idFrom.name)»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}else{
			
			'''
			
			@ElementCollection
			@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(clazz.name)»", joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(idFrom.name)»"))
			«clazz.visibility.getName» Set<«end.type.name»> «Utils.getFirstToLowerCase(clazz.name)»;
			'''
		}
		
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
	static def generateAttribut(Property property, String additionnalName, Classifier fromClass){
		if(Utils.isValueObject(property.type)){
			'''«property.generateVOAttribute(additionnalName, fromClass)»'''
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
					val tableName = Utils.toSnakeCase( fromClass.name+ Utils.getFirstToUpperCase(property.name))
					val idFrom = ClassifierUtils.getId(fromClass).get(0)
					val idFromName = Utils.toSnakeCase(idFrom.name + Utils.getFirstToUpperCase((fromClass as Classifier).name))
					val idTo = ClassifierUtils.getId(property.type as Classifier).get(0)
					val idToName = Utils.toSnakeCase(idTo.name + Utils.getFirstToUpperCase(property.name))
					link = 
					'''
					@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
					@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«Utils.toSnakeCase(idFrom.name)»"),
						inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«Utils.toSnakeCase(idTo.name)»"))
					'''
				}
			}else{
				val idTo = ClassifierUtils.getId(property.type as Classifier).get(0)
				
				link = 
				'''
				@ManyToOne«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				@JoinColumn(name = "«PropertyUtils.getDatabaseName(idTo, idTo.name, null)»_«PropertyUtils.getDatabaseName(property, property.name, null)»", nullable = «nullable»)
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
		
		var lengthValue = ""
		val length = PropertyUtils.getStereotypePropertyValue(property, Utils.MODEL_ATTRIBUTE, Utils.MODEL_ATTRIBUTE_LENGTH )
		if(length !== null && length != 0){
			lengthValue = '''@Size(max=«length»)'''
		}
		
		var isAsso = property.association !== null && Utils.isEntity(property.type)
		'''
		
		«property.generateAGAnnotations»
		«Utils.generateComments(property)»«property.generateAGAnnotations»
		«IF property.isMultivalued && !Utils.isEntity(property.type)»
		@ElementCollection
«««		@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»"«IF ! Utils.isValueObject(fromClass)», joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(id.name)»")«ENDIF»)
		«ELSE»
		«IF isId»@Id«ENDIF»«IF Utils.isSequence(property)»
		@GeneratedValue(strategy=GenerationType.SEQUENCE)«ENDIF»
		«IF isEnum»
		@Enumerated
		«ENDIF»«IF Utils.isEntity(property.type) && !isAsso»
		«link»«ELSEIF isAsso»
		«property.generateAssociationAnnotation(fromClass)»
		«ELSE»
		«IF nullable == false»@NotNull«ENDIF»
		«IF lengthValue != ""»«lengthValue»«ENDIF»
		@Column(name = "«columnName»")
		«ENDIF»«ENDIF»
		«property.visibility.getName» «array»«typeName»«endArray» «name»;
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
		val tableName = Utils.toSnakeCase( fromClass.name+ Utils.getFirstToUpperCase(property.name))
		val idFrom = ClassifierUtils.getId(fromClass).get(0)
		val idFromName = Utils.toSnakeCase(idFrom.name + Utils.getFirstToUpperCase((fromClass as Classifier).name))
		val idToName = Utils.toSnakeCase(id.name + Utils.getFirstToUpperCase(property.name))
		if(fromMultiplicity){
			if(toMultiplicity){
				
				'''
				@ManyToMany«IF fetchType!==null»(fetch=FetchType.«fetchType.name»)«ENDIF»
				@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«Utils.toSnakeCase(idFrom.name)»"),
					inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«Utils.toSnakeCase(id.name)»"))
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
				@JoinTable(name="«tableName»", joinColumns=@JoinColumn(name="«idFromName»", referencedColumnName="«Utils.toSnakeCase(idFrom.name)»"),
					inverseJoinColumns=@JoinColumn(name="«idToName»", referencedColumnName="«Utils.toSnakeCase(id.name)»", unique=true))
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
	
	static def generateVOAttribute(Property property, String additionnalName, Classifier fromClass){
		var name = Utils.addAdditionnalName(additionnalName, property.name)
		val nullable = PropertyUtils.isNullable(property)
		var array =""
		var endArray =""
		if(property.isMultivalued){
			array= "Set<"
			endArray =">"
		}
		var typeName = property.type.name;
		
		val id = ClassifierUtils.getId(fromClass).get(0)
		'''
		
		«Utils.generateComments(property)»«property.generateAGAnnotations»
		«IF property.isMultivalued »
		@ElementCollection
		@CollectionTable(name = "«Utils.toSnakeCase(fromClass.name)»_«Utils.toSnakeCase(name)»", joinColumns = @JoinColumn(name = "«Utils.toSnakeCase(id.name)»"))
		«ELSE»
		«IF nullable == false»@NotNull«ENDIF»
		@Embedded
		«ENDIF»
		«property.visibility.getName» «array»«typeName»«endArray» «name»;
		'''
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