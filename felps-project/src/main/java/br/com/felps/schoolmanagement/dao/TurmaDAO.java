package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Turma;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class TurmaDAO {

    protected EntityManagerFactory entityManagerFactory =
            PersistenceManager.getInstance().getEntityManagerFactory();

    protected EntityManager entityManager =
            entityManagerFactory.createEntityManager();

    public Turma persist(Turma turma){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(turma);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return turma;
    }

    public Turma atualizar(Turma turma){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(turma);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return turma;
    }

    public List<Turma> listarTodos(){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Turma> turmas = entityManager.createQuery("Select t from Turma t").getResultList();
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return turmas;
    }

    public Turma remover(String idTurma){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Turma turma = entityManager.find(Turma.class, idTurma);
        turma.getAlunos().forEach(this::removeAlunoTurmaAssociacao);
        turma.setAlunos(null);
        entityManager.merge(turma);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        entityManager.remove(turma);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        return turma;
    }

    public void removeAlunoTurmaAssociacao(Aluno aluno){
        AlunoDAO alunoDAO = new AlunoDAO();
        aluno.setTurma(null);
        alunoDAO.atualizar(aluno);
    }

    public List<Turma> obtemTurmasPorCurso(String idCurso){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Turma> turmas = entityManager.createQuery("SELECT t from Turma t INNER JOIN t.curso c WHERE c.id = :idCurso")
                .setParameter("idCurso", idCurso)
                .getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return turmas;
    }

    public Turma obtemTurmasPorId(String idTurma) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Turma turma = (Turma) entityManager.createQuery("SELECT t FROM Turma t WHERE t.id = :id")
                        .setParameter("id", idTurma)
                                .getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return turma;
    }

    public String getUltimoNumeroTurma() {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        long ultimoNumeroTurma = (long) entityManager.createNativeQuery("SELECT MAX(CAST(NUMERO AS SIGNED)) FROM TB_TURMA").getSingleResult();
        entityManager.getTransaction().commit();
        entityManager.close();

        ultimoNumeroTurma = ultimoNumeroTurma + 1;

        if (ultimoNumeroTurma >= 1 && ultimoNumeroTurma < 10){
            return "00" + ultimoNumeroTurma;
        }
        else if (ultimoNumeroTurma >= 10 && ultimoNumeroTurma <= 99){
            return "0" + ultimoNumeroTurma;
        }
        else {
            return String.valueOf(ultimoNumeroTurma);
        }
    }
}
