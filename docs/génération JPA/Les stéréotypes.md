# Les stéréotypes pour la génération JPA

Chaque classe ou attribut du modèle doit avoir un stéréotype associé.
Les stéréotypes apportent des précisions pour la génération de code.

## Le stéréotype domain

Le stéréotype domain peut être assigné au package qui contient les classes.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| schema | détermine le schema des tables présentes dans le package. | String | null |

## Le stéréotype application

Le stéréotype application peut être assigné au model (dans la vue Model Explorer).

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| rootPackage | Indique le package de base des classes générées                  | String | fr.gouv.dimplomatie |

## Les stéréotypes de classe

Les stéréotypes de classe proposés par le profil sont les suivants:

- entity: représente une entité majeure du métier qui regroupe des attributs.

Un exemple d'entity est présent dans le tutoriel ci-dessus (classe `Partenaire` ou `Secteur`)

- valueObject: représente une entité de second rang qui n'a d'existence dans le système que parce qu'elle est liée à une entité. Un valueObject ne sera donc généré que s'il est présent en tant que champs dans une classe.

Un exemple de valueObject est présent dans le tutoriel ci-dessus (classe `Assistant`)

- nomenclature: définit un ensemble de couple code / libellé servant à caractériser des entités. Attention il ne s'agit pas d'un référentiel, pour cela utiliser le stéréotype entity.
Les classes possédant le stéréotype `nomenclature` ne doivent posséder que des attributs avec le stéréotype `CodeLibelleNomenclature`. il s'agira des valeurs possibles de l'enuméré. Cette table est généré avec deux champs, code et libéllé.


