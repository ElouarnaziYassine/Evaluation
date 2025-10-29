package org.example.service;

import org.example.dao.IDao;
import org.example.entities.LigneCommandeProduit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class LigneCommandeProduitService implements IDao<LigneCommandeProduit> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(LigneCommandeProduit ligneCommandeProduit) {
        try {
            entityManager.persist(ligneCommandeProduit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(LigneCommandeProduit ligneCommandeProduit) {
        try {
            entityManager.merge(ligneCommandeProduit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(LigneCommandeProduit ligneCommandeProduit) {
        try {
            ligneCommandeProduit = entityManager.merge(ligneCommandeProduit);
            entityManager.remove(ligneCommandeProduit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public LigneCommandeProduit findById(int id) {
        try {
            return entityManager.find(LigneCommandeProduit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        try {
            return entityManager.createQuery(
                            "SELECT lcp FROM LigneCommandeProduit lcp",
                            LigneCommandeProduit.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}