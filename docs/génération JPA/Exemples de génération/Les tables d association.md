# Exemple de génération de table d'association


![Table d'association](../../sources/association.png)

Note: Pour changer le nom des attributs présent sur le lien de l'association( ici `attUn` et `attDeux`), il faut cliquer sur le nom puis allez dans la vue `Properties` dans l'onglet `Advanced` et changer la propriété `Name`.

L'association sera alors dans les deux sens:

MaClasse.java:

```java
package fr.gouv.diplomatie;

/*imports*/

@Entity
@Table(name = "ma_classe")
@NoArgsConstructor
@AllArgsConstructor
public class MaClasse  implements Serializable {
	
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    @Id@NotNull
    @Column(name = "id")
    public Integer id;
    
    @NotNull
    @Column(name = "un_attribut")
    public Date unAttribut;
    
    @ManyToMany
    @JoinTable(name="mon_association", joinColumns=@JoinColumn(name="id_att_un", referencedColumnName="id"),
    	inverseJoinColumns=@JoinColumn(name="id_att_deux", referencedColumnName="id"))
    public Set<AutreClasse> attDeux;
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
    
    @Id@NotNull
    @Column(name = "id")
    public Integer id;
    
    @NotNull
    @Column(name = "un_attribut")
    public String unAttribut;
    
    @ManyToMany
    @JoinTable(name="mon_association", joinColumns=@JoinColumn(name="id_att_deux", referencedColumnName="id"),
    	inverseJoinColumns=@JoinColumn(name="id_att_un", referencedColumnName="id"))
    public Set<MaClasse> attUn;
}
```

