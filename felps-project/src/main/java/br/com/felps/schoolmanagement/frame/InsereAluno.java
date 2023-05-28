package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.dao.TurmaDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Turma;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public class InsereAluno extends InserirFrame {

    private DefaultTableModel modelo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JTextField nomeTextField;
    private JLabel lbRa;
    private JTextField raTextField;
    private JLabel lbTel;
    private JTextField telTextField;
    private JLabel lbDtNasc;
    private JTextField dtNascTextField;
    private JLabel lbEmail;
    private JTextField emailTextField;
    private JLabel lbTurma;
    private JLabel lbCurso;
    private JComboBox cbCursos;
    private JComboBox cbTurmas;


    public InsereAluno(DefaultTableModel md) {
        super("Inserir novo");
        this.inicializar();
        modelo = md;
    }

    @Override
    protected void inicializar() {
        inicializarComponentes();
        configurarPainel();
        configurarListeners();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbNome);
        pane.add(nomeTextField, "wrap");
        pane.add(lbRa);
        pane.add(raTextField, "wrap");
        pane.add(lbTel);
        pane.add(telTextField, "wrap");
        pane.add(lbDtNasc);
        pane.add(dtNascTextField, "wrap");
        pane.add(lbEmail);
        pane.add(emailTextField, "wrap");
        pane.add(lbCurso);
        pane.add(cbCursos, "wrap");
        pane.add(lbTurma);
        pane.add(cbTurmas, "wrap");
        pane.add(btLimpar, "span, align center");
        pane.add(btSalvar, "span, align center");
    }

    @Override
    protected void inicializarComponentes() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");
        lbNome = ComponentUtils.Labels.getNomeLabel();
        nomeTextField = ComponentUtils.TextFields.getNomeTextField();
        lbRa = ComponentUtils.Labels.getRaLabel();
        raTextField = ComponentUtils.TextFields.getRaTextField();
        lbTel = ComponentUtils.Labels.getTelefoneLabel();
        telTextField = ComponentUtils.TextFields.getTelefoneTextField();
        lbDtNasc = ComponentUtils.Labels.getDtNascLabel();
        dtNascTextField = ComponentUtils.TextFields.getDtNascTextField();
        lbEmail = ComponentUtils.Labels.getEmailLabel();
        emailTextField = ComponentUtils.TextFields.getEmailTextField();
        lbCurso = ComponentUtils.Labels.getNomeCursoLabel();
        cbCursos = new JComboBox<>(new CursoDAO().listarTodos().toArray());
        lbTurma = ComponentUtils.Labels.getTurmaLabel();
        Curso cursoSelecionadoPorPadrao = (Curso) cbCursos.getSelectedItem();
        cbTurmas = new JComboBox<>(new TurmaDAO().obtemTurmasPorCurso(cursoSelecionadoPorPadrao.getId()).toArray());
    }

    @Override
    protected void configurarListeners() {
        configurarListenerComboBoxCurso();
        configurarListenerBotaoSalvar();
        configurarListenerBotaoLimpar();
    }

    private void configurarListenerBotaoLimpar() {
        btLimpar.addActionListener(a -> {
            nomeTextField.setText("");
            raTextField.setText("");
            telTextField.setText("");
            dtNascTextField.setText("");
            emailTextField.setText("");
        });
    }

    private void configurarListenerBotaoSalvar() {
        btSalvar.addActionListener(a -> {
            if (!dadosInformadosSaoValidos()) {
                return;
            }
                Aluno aluno = Aluno.builder()
                        .nome(nomeTextField.getText())
                        .ra(raTextField.getText())
                        .telefone(telTextField.getText())
                        .dataNascimento(dtNascTextField.getText())
                        .email(emailTextField.getText())
                        .build();

                AlunoDAO dao = new AlunoDAO();
                dao.salvarAluno(aluno);

                ListaAluno.pesquisar(modelo);

                setVisible(false);
        });
    }

    private void configurarListenerComboBoxCurso() {
        cbCursos.addActionListener(evt -> {
            Curso curso = (Curso) cbCursos.getSelectedItem();
            List<Turma> turmas = new TurmaDAO().obtemTurmasPorCurso(curso.getId());
            cbTurmas.removeAllItems();
            turmas.forEach(t -> cbTurmas.addItem(t));
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(nomeTextField)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(raTextField)){
            ComponentUtils.showError(this, "Campo ra não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(telTextField)){
            ComponentUtils.showError(this, "Campo telefone não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(dtNascTextField)){
            ComponentUtils.showError(this, "Campo data de nascimento não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(emailTextField)){
            ComponentUtils.showError(this, "Campo email não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbCursos)){
            ComponentUtils.showError(this, "Campo curso não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbTurmas)){
            ComponentUtils.showError(this, "Campo turma não pode ser vazio.");
            return false;
        }
        return true;
    }
}
