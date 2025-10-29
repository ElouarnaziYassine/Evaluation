package ma.projet;

import ma.projet.entities.Employe;
import ma.projet.entities.EmployeTache;
import ma.projet.entities.Projet;
import ma.projet.entities.Tache;
import ma.projet.service.EmployeService;
import ma.projet.service.EmployeTacheService;
import ma.projet.service.ProjetService;
import ma.projet.service.TacheService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Initialisation des services
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeService employeService = new EmployeService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // 1. Créer des projets
            Projet p1 = new Projet("Projet A", dateFormat.parse("01/01/2023"));
            Projet p2 = new Projet("Projet B", dateFormat.parse("15/03/2023"));

            projetService.create(p1);
            projetService.create(p2);
            System.out.println("\n=== Projets créés ===");

            // 2. Créer des employés
            Employe e1 = new Employe("ALAMI", "Ahmed", "0612345678");
            Employe e2 = new Employe("BENNANI", "Fatima", "0623456789");
            Employe e3 = new Employe("IDRISSI", "Mohammed", "0634567890");

            employeService.create(e1);
            employeService.create(e2);
            employeService.create(e3);
            System.out.println("=== Employés créés ===");

            // 3. Créer des tâches pour les projets
            Tache t1 = new Tache("Tache 1", dateFormat.parse("10/01/2023"),
                    dateFormat.parse("20/01/2023"), 500, p1);
            Tache t2 = new Tache("Tache 2", dateFormat.parse("21/01/2023"),
                    dateFormat.parse("31/01/2023"), 1200, p1);
            Tache t3 = new Tache("Tache 3", dateFormat.parse("01/02/2023"),
                    dateFormat.parse("15/02/2023"), 800, p1);
            Tache t4 = new Tache("Tache 4", dateFormat.parse("20/03/2023"),
                    dateFormat.parse("30/03/2023"), 1500, p2);
            Tache t5 = new Tache("Tache 5", dateFormat.parse("01/04/2023"),
                    dateFormat.parse("15/04/2023"), 2000, p2);

            tacheService.create(t1);
            tacheService.create(t2);
            tacheService.create(t3);
            tacheService.create(t4);
            tacheService.create(t5);
            System.out.println("=== Tâches créées ===");

            // 4. Affecter des employés aux tâches
            EmployeTache et1 = new EmployeTache(e1, t1,
                    dateFormat.parse("10/01/2023"), dateFormat.parse("19/01/2023"));
            EmployeTache et2 = new EmployeTache(e2, t1,
                    dateFormat.parse("10/01/2023"), dateFormat.parse("20/01/2023"));
            EmployeTache et3 = new EmployeTache(e1, t2,
                    dateFormat.parse("21/01/2023"), dateFormat.parse("30/01/2023"));
            EmployeTache et4 = new EmployeTache(e3, t3,
                    dateFormat.parse("01/02/2023"), dateFormat.parse("14/02/2023"));
            EmployeTache et5 = new EmployeTache(e2, t4,
                    dateFormat.parse("20/03/2023"), dateFormat.parse("29/03/2023"));
            EmployeTache et6 = new EmployeTache(e3, t5,
                    dateFormat.parse("01/04/2023"), dateFormat.parse("15/04/2023"));

            employeTacheService.create(et1);
            employeTacheService.create(et2);
            employeTacheService.create(et3);
            employeTacheService.create(et4);
            employeTacheService.create(et5);
            employeTacheService.create(et6);
            System.out.println("=== Affectations créées ===\n");

            // 5. Tests des fonctionnalités demandées

            // a) Afficher la liste des tâches réalisées dans le projet "Projet A"
            System.out.println("\n=== Tâches du Projet A ===");
            List<Tache> tachesProjetA = tacheService.findTachesByProjet(p1);
            for (Tache t : tachesProjetA) {
                System.out.println(t);
            }

            // b) Afficher la liste des tâches dont le prix est supérieur à 1000 DH
            System.out.println("\n=== Tâches avec prix > 1000 DH ===");
            List<Tache> tachesPrixSup1000 = tacheService.findTachesPrixSuperieur1000();
            for (Tache t : tachesPrixSup1000) {
                System.out.println(t + " - Prix: " + t.getPrix() + " DH");
            }

            // c) Afficher la liste des tâches réalisées entre le 01/02/2023 et le 31/03/2023
            System.out.println("\n=== Tâches entre 01/02/2023 et 31/03/2023 ===");
            List<Tache> tachesBetweenDates = tacheService.findTachesBetweenDates(
                    dateFormat.parse("01/02/2023"), dateFormat.parse("31/03/2023"));
            for (Tache t : tachesBetweenDates) {
                System.out.println(t);
            }

            // d) Afficher la liste des tâches planifiées pour un employé
            System.out.println("\n=== Tâches planifiées pour " + e1.getNom() + " " + e1.getPrenom() + " ===");
            List<Tache> tachesEmploye1 = employeTacheService.findTachesByEmploye(e1);
            for (Tache t : tachesEmploye1) {
                System.out.println(t);
            }

            // e) Afficher la liste des employés qui réalisent une tâche
            System.out.println("\n=== Employés qui réalisent la Tache 1 ===");
            List<Employe> employesTache1 = employeTacheService.findEmployesByTache(t1);
            for (Employe e : employesTache1) {
                System.out.println(e);
            }

            // Afficher toutes les affectations
            System.out.println("\n=== Toutes les affectations ===");
            List<EmployeTache> allAffectations = employeTacheService.findAll();
            for (EmployeTache et : allAffectations) {
                System.out.println(et);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== Tests terminés ===");
    }
}