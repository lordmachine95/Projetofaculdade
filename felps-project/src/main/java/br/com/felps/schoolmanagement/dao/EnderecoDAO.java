package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Endereco;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnderecoDAO {

    protected EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    protected EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public Endereco persist(Endereco endereco){
        entityManager.getTransaction().begin();
        entityManager.persist(endereco);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return endereco;
    }
}
