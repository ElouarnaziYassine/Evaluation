package org.example.service;

import org.example.dao.IDao;
import org.example.entities.Categorie;
import org.example.entities.Produit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Produit produit) {
        try {
            entityManager.persist(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit produit) {
        try {
            entityManager.merge(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit produit) {
        try {
            produit = entityManager.merge(produit);
            entityManager.remove(produit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit findById(int id) {
        try {
            return entityManager.find(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> findAll() {
        try {
            return entityManager.createQuery("SELECT p FROM Produit p", Produit.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour afficher la liste des produits par catégorie
    public List<Produit> findByCategorie(Categorie categorie) {
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Produit p WHERE p.categorie = :categorie", Produit.class)
                    .setParameter("categorie", categorie)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Méthode pour afficher les produits dont le prix est supérieur à 100 DH
    public List<Produit> findByPrixGreaterThan(float prix) {
        try {
            return entityManager.createQuery(
                            "SELECT p FROM Produit p WHERE p.prix > :prix", Produit.class)
                    .setParameter("prix", prix)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}