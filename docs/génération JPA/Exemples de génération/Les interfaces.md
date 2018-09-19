# Exemple de génération d'interface

![Interface](../../sources/interface.png)

Sera généré de la façon suivante :

MaClasse.java:

```java
package fr.gouv.diplomatie;

/*imports*/

@Inheritance
@Entity
@Table(name = "ma_classe")
@NoArgsConstructor
@AllArgsConstructor
public class MaClasse  implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    @Id
    //@SequenceGenerator(name="ma_classe_id_generator", initialValue=1, allocationSize=1, sequenceName="ma_classe_id_sequence")
    //@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ma_classe_id_generator")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @NotNull
    @Column(name = "id")
    public Integer id;
    
    @NotNull
    @Column(name = "un_attribut")
    public String unAttribut;
    
    @NotNull
    @Column(name = "interface_attribut")
    public String interfaceAttribut;
}
```

