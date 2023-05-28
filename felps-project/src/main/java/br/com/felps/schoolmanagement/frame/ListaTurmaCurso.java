package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ListaTurmaCurso extends ListaTurma {

    public ListaTurmaCurso(Curso curso){
        super("Curso " + curso.getNome() + " Turmas");
        this.curso = curso;
        this.inicializar();
    }

    @Override
    protected void inicializar() {
        this.inicializarTabela();
        inicializarComponentes();
        configurarPainel();
        configurarListeners();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    protected void inicializarTabela() {
        tabela = new JTable(modelo);
        modelo.addColumn(ComponentUtils.TableColumns.ID);
        modelo.addColumn(ComponentUtils.TableColumns.NUMERO_TURMA);
        modelo.addColumn(ComponentUtils.TableColumns.TURNO_TURMA);
        this.pesquisar(modelo, curso);
    }

    public static void pesquisar(DefaultTableModel modelo, Curso curso) {
        modelo.setNumRows(0);
        curso.getTurmas().forEach(turma -> {
            modelo.addRow(new Object[]{turma.getId(), turma.getNumeroTurma(), turma.getTurno()});
        });
    }
}
