package org.example.test;

import org.example.entities.Categorie;
import org.example.entities.Commande;
import org.example.entities.LigneCommandeProduit;
import org.example.entities.Produit;
import org.example.service.CategorieService;
import org.example.service.CommandeService;
import org.example.service.LigneCommandeProduitService;
import org.example.service.ProduitService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Arrays;

public class TestGestionStock {

    public static void main(String[] args) {
        // Charger le contexte Spring
        System.out.println("=== Chargement du contexte Spring ===");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // Afficher tous les beans détectés
        System.out.println("\n=== Beans détectés par Spring ===");
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("Nombre total de beans: " + beanNames.length);
        for (String beanName : beanNames) {
            System.out.println("Bean: " + beanName + " - Type: " + context.getBean(beanName).getClass().getName());
        }

        // Récupérer les services
        System.out.println("\n=== Récupération des services ===");
        try {
            CategorieService categorieService = context.getBean(CategorieService.class);
            System.out.println("✓ CategorieService trouvé: " + categorieService);

            ProduitService produitService = context.getBean(ProduitService.class);
            System.out.println("✓ ProduitService trouvé: " + produitService);

            CommandeService commandeService = context.getBean(CommandeService.class);
            System.out.println("✓ CommandeService trouvé: " + commandeService);

            LigneCommandeProduitService ligneService = context.getBean(LigneCommandeProduitService.class);
            System.out.println("✓ LigneCommandeProduitService trouvé: " + ligneService);

            // Continuer avec les tests
            runTests(categorieService, produitService, commandeService, ligneService);

        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération des services:");
            e.printStackTrace();
        }

        // Fermer le contexte
        ((ClassPathXmlApplicationContext) context).close();
    }

    private static void runTests(CategorieService categorieService,
                                 ProduitService produitService,
                                 CommandeService commandeService,
                                 LigneCommandeProduitService ligneService) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Créer des catégories
            System.out.println("\n=== Création des catégories ===");
            Categorie cat1 = new Categorie("ELEC", "Electronique");
            Categorie cat2 = new Categorie("INFO", "Informatique");
            Categorie cat3 = new Categorie("MEUBL", "Meubles");

            categorieService.create(cat1);
            categorieService.create(cat2);
            categorieService.create(cat3);
            System.out.println("Catégories créées avec succès");

            // Créer des produits
            System.out.println("\n=== Création des produits ===");
            Produit p1 = new Produit("TV01", 120.5f, cat1);
            Produit p2 = new Produit("PC01", 150.0f, cat2);
            Produit p3 = new Produit("SOURIS", 25.0f, cat2);
            Produit p4 = new Produit("TABLE", 200.0f, cat3);
            Produit p5 = new Produit("CHAISE", 85.0f, cat3);

            produitService.create(p1);
            produitService.create(p2);
            produitService.create(p3);
            produitService.create(p4);
            produitService.create(p5);
            System.out.println("Produits créés avec succès");

            // Créer une commande du 14 Mars 2013
            System.out.println("\n=== Création de la commande du 14 Mars 2013 ===");
            Date date1 = sdf.parse("14/03/2013");
            Commande cmd1 = new Commande(date1);
            commandeService.create(cmd1);

            // Ajouter des lignes de commande
            LigneCommandeProduit lcp1 = new LigneCommandeProduit(p1, cmd1, 2);
            LigneCommandeProduit lcp2 = new LigneCommandeProduit(p2, cmd1, 1);
            LigneCommandeProduit lcp3 = new LigneCommandeProduit(p3, cmd1, 4);
            LigneCommandeProduit lcp4 = new LigneCommandeProduit(p4, cmd1, 1);
            LigneCommandeProduit lcp5 = new LigneCommandeProduit(p5, cmd1, 5);

            ligneService.create(lcp1);
            ligneService.create(lcp2);
            ligneService.create(lcp3);
            ligneService.create(lcp4);
            ligneService.create(lcp5);
            System.out.println("Commande créée avec 5 produits");

            // Afficher la liste des produits
            System.out.println("\n=== Liste de tous les produits ===");
            List<Produit> produits = produitService.findAll();
            System.out.println("Référence\tQuantité\tPrix");
            for (Produit p : produits) {
                System.out.printf("%s\t\t%d\t\t%.2f DH\n",
                        p.getReference(),
                        p.getLigneCommandeProduits().stream()
                                .filter(lcp -> lcp.getCommande().equals(cmd1))
                                .mapToInt(LigneCommandeProduit::getQuantite)
                                .sum(),
                        p.getPrix());
            }

            // Afficher la liste des produits par catégorie
            System.out.println("\n=== Liste des produits de la catégorie Informatique ===");
            List<Produit> produitsInfo = produitService.findByCategorie(cat2);
            for (Produit p : produitsInfo) {
                System.out.println(p);
            }

            // Afficher les produits commandés entre deux dates
            System.out.println("\n=== Produits commandés entre le 01/03/2013 et le 31/03/2013 ===");
            Date dateDebut = sdf.parse("01/03/2013");
            Date dateFin = sdf.parse("31/03/2013");
            List<Produit> produitsCommandes = commandeService.findProduitsCommandesBetweenDates(dateDebut, dateFin);
            for (Produit p : produitsCommandes) {
                System.out.println(p);
            }

            // Afficher les produits dont le prix est supérieur à 100 DH
            System.out.println("\n=== Produits avec prix > 100 DH ===");
            List<Produit> produitsChers = produitService.findByPrixGreaterThan(100.0f);
            for (Produit p : produitsChers) {
                System.out.printf("%s - %.2f DH\n", p.getReference(), p.getPrix());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}