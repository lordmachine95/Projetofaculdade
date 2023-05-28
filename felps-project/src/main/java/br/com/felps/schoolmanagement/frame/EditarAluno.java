package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EditarAluno extends EditarFrame {

    private DefaultTableModel modelo =
            new DefaultTableModel();
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JLabel lbTelefone;
    private JLabel lbEmail;
    private JLabel lbId;
    private JLabel lbRa;
    private JLabel lbDtNasc;
    private JTextField txNome;
    private JTextField txId;
    private JTextField txTelefone;
    private JTextField txEmail;
    private JTextField txRa;
    private JTextField txDtNasc;
    private Aluno aluno;
    private int linhaSelecionada;
    private String idAluno;
    public EditarAluno(DefaultTableModel modelo,
                       String idAluno, int linhaSelecionada) {
        super("Editar Aluno");
        this.modelo = modelo;
        this.linhaSelecionada = linhaSelecionada;
        this.idAluno = idAluno;
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
        lbNome = ComponentUtils.Labels.getNomeLabel();
        lbTelefone = ComponentUtils.Labels.getTelefoneLabel();
        lbEmail = ComponentUtils.Labels.getEmailLabel();
        lbId = ComponentUtils.Labels.getIdLabel();
        lbRa = ComponentUtils.Labels.getRaLabel();
        lbDtNasc = ComponentUtils.Labels.getDtNascLabel();
        txNome = ComponentUtils.TextFields.getNomeTextField();
        txTelefone = ComponentUtils.TextFields.getTelefoneTextField();
        txEmail = ComponentUtils.TextFields.getEmailTextField();
        txRa = ComponentUtils.TextFields.getRaTextField();
        txDtNasc = ComponentUtils.TextFields.getDtNascTextField();
        txId = ComponentUtils.TextFields.getIdTextField();
        txId.setEditable(false);

        AlunoDAO dao = new AlunoDAO();
        aluno = dao.obtemAlunoPorId(idAluno);
        txId.setText(aluno.getId().toString());
        txNome.setText(aluno.getNome());
        txTelefone.setText(aluno.getTelefone());
        txEmail.setText(aluno.getEmail());
        txRa.setText(aluno.getRa());
        txDtNasc.setText(aluno.getDataNascimento());
    }

    @Override
    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbId);
        pane.add(txId, "wrap");
        pane.add(lbNome);
        pane.add(txNome, "wrap");
        pane.add(lbTelefone);
        pane.add(txTelefone, "wrap");
        pane.add(lbRa);
        pane.add(txRa, "wrap");
        pane.add(lbDtNasc);
        pane.add(txDtNasc, "wrap");
        pane.add(lbEmail);
        pane.add(txEmail, "wrap");
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
            if (!dadosInformadosSaoValidos()){
                return;
            }
            Aluno aluno = new Aluno();
            aluno.setId(txId.getText());
            aluno.setNome(txNome.getText());
            aluno.setTelefone(txTelefone.getText());
            aluno.setEmail(txEmail.getText());
            aluno.setDataNascimento(txDtNasc.getText());
            aluno.setRa(txRa.getText());

            AlunoDAO dao = new AlunoDAO();

            dao.atualizar(aluno);

            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{aluno.getId(), aluno.getNome(), aluno.getRa(),
                    aluno.getTelefone(), aluno.getDataNascimento(), aluno.getEmail()});
            setVisible(false);
        });
    }

    private void configurarBotaoLimparListener() {
        btLimpar.addActionListener(a -> {
            txNome.setText("");
            txTelefone.setText("");
            txEmail.setText("");
            txDtNasc.setText("");
            txRa.setText("");
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txNome)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txEmail)){
            ComponentUtils.showError(this, "Campo email não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txDtNasc)){
            ComponentUtils.showError(this, "Campo data de nascimento não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txRa)){
            ComponentUtils.showError(this, "Campo RA não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txTelefone)){
            ComponentUtils.showError(this, "Campo telefone não pode ser vazio.");
            return false;
        }
        return true;
    }
}
