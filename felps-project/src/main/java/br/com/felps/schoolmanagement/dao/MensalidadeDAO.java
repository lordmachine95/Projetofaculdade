package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Mensalidade;
import br.com.felps.schoolmanagement.entity.Nota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MensalidadeDAO {

    protected EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    protected EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public Mensalidade persist(Mensalidade mensalidade){
        entityManager.getTransaction().begin();
        entityManager.persist(mensalidade);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return mensalidade;
    }
}
