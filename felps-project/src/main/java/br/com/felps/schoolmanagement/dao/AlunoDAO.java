package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class AlunoDAO {

    protected EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    protected EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    protected TurmaDAO turmaDAO;
    public List<Aluno> listarTodos(){
        entityManager = entityManagerFactory.createEntityManager();
        List<Aluno> alunos = entityManager.createQuery("Select a from Aluno a").getResultList();
        entityManager.close();
        return alunos;
    }

    public void salvarAluno(Aluno aluno){
        entityManager.getTransaction().begin();
        entityManager.persist(aluno);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Aluno obtemAlunoPorId(String id) {
        entityManager = entityManagerFactory.createEntityManager();
        Aluno aluno = entityManager.find(Aluno.class, id);
        entityManager.close();
        return aluno;
    }

    public Aluno persist(Aluno aluno){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(aluno);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return aluno;
    }

    public Aluno obtemAlunoPorCpf(String cpf) {
        entityManager = entityManagerFactory.createEntityManager();
        Aluno aluno = (Aluno) entityManager.createQuery("SELECT a FROM Aluno a WHERE a.cpf = :cpf")
                .setParameter("cpf", cpf)
                .getSingleResult();

        entityManager.close();
        return aluno;
    }
    public void atualizar(Aluno aluno) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(aluno);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Aluno remover(String idAluno) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Aluno aluno = entityManager.find(Aluno.class, idAluno);
        entityManager.remove(aluno);
        entityManager.getTransaction().commit();
        entityManager.close();
        return aluno;
    }
}