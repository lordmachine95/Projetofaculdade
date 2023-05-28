package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class NotaDAO {

    private EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    private EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public Nota salvarNota(Nota nota){
        entityManager.getTransaction().begin();
        entityManager.persist(nota);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return nota;
    }

    public void atualizar(Nota nota){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(nota);
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
