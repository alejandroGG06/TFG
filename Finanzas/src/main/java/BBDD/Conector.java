package BBDD;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conector {

    public static final EntityManagerFactory emf;

    static {
        try {
            emf= Persistence.createEntityManagerFactory("FinanzasPU");
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }




}


