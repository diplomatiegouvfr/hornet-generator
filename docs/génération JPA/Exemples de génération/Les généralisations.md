# Exemple de génération de généralisation

![Interface](../../sources/extend.png)

Sera généré de la façon suivante :

MaClasse.java:

```java
package fr.gouv.diplomatie;

/*imports*/

@Entity
@Table(name = "ma_classe")
@NoArgsConstructor
@AllArgsConstructor
public class MaClasse extends AutreClasse  {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    @Id@NotNull
    @Column(name = "id_autre_classe")
    public Integer idAutreClasse;
    
    @NotNull
    @Column(name = "un_attribut")
    public Date unAttribut;
}
```

AutreClasse.java:

```java
package fr.gouv.diplomatie;

/*imports*/

@Entity
@Table(name = "autre_classe")
@NoArgsConstructor
@AllArgsConstructor
public class AutreClasse  implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    @Id
    //@SequenceGenerator(name="autre_classe_id_generator", initialValue=1, allocationSize=1, sequenceName="autre_classe_id_sequence")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="autre_classe_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    public Integer id;
    
    @NotNull
    @Column(name = "un_attribut")
    public String unAttribut;
}
```