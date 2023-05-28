package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Usuario;

public class ListaNotasUsuario extends ListaNotas {

    public ListaNotasUsuario(Usuario usuario) {
        super("Minhas Notas");
        this.aluno = obterAlunoPorUsuario(usuario);
        this.inicializar();
    }

    private Aluno obterAlunoPorUsuario(Usuario usuario) {
        AlunoDAO alunoDAO = new AlunoDAO();
        return alunoDAO.obtemAlunoPorCpf(usuario.getCpf());
    }
}
