package br.com.felps.schoolmanagement.usecase.curso;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.frame.ListaCursos;
import br.com.felps.schoolmanagement.frame.ListaTurmaCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemoveCursoService {

    private JFrame component;
    private int linhaSelecionada;
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private static final Logger LOGGER =Logger.getLogger(RemoveCursoService.class.getName());

    public RemoveCursoService(JFrame component, JTable tabela, int linhaSelecionada, DefaultTableModel modeloTabela) {
        this.component = component;
        this.tabela = tabela;
        this.linhaSelecionada = linhaSelecionada;
        this.modeloTabela = modeloTabela;
    }

    public void removerCurso(){
        CursoDAO cursoDAO;
        String idCurso;
        cursoDAO = new CursoDAO();
        idCurso = (String) tabela.getValueAt(linhaSelecionada, 0);
        Curso curso = cursoDAO.obterCursoPorId(idCurso);

        if (! curso.getTurmas().isEmpty()){
            String[] options = { "Ver turmas", "Cancelar" };
            var selection = JOptionPane.showOptionDialog(null, "Erro ao remover curso, pois há turmas vinculadas a ele.", "Falha",
                    0, JOptionPane.ERROR_MESSAGE, null, options, options[0]);
            if (selection == 0){
                new ListaTurmaCurso(curso);
            } else {
                ListaCursos.pesquisar(modeloTabela);
            }
        } else {
            try {
                cursoDAO.remover(curso.getId());
                JOptionPane.showMessageDialog(component, "Curso: " + curso.getNome() + " removido com sucesso");
                modeloTabela.removeRow(linhaSelecionada);
            } catch (Exception e){
                LOGGER.log(Level.SEVERE, "Erro ao remover curso: " + e.getMessage());
                JOptionPane.showMessageDialog(component, "Erro ao remover o curso.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
