package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Mensalidade;
import br.com.felps.schoolmanagement.entity.Turma;
import br.com.felps.schoolmanagement.exception.SQLStatementException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CursoDAO {

    protected EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    protected EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public Curso persist(Curso curso){
        entityManager.getTransaction().begin();
        entityManager.persist(curso);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return curso;
    }


    public List<Curso> listarTodos() {
        entityManager.getTransaction().begin();
        List<Curso> cursos = entityManager.createQuery("Select c from Curso c").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return cursos;
    }

    public Curso obterCursoPorId(String idCurso) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Curso curso = entityManager.find(Curso.class, idCurso);
        entityManager.getTransaction().commit();
        entityManager.close();
        return curso;
    }

    public Curso atualizar(Curso curso) {
        entityManager.getTransaction().begin();
        entityManager.merge(curso);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return curso;
    }

    public Curso remover(String idCurso){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Curso curso = entityManager.find(Curso.class, idCurso);
        entityManager.remove(curso);
        entityManager.getTransaction().commit();
        entityManager.close();
        return curso;
    }

    protected boolean cursoHasTurmas(Curso curso) {
        TurmaDAO turmaDAO = new TurmaDAO();
        return ! turmaDAO.obtemTurmasPorCurso(curso.getId()).isEmpty();
    }
}
