package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public boolean create(Tache o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean delete(Tache o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public boolean update(Tache o) {
        Session session = null;
        Transaction tx = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Tache findById(int id) {
        Session session = null;
        Tache tache = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tache = (Tache) session.get(Tache.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return tache;
    }

    @Override
    public List<Tache> findAll() {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery("from Tache").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    // Méthode personnalisée : Afficher la liste des tâches réalisées dans un projet
    public List<Tache> findTachesByProjet(Projet projet) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery("from Tache t where t.projet = :projet")
                    .setParameter("projet", projet)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    // Afficher la liste des tâches dont le prix est supérieur à 1000 DH
    public List<Tache> findTachesPrixSuperieur1000() {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery("from Tache t where t.prix > 1000")
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    // Afficher la liste des tâches réalisées entre deux dates
    public List<Tache> findTachesBetweenDates(Date dateDebut, Date dateFin) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery("from Tache t where t.dateDebut >= :dateDebut and t.dateFin <= :dateFin")
                    .setParameter("dateDebut", dateDebut)
                    .setParameter("dateFin", dateFin)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }
}