package ma.projet.service;

import ma.projet.entities.Employe;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeTacheService {

    public boolean create(EmployeTache o) {
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

    public boolean delete(EmployeTache o) {
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

    public List<EmployeTache> findAll() {
        Session session = null;
        List<EmployeTache> employeTaches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employeTaches = session.createQuery("from EmployeTache").list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employeTaches;
    }

    // Afficher la liste des tâches planifiées pour un employé
    public List<Tache> findTachesByEmploye(Employe employe) {
        Session session = null;
        List<Tache> taches = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            taches = session.createQuery(
                            "select et.tache from EmployeTache et where et.employe = :employe")
                    .setParameter("employe", employe)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return taches;
    }

    // Afficher la liste des employés qui réalisent une tâche
    public List<Employe> findEmployesByTache(Tache tache) {
        Session session = null;
        List<Employe> employes = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            employes = session.createQuery(
                            "select et.employe from EmployeTache et where et.tache = :tache")
                    .setParameter("tache", tache)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) session.close();
        }
        return employes;
    }
}