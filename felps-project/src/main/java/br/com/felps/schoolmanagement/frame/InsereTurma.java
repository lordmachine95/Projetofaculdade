package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.dao.TurmaDAO;
import br.com.felps.schoolmanagement.domain.TurnoTurma;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Turma;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class InsereTurma extends InserirFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private JLabel lbNumero;
    private JTextField txNumero;
    private JLabel lbTurno;
    private JComboBox cbTurno;
    private JLabel lbCurso;
    private JComboBox cbCurso;
    private JButton btSalvar;
    private JButton btLimpar;
    private TurmaDAO turmaDAO;
    private Curso curso;
    public InsereTurma(DefaultTableModel modelo) {
        super("Nova Turma");
        this.turmaDAO = new TurmaDAO();
        this.modelo = modelo;
        this.inicializar();;
    }

    public InsereTurma(DefaultTableModel modelo, Curso curso) {
        super("Nova Turma " + curso.getNome());
        this.turmaDAO = new TurmaDAO();
        this.modelo = modelo;
        this.curso = curso;
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
        lbNumero = ComponentUtils.Labels.getNumeroTurmaLabel();
        txNumero = ComponentUtils.TextFields.getNumeroTurmaTextField();
        txNumero.setText(getNumeroTurma());
        txNumero.setEditable(false);
        lbTurno = ComponentUtils.Labels.getTurnoTurmaLabel();
        cbTurno = ComponentUtils.ComboBoxes.getTurnoTurmaComboBox();
        lbCurso = ComponentUtils.Labels.getNomeCursoLabel();
        cbCurso = new JComboBox(new CursoDAO().listarTodos().toArray());
        cbCurso.setEditable(true);
    }

    @Override
    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbNumero);
        pane.add(txNumero, "wrap");
        pane.add(lbTurno);
        pane.add(cbTurno, "wrap");
        pane.add(lbCurso);
        if (Objects.nonNull(curso)){
            cbCurso.setSelectedItem(setDefaultCurso(cbCurso, curso));
            cbCurso.setEnabled(false);
        }
        pane.add(cbCurso, "wrap");
        pane.add(btLimpar, "span, align center");
        pane.add(btSalvar, "span, align center");
    }

    @Override
    protected void configurarListeners() {
        this.configurarBotaoSalvarListener();
    }



    private void configurarBotaoSalvarListener() {
        btSalvar.addActionListener(a -> {

            if (! dadosInformadosSaoValidos()){
                return;
            }
            String selectedTurno = (String) cbTurno.getSelectedItem();

            Curso curso = (Curso) cbCurso.getSelectedItem();

            Turma turma = Turma.builder()
                    .turno(TurnoTurma.valueOf(selectedTurno.toUpperCase()))
                    .curso(curso)
                    .numeroTurma(txNumero.getText())
                    .build();

            turmaDAO.persist(turma);

            ListaTurma.pesquisar(modelo, null);

            setVisible(false);
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbTurno)){
            ComponentUtils.showError(this, "Campo turno não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbCurso)){
            ComponentUtils.showError(this, "Campo curso não pode ser vazio.");
            return false;
        }
        return true;
    }
    private Curso setDefaultCurso(JComboBox cbCurso, Curso curso) {
        for (int i = 0; i < cbCurso.getModel().getSize(); i++) {
            Curso currentCurso = (Curso) cbCurso.getModel().getElementAt(i);
            if (currentCurso.getNome().equals(curso.getNome())){
                return currentCurso;
            } else {
                return null;
            }
        }
        return null;
    }

    private String getNumeroTurma() {
        return turmaDAO.getUltimoNumeroTurma();
    }

}
