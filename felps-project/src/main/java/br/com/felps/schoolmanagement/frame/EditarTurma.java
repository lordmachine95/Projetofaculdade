package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.dao.NotaDAO;
import br.com.felps.schoolmanagement.dao.TurmaDAO;
import br.com.felps.schoolmanagement.domain.TurnoTurma;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.entity.Turma;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class EditarTurma extends EditarFrame {

    private DefaultTableModel modelo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbTurno;
    private JComboBox cbTurno;
    private JLabel lbCurso;
    private JComboBox cbCurso;
    private JLabel lbNumero;
    private JTextField txNumero;
    private Turma turma;
    private int linhaSelecionada;
    public EditarTurma(DefaultTableModel modelo,
                       Turma turma,
                       int linhaSelecionada) {
        super("Editar Turma");
        this.modelo = modelo;
        this.linhaSelecionada = linhaSelecionada;
        this.turma = turma;
        this.inicializar();
    }

    @Override
    protected void inicializar() {
        this.inicializarComponentes();
        this.configurarPainel();
        this.configurarListeners();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbCurso = ComponentUtils.Labels.getTurnoTurmaLabel();
        cbCurso = new JComboBox(new CursoDAO().listarTodos().toArray());
        cbCurso.setEditable(false);
        lbTurno = ComponentUtils.Labels.getTurnoTurmaLabel();
        cbTurno = ComponentUtils.ComboBoxes.getTurnoTurmaComboBox();
        lbNumero = ComponentUtils.Labels.getNumeroTurmaLabel();
        txNumero = ComponentUtils.TextFields.getNumeroTurmaTextField();
        txNumero.setEditable(false);

        txNumero.setText(turma.getNumeroTurma());
    }

    @Override
    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbNumero);
        pane.add(txNumero,"wrap");
        pane.add(lbTurno);
        pane.add(cbTurno, "wrap");
        pane.add(lbCurso);
        pane.add(cbCurso, "wrap");
        pane.add(btLimpar, "span, align center");
        pane.add(btSalvar, "span, align center");
    }

    @Override
    protected void configurarListeners() {
        this.configurarBotaoSalvarListener();
        this.configurarBotaoLimparListener();
    }
    private void configurarBotaoSalvarListener() {
        btSalvar.addActionListener(a -> {
            if (! dadosInformadosSaoValidos()){
                return;
            }
            Curso selectedCurso = (Curso) cbCurso.getSelectedItem();
            turma.setCurso(selectedCurso);
            String strSelectedTurno = (String) cbTurno.getSelectedItem();
            TurnoTurma turnoTurma = TurnoTurma.valueOf(strSelectedTurno.toUpperCase());
            turma.setTurno(turnoTurma);

            TurmaDAO turmaDAO = new TurmaDAO();
            turmaDAO.atualizar(turma);

            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{turma.getId(), turma.getNumeroTurma(), turma.getTurno().getTurno(),
                    turma.getCurso().getNome()});

            setVisible(false);
        });
    }
    private void configurarBotaoLimparListener() {
        btLimpar.addActionListener(a -> {
            txNumero.setText("");
            cbTurno.setSelectedItem(null);
            cbCurso.setSelectedItem(null);
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbTurno)){
            ComponentUtils.showError(this, "Campo turno não pode ser vazio.");
            return false;
        }
        return true;
    }
}
