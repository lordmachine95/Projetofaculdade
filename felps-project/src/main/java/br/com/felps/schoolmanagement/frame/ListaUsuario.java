package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.UsuarioDAO;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ListaUsuario extends ListaFrame {

    protected JTable tabela;
    protected JScrollPane barraRolagem;

    protected JPanel painelFundo;
    protected JPanel painelBotoes;
    protected JButton btInserir;
    protected JButton btExcluir;
    protected JButton btEditar;
    protected DefaultTableModel modelo = new DefaultTableModel();
    protected TableCellRenderer passwordRenderer = new PasswordRenderer();

    public ListaUsuario() {
        super("Usuarios");
        this.inicializar();
    }

    @Override
    protected void inicializar() {
        inicializarTabela();
        inicializarComponentes();
        configurarPainel();
        configurarListeners();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    protected void inicializarTabela() {
        tabela = new JTable(modelo);
        modelo.addColumn(ComponentUtils.TableColumns.ID);
        modelo.addColumn(ComponentUtils.TableColumns.NOME);
        modelo.addColumn(ComponentUtils.TableColumns.DT_NASCIMENTO);
        modelo.addColumn(ComponentUtils.TableColumns.CPF);
        modelo.addColumn(ComponentUtils.TableColumns.DISCIPLINA);
        modelo.addColumn(ComponentUtils.TableColumns.EMAIL);
        modelo.addColumn(ComponentUtils.TableColumns.SENHA);
        modelo.addColumn(ComponentUtils.TableColumns.TELEFONE);

        tabela.getColumnModel().getColumn(6).setCellRenderer(passwordRenderer);

        JPasswordField password = new JPasswordField();
        TableCellEditor editor = new DefaultCellEditor(password);
        tabela.getColumnModel().getColumn(6).setCellEditor(editor);

        pesquisar(modelo);
    }

    @Override
    protected void inicializarComponentes() {
        btInserir = new JButton("Inserir");
        btExcluir = new JButton("Excluir");
        btEditar = new JButton("Editar");
    }

    @Override
    protected void configurarPainel() {
        setLayout(new BorderLayout());
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btEditar);
        painelBotoes.add(btExcluir);
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners() {
        configurarBotaoInserirListener();
        configurarBotaoEditarListener();
        configurarBotaoExcluirListener();
    }

    protected void configurarBotaoInserirListener() {
        btInserir.addActionListener(a -> {
            InsereUsuario iu = new InsereUsuario(modelo);
            iu.setVisible(true);
        });
    }

    protected void configurarBotaoEditarListener() {
        btEditar.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idUsuario = (String) tabela
                        .getValueAt(linhaSelecionada, 0);
                EditarUsuario eu =
                        new EditarUsuario(modelo, idUsuario, linhaSelecionada);
                eu.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "É necesário selecionar um registro");
            }
        });
    }

    protected void configurarBotaoExcluirListener() {
        btExcluir.addActionListener(a -> {

            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada < 0) {
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
                return;
            }

            int resposta =
                    JOptionPane.showConfirmDialog(null, "Você tem certeza de que quer excluir o registro?",
                            "Excluir registro",
                            JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                String idUsuario = (String) tabela.getValueAt(linhaSelecionada, 0);
                UsuarioDAO dao = new UsuarioDAO();
                Usuario usuario = dao.remover(idUsuario);
                JOptionPane.showMessageDialog(null, "Usuário " + usuario.getNome() + " removido com sucesso!");
                modelo.removeRow(linhaSelecionada);
            }
        });
    }

    public static void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        UsuarioDAO dao = new UsuarioDAO();

        dao.listarTodos().forEach(u -> {
            modelo.addRow(new Object[]{u.getId(), u.getNome(), u.getDataNascimento(),
                    u.getCpf(), u.getDisciplina(), u.getEmail(), u.getSenha(),
                    u.getTelefone()});
        });
    }

    static class PasswordRenderer extends DefaultTableCellRenderer
    {
        protected void setValue(Object value)
        {
            setText( "********" );
        }
    }
}
