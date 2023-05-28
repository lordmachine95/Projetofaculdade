package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.dao.UsuarioDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class EditarUsuario extends EditarFrame {

    private DefaultTableModel modelo;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JLabel lbTelefone;
    private JLabel lbEmail;
    private JLabel lbId;
    private JLabel lbRa;
    private JLabel lbDtNasc;
    private JLabel lbSenha;
    private JLabel lbConfirmaSenha;
    private JTextField txNome;
    private JTextField txId;
    private JTextField txTelefone;
    private JTextField txEmail;
    private JTextField txDtNasc;
    private JPasswordField txSenha;
    private JPasswordField txConfirmaSenha;

    private Usuario usuario;
    private int linhaSelecionada;
    private String idUsuario;
    public EditarUsuario(DefaultTableModel modelo,
                         String idUsuario, int linhaSelecionada) {
        super("Editar Usuario");
        this.modelo = modelo;
        this.idUsuario = idUsuario;
        this.linhaSelecionada = linhaSelecionada;
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
        lbSenha = ComponentUtils.Labels.getSenhaLabel();
        lbConfirmaSenha = ComponentUtils.Labels.getConfirmarSenhaLabel();
        txNome = ComponentUtils.TextFields.getNomeTextField();
        txTelefone = ComponentUtils.TextFields.getTelefoneTextField();
        txEmail = ComponentUtils.TextFields.getEmailTextField();
        txSenha = ComponentUtils.TextFields.getSenhaTextField();
        txDtNasc = ComponentUtils.TextFields.getDtNascTextField();
        txId = ComponentUtils.TextFields.getIdTextField();
        txSenha = ComponentUtils.TextFields.getSenhaTextField();
        txConfirmaSenha = ComponentUtils.TextFields.getConfirmaSenhaTextField();

        UsuarioDAO dao = new UsuarioDAO();
        this.usuario = dao.obtemUsuarioPorId(this.idUsuario);
        txId.setText(usuario.getId());
        txNome.setText(usuario.getNome());
        txTelefone.setText(usuario.getTelefone());
        txEmail.setText(usuario.getEmail());
        txSenha.setText(usuario.getSenha());
        txDtNasc.setText(usuario.getDataNascimento());
        txId.setEditable(false);
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
        pane.add(lbDtNasc);
        pane.add(txDtNasc, "wrap");
        pane.add(lbEmail);
        pane.add(txEmail, "wrap");
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
            if (!dadosInformadosSaoValidos()) {
                return;
            }
            usuario.setId(txId.getText());
            usuario.setNome(txNome.getText());
            usuario.setTelefone(txTelefone.getText());
            usuario.setEmail(txEmail.getText());
            usuario.setDataNascimento(txDtNasc.getText());
            usuario.setSenha(String.valueOf(txSenha.getPassword()));

            UsuarioDAO dao = new UsuarioDAO();
            dao.atualizar(usuario);

            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{usuario.getId(), usuario.getNome(), usuario.getDataNascimento(),
                    usuario.getCpf(), usuario.getDisciplina(), usuario.getEmail(), usuario.getSenha(), usuario.getTelefone()});

            setVisible(false);
        });
    }

    private void configurarBotaoLimparListener(){
        btLimpar.addActionListener(a -> {
            txNome.setText("");
            txTelefone.setText("");
            txEmail.setText("");
            txDtNasc.setText("");
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txNome)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }

        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txTelefone)){
            ComponentUtils.showError(this, "Campo Telefone não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txDtNasc)){
            ComponentUtils.showError(this, "Campo Data de Nascimento não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txEmail)){
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
