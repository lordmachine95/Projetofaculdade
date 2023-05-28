package br.com.felps.schoolmanagement.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class MateriaDAO {

    private EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    private EntityManager entityManager =
            entityManagerFactory.createEntityManager();

}