### Les attributs du stéréotype entity

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |
| tableName | Nom de la table dans la base de données                            | String | null (valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

Attributs spécifiques à la génération JPA:

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| lombokGetter | Indique si l'annotation getter de lombok doit etre générée       | boolean | false |
| lombokSetter | Indique si l'annotation setter de lombok doit etre générée       | boolean | false |
| lombokNoArgsConstructor| Indique si l'annotation NoArgsConstructor de lombok doit etre générée| boolean | true |
| lombokAllArgsConstructor| Indique si l'annotation AllArgsConstructor de lombok doit etre générée| boolean | true |
| lombokToString| Indique si l'annotation ToString de lombok doit etre générée| boolean | false |
| lombokEqualsAndHashCode| Indique si l'annotation EqualsAndHashCode de lombok doit etre générée| boolean | false |
| fetchType | Mode de chargement de la donnée | FetchType | EAGER |

### Les attributs du stéréotype valueObject

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |

### Les attributs du stéréotype nomenclature

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| generated | Indique si la classe doit être générée ou non                      | boolean | true |
| tableName | Nom de la table dans la base de données                            | String | null (valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

## Les stéréotypes de classe d'association

Le stéréotype `AssociationTable` est disponible pour les classes d'associations (ne pas l'ajouter à une classe basique (class)).

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| tableName | nom de la table                     | string | null(valeur générée: nom de la table en Snake case) |
| [`DEPRECATED`: utiliser plutot la gestion par package avec le stéréotype `domain`] schema    | Nom du schema de la table (sera pris en compte plutot que celui du domain) | String | null |

## Les stéréotypes d'attributs

- attribute: attribut de la classe.
- keyAttribute: identifiant de la classe.

Un attribut devra avoir au moins un des deux attributs attribute ou keyAttribute.

- sequence: sequence liée a un attributs. Elle ne seront appliquées que pour les attributs de type KeyAttribute.
- CodeLibelleNomenclature: couple code / libellé d'une nomenclature.
- naturalOrder : il définit si le champs sera présent dans la méthode de comparaison compareTo de la classe
- les stéréotypes de type de champs qui permettent d'ajouter des précisions sur le champs

### Les attributs du stéréotype attribute et KeyAttribute

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| length | Longueur du champ (l'attribut hasLength doit etre mis a true pour que celle-ci soit pris en compte)| Integer | 0 |
| ColumnName | Nom de la colonne en base de donnée | String | null (valeur générée: nom du champs en Snake case ( + préfixes éventuellement)) |
| hasLength | Indique si l'attribut length est valué | boolean | false |
| index		| Indique si un index doit être génré ou non sur la colonne | boolean | false |

Attributs spécifiques à la génération JPA:

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| lombokDisplayWith | Indique si le champs doit etre présent dans l'annotation ToString de lombok | boolean | false |
| fetchType | Mode de chargement de la donnée | FetchType | EAGER |
| shouldBeNull | Indique si l'annotation [Null](http://beanvalidation.org/2.0/spec/#builtinconstraints-null) de Java Bean Validation doit etre générée | boolean | false |

### Les attributs du stéréotype sequence

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| startWith | Valeur de départ                                                   | Integer | 1 |
| incrementBy | Le pas de l'increment                                            | Integer | 1 |
| minValue | Valeur minimum                                                      | Integer | 0 |
| maxValue | Valeur maximum                                                      | Integer | 0 |
| cache | Spécifie comment les numéros de séquence doivent être préalloués et stockés en mémoire pour un accès plus rapide, par défaut on préalloue 1 nombre.| Integer | 1 |                               
| cycle | Si on atteint maxValue ou minValue, on autorise le générateur à boucler | boolean | false |
| hasMaxValue | Indique si le champs possède une valeur maximum                  | boolean | false |
| hasMinValue |  Indique si le champs possède une valeur minimum                 | boolean | false |

### Les attributs du stéréotype CodeLibelleNomenclature

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| code | Valeur                                                                  | Integer | 0 |
| libelle | Libéllé lié a la valeur                                              | Integer | 0 |

### Les stéréotypes liés au type du champs

Il ne seront utilisés que pour la génération JPA.
Ils permettent principalement de générer des annotations de [Java bean Validation](http://beanvalidation.org/2.0/spec/)

#### stringTyped

Stéréotype pour les champs de type String.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| canBeEmpty | Indique si le la chaine peut etre vide. Si false, une annotation [NotEmpty](http://beanvalidation.org/2.0/spec/#builtinconstraints-notempty) de Java Bean Validation sera générée| boolean | false |
| pattern | Pattern que doit respecté la chaine de caractère (annotation [Pattern](http://beanvalidation.org/2.0/spec/#builtinconstraints-pattern) de Java Bean Validation) | String | null |
| sizeMin | Taille minimal de la chaine de caractère (l'attribut hasSizeMin doit être mis a true pour que celle ci soit pris en compte)| Integer | 0 |
| hasSizeMin | Indique si l'attribut sizeMin est pris en compte | boolean | false |

#### booleanTyped

Stéréotype pour les champs de type boolean.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| alwaysTrue | Indique si le champs doit etre obligatoirement à true ( annotation [AssertTrue](http://beanvalidation.org/2.0/spec/#builtinconstraints-asserttrue) de Java Bean Validation)| boolean | false |
| alwaysFalse | Indique si le champs doit etre obligatoirement à false ( annotation [AssertFalse](http://beanvalidation.org/2.0/spec/#builtinconstraints-assertfalse) de Java Bean Validation)| bollean | false |

#### dateTyped

Stéréotype pour les champs de type date.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| future | Indique si la date est forcement une date dans le futur (annotation [Future](http://beanvalidation.org/2.0/spec/#builtinconstraints-future) de Java Bean Validation) | boolean | false |
| past | Indique si la date est forcement une date du passé (annotation [Past](http://beanvalidation.org/2.0/spec/#builtinconstraints-past) de Java Bean Validation)| boolean | false |
| futureOrPresent | Indique si la date est forcement une date dans le futur ou présente (annotation [FutureOrPresent](http://beanvalidation.org/2.0/spec/#builtinconstraints-futureorpresent) de Java Bean Validation)| boolean | false |
| pastOrpresent | Indique si la date est forcement une date du passé ou du présent (annotation [PastOrPresent](http://beanvalidation.org/2.0/spec/#builtinconstraints-pastorpresent) de Java Bean Validation)| boolean | false |

#### numericTyped

Stéréotype pour les champs de type numérique.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
|min | Valeur minimale du champ (hasMin doit etre a true pour que l'attribut soit pris en compte) ( annotation [Min](http://beanvalidation.org/2.0/spec/#builtinconstraints-min) de Java Bean Validation)  | Integer | 0 |
|max | Valeur maximale du champ (hasMax doit etre a true pour que l'attribut soit pris en compte) ( annotation [Max](http://beanvalidation.org/2.0/spec/#builtinconstraints-max) de Java Bean Validation)| Integer | 0 |
|negative | Indique si la valeur doit etre négative ( annotation [Negative](http://beanvalidation.org/2.0/spec/#builtinconstraints-negative) de Java Bean Validation)| boolean | false |
|negativeOrZero| Indique si la valeur doit etre négative ou 0 ( annotation [NegativeorZero](http://beanvalidation.org/2.0/spec/#builtinconstraints-negativeorzero) de Java Bean Validation)| boolean | false |
|positive| Indique si la valeur doit etre positive  ( annotation [Positive](http://beanvalidation.org/2.0/spec/#builtinconstraints-positive) de Java Bean Validation)| boolean | false |
| positiveOrZero |Indique si la valeur doit etre positive ou zéro ( annotation [PositiveOrZero](http://beanvalidation.org/2.0/spec/#builtinconstraints-positiveorzero) de Java Bean Validation)| boolean | false |
|digits| indique si l'annotation [Digits](http://beanvalidation.org/2.0/spec/#builtinconstraints-digits) de Java Bean Validation doit être générée| boolean | false |
|digitsInteger | Nombre maximum de chiffres pour le partie entière accepté  | Integer | 0 |
|digitsFraction| Nombre maximum de chiffres pour le partie décimale accepté | Integer | 0 |
|decimalMin | Decimale minimum (hasDecimalMin doit etre a true pour que l'attribut soit pris en compte) (annotation [DecimalMin](http://beanvalidation.org/2.0/spec/#builtinconstraints-decimalmin) de Java Bean Validation)| Real | 0.0 |
|decimalMax| Decimale maximum (hasDecimalMax doit etre a true pour que l'attribut soit pris en compte) (annotation [DecimalMax](http://beanvalidation.org/2.0/spec/#builtinconstraints-decimalmax) de Java Bean Validation)| Real | 0.0 |
|hasMin | Indique si l'attribut min est valué | boolean | false |
|hasMax|Indique si l'attribut max est valué | boolean | false |
|hasDecimalMin| Indique si l'attribut decimalMin est valué | boolean | false |
|hasDecimalMax|Indique si l'attribut decimalMax est valué | boolean | false |

#### collection

Stéréotype pour les champs multivalués.

| attibut   | description                                                        | type | Valeur par défaut |
| --------- | ------------------------------------------------------------------ | ---- |-------------------|
| sizeMin | Taille minimale de la collection  (HasSizeMin doit etre a true pour que celle ci soit pris en compte) (annotation [Size](http://beanvalidation.org/2.0/spec/#builtinconstraints-size) de Java Bean Validation)| Integer | 0 |
| sizeMax |Taille maximale de la collection (HasSizeMax doit etre a true pour que celle ci soit pris en compte)| Integer | 0 |
| hasSizeMin | Indique si sizeMin est pris en comtpe | boolean | false |
| hasSizeMax | Indique si sizeMax est pris en comtpe | boolean | false |