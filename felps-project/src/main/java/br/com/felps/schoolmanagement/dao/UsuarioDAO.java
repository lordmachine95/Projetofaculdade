package br.com.felps.schoolmanagement.dao;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.exception.InvalidUserException;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

public class UsuarioDAO {

    protected EntityManagerFactory entityManagerFactory =
        PersistenceManager.getInstance().getEntityManagerFactory();
    protected EntityManager entityManager = entityManagerFactory.createEntityManager();

    public void persist(Usuario usuario){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Usuario efetuarLogin(String email, String senha) throws InvalidUserException {

        entityManager = entityManagerFactory.createEntityManager();

        Query query = entityManager.createNativeQuery("SELECT t.* FROM TB_USERS t " +
                "WHERE t.EMAIL = ? AND t.SENHA = ?", Usuario.class);

        query.setParameter(1, email);
        query.setParameter(2, senha);

        Usuario usuario = null;

        try {
            usuario = (Usuario) query.getSingleResult();
        } catch (NoResultException ex){
            throw new InvalidUserException("");
        }

        return usuario;

    }

    public List<Usuario> listarTodos() {
        entityManager = entityManagerFactory.createEntityManager();
        List<Usuario> usuarios = entityManager.createQuery("Select u from Usuario u").getResultList();
        entityManager.close();
        return usuarios;
    }

    public void atualizar(Usuario usuario){
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public Usuario obtemUsuarioPorId(String id) {
        entityManager = entityManagerFactory.createEntityManager();
        Usuario usuario = (Usuario) entityManager.createQuery("SELECT u FROM Usuario u WHERE u.id = :id")
                .setParameter("id", id)
                .getSingleResult();

        entityManager.close();
        return usuario;
    }

    public Usuario remover(String idUsuario) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Usuario usuario = entityManager.find(Usuario.class, idUsuario);
        entityManager.remove(usuario);
        entityManager.getTransaction().commit();
        entityManager.close();
        return usuario;
    }
}
