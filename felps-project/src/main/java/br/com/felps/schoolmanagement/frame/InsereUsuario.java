package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.UsuarioDAO;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class InsereUsuario extends InserirFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
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
    private JLabel lbSenha;
    private JTextField txSenha;
    private JLabel lbConfirmaSenha;
    private JTextField txConfirmaSenha;
    public InsereUsuario(DefaultTableModel md) {
        super("Inserir novo");
        modelo = md;
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
        nomeTextField = ComponentUtils.TextFields.getNomeTextField();
        lbRa = ComponentUtils.Labels.getRaLabel();
        raTextField = ComponentUtils.TextFields.getRaTextField();
        lbTel = ComponentUtils.Labels.getTelefoneLabel();
        telTextField = ComponentUtils.TextFields.getTelefoneTextField();
        lbDtNasc = ComponentUtils.Labels.getDtNascLabel();
        dtNascTextField = ComponentUtils.TextFields.getDtNascTextField();
        lbEmail = ComponentUtils.Labels.getEmailLabel();
        emailTextField = ComponentUtils.TextFields.getEmailTextField();
        lbSenha = ComponentUtils.Labels.getSenhaLabel();
        txSenha = ComponentUtils.TextFields.getSenhaTextField();
        lbConfirmaSenha = ComponentUtils.Labels.getConfirmarSenhaLabel();
        txConfirmaSenha = ComponentUtils.TextFields.getConfirmaSenhaTextField();
    }

    @Override
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
        pane.add(lbSenha);
        pane.add(txSenha, "wrap");
        pane.add(lbConfirmaSenha);
        pane.add(txConfirmaSenha, "wrap");
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

            if (! dadosInformadosSaoValidos())
                return;

            Usuario usuario = Usuario.builder()
                    .nome(nomeTextField.getText())
                    .telefone(telTextField.getText())
                    .dataNascimento(dtNascTextField.getText())
                    .email(emailTextField.getText())
                    .build();

            UsuarioDAO dao = new UsuarioDAO();
            dao.persist(usuario);

            ListaUsuario.pesquisar(modelo);

            setVisible(false);
        });
    }
    private void configurarBotaoLimparListener() {
        btLimpar.addActionListener(a -> {
            nomeTextField.setText("");
            raTextField.setText("");
            telTextField.setText("");
            dtNascTextField.setText("");
            emailTextField.setText("");
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(nomeTextField)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(raTextField)){
            ComponentUtils.showError(this, "Campo RA não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(telTextField)){
            ComponentUtils.showError(this, "Campo Telefone não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(dtNascTextField)){
            ComponentUtils.showError(this, "Campo Data de Nascimento não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(emailTextField)){
            ComponentUtils.showError(this, "Campo Email não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txSenha)){
            ComponentUtils.showError(this, "Campo Senha não pode ser vazio.");
            return false;
        }

        if (txSenha.getText().length() < 8){
            ComponentUtils.showError(this, "Senha deve ter no mínimo 8 digitos!");
            return false;
        }

        if (! txSenha.getText().matches(".*([a-zA-Z].*[0-9]|[0-9].*[a-zA-Z]).*")){
            ComponentUtils.showError(this, "Senha deve possuir letras e números!");
            return false;
        }

        if (! txSenha.getText().equals(txConfirmaSenha.getText())){
            ComponentUtils.showError(this, "Senhas não conferem!");
            return false;
        }

        return true;
    }
}
