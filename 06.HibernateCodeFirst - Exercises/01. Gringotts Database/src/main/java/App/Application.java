package App;

import App.Entities.WizardDeposits;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class Application {
    public static void main(String[] args) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("wizard_deposits")
                .createEntityManager();

        entityManager.getTransaction().begin();

        WizardDeposits wizardDeposits = new WizardDeposits("Pesho", "Peshev", 25);
        entityManager.persist(wizardDeposits);

        entityManager.getTransaction().commit();

    }
}
