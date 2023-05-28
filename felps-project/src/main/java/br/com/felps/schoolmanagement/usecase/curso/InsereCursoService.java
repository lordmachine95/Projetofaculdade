package br.com.felps.schoolmanagement.usecase.curso;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.domain.TipoDiploma;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.frame.ListaCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsereCursoService {

    private String nome;
    private String tipoDiploma;
    private JFrame component;
    private DefaultTableModel modeloTabela;
    private static final Logger LOGGER = Logger.getLogger(InsereCursoService.class.getName());
    public InsereCursoService(JFrame component, DefaultTableModel modeloTabela, String nome, String tipoDiploma) {
        this.component = component;
        this.modeloTabela = modeloTabela;
        this.nome = nome;
        this.tipoDiploma = tipoDiploma;
    }

    public void inserirCurso(){
        CursoDAO cursoDAO;
        try {
            cursoDAO = new CursoDAO();
            Curso curso = Curso.builder()
                    .nome(this.nome)
                    .tipoDiploma(TipoDiploma.valueOf(tipoDiploma.toUpperCase()))
                    .build();
            cursoDAO.persist(curso);
            ListaCursos.pesquisar(modeloTabela);
        } catch (Exception e){
            LOGGER.log(Level.SEVERE, "Erro ao inserir curso: " + e.getMessage());
            JOptionPane.showMessageDialog(component, "Erro ao remover o curso.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
