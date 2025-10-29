package org.example.service;

import org.example.dao.IDao;
import org.example.entities.Commande;
import org.example.entities.Produit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Commande commande) {
        try {
            entityManager.persist(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Commande commande) {
        try {
            entityManager.merge(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Commande commande) {
        try {
            commande = entityManager.merge(commande);
            entityManager.remove(commande);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Commande findById(int id) {
        try {
            return entityManager.find(Commande.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Commande> findAll() {
        try {
            return entityManager.createQuery("SELECT c FROM Commande c", Commande.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour afficher les produits commandés entre deux dates
    public List<Produit> findProduitsCommandesBetweenDates(Date dateDebut, Date dateFin) {
        try {
            return entityManager.createQuery(
                            "SELECT DISTINCT lcp.produit FROM LigneCommandeProduit lcp " +
                                    "WHERE lcp.commande.date BETWEEN :dateDebut AND :dateFin",
                            Produit.class)
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour afficher les produits dans une commande donnée
    public List<Produit> findProduitsInCommande(Commande commande) {
        try {
            return entityManager.createQuery(
                            "SELECT lcp.produit FROM LigneCommandeProduit lcp " +
                                    "WHERE lcp.commande = :commande",
                            Produit.class)
                    .setParameter("commande", commande)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}