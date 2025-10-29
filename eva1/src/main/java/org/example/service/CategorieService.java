package org.example.service;

import org.example.dao.IDao;
import org.example.entities.Categorie;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean create(Categorie categorie) {
        try {
            entityManager.persist(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Categorie categorie) {
        try {
            entityManager.merge(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Categorie categorie) {
        try {
            categorie = entityManager.merge(categorie);
            entityManager.remove(categorie);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Categorie findById(int id) {
        try {
            return entityManager.find(Categorie.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Categorie> findAll() {
        try {
            return entityManager.createQuery("SELECT c FROM Categorie c", Categorie.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}