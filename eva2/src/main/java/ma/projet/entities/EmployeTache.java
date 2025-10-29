package ma.projet.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "employetache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTachePK id;

    @ManyToOne
    @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne
    @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    @Column(name = "dateDebutReelle")
    @Temporal(TemporalType.DATE)
    private Date dateDebutReelle;

    @Column(name = "dateFinReelle")
    @Temporal(TemporalType.DATE)
    private Date dateFinReelle;

    // Constructeurs
    public EmployeTache() {
    }

    public EmployeTache(Employe employe, Tache tache, Date dateDebutReelle, Date dateFinReelle) {
        this.id = new EmployeTachePK(employe.getId(), tache.getId());
        this.employe = employe;
        this.tache = tache;
        this.dateDebutReelle = dateDebutReelle;
        this.dateFinReelle = dateFinReelle;
    }

    // Getters et Setters
    public EmployeTachePK getId() {
        return id;
    }

    public void setId(EmployeTachePK id) {
        this.id = id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public Date getDateDebutReelle() {
        return dateDebutReelle;
    }

    public void setDateDebutReelle(Date dateDebutReelle) {
        this.dateDebutReelle = dateDebutReelle;
    }

    public Date getDateFinReelle() {
        return dateFinReelle;
    }

    public void setDateFinReelle(Date dateFinReelle) {
        this.dateFinReelle = dateFinReelle;
    }

    @Override
    public String toString() {
        return "EmployeTache{" +
                "employe=" + employe.getNom() + " " + employe.getPrenom() +
                ", tache=" + tache.getNom() +
                ", dateDebutReelle=" + dateDebutReelle +
                ", dateFinReelle=" + dateFinReelle +
                '}';
    }
}

// Classe pour la clé composite
@Embeddable
class EmployeTachePK implements Serializable {

    @Column(name = "employe_id")
    private int employeId;

    @Column(name = "tache_id")
    private int tacheId;

    public EmployeTachePK() {
    }

    public EmployeTachePK(int employeId, int tacheId) {
        this.employeId = employeId;
        this.tacheId = tacheId;
    }

    public int getEmployeId() {
        return employeId;
    }

    public void setEmployeId(int employeId) {
        this.employeId = employeId;
    }

    public int getTacheId() {
        return tacheId;
    }

    public void setTacheId(int tacheId) {
        this.tacheId = tacheId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeTachePK that = (EmployeTachePK) o;
        return employeId == that.employeId && tacheId == that.tacheId;
    }

    @Override
    public int hashCode() {
        return 31 * employeId + tacheId;
    }
}