package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
public class ListaAluno extends ListaFrame {

    protected JTable tabela;

    protected DefaultTableModel modelo = new DefaultTableModel();
    protected JScrollPane barraRolagem;
    protected JPanel painelFundo;
    protected JPanel painelBotoes;
    protected JPanel painelProcurar;
    protected JButton btInserir;
    protected JButton btExcluir;
    protected JButton btEditar;
    protected JButton btNotas;
    private static Logger logger = Logger.getLogger(ListaAluno.class.getName());
    public ListaAluno(){
        super("Alunos");
        this.inicializar();
    }

    /*
        construtor para as classes filhas
     */
    protected ListaAluno(String title) {
        super(title);
    }
    @Override
    protected void inicializar() {
        inicializarTabela();
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
        modelo.addColumn(ComponentUtils.TableColumns.NOME);
        modelo.addColumn(ComponentUtils.TableColumns.RA);
        modelo.addColumn(ComponentUtils.TableColumns.TELEFONE);
        modelo.addColumn(ComponentUtils.TableColumns.DT_NASCIMENTO);
        modelo.addColumn(ComponentUtils.TableColumns.EMAIL);
        pesquisar(modelo);
    }
    @Override
    protected void inicializarComponentes(){
        btInserir = new JButton("Inserir");
        btExcluir = new JButton("Excluir");
        btEditar = new JButton("Editar");
        btNotas = new JButton("Notas");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelProcurar = new JPanel();
    }
    @Override
    protected void configurarPainel() {
        setLayout(new BorderLayout());
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btEditar);
        painelBotoes.add(btExcluir);
        painelBotoes.add(btNotas);
        painelProcurar.setLayout(new MigLayout());
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);
        painelFundo.add(BorderLayout.NORTH, painelProcurar);
        getContentPane().add(painelFundo);
    }
    @Override
    protected void configurarListeners() {
        configurarListenerBotaoEditar();
        configurarListenerBotaoExcluir();
        configurarListenerBotaoInserir();
        configurarListenerBotaoNotas();
    }
    protected void configurarListenerBotaoNotas() {
        btNotas.addActionListener(e -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada < 0){
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
                return;
            }
            String idAluno = (String) tabela.getValueAt(linhaSelecionada, 0);
            AlunoDAO dao = new AlunoDAO();
            Aluno aluno = dao.obtemAlunoPorId(idAluno);
            new ListaNotas(aluno);
        });
    }

    protected void configurarListenerBotaoExcluir() {
        btExcluir.addActionListener(a -> {

            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();

            if (linhaSelecionada < 0){
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
                return;
            }

            int resposta =
                JOptionPane.showConfirmDialog(null, "Você tem certeza de que quer excluir o registro?",
                        "Excluir registro",
                        JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                String idAluno = (String) tabela.getValueAt(linhaSelecionada, 0);
                AlunoDAO dao = new AlunoDAO();
                Aluno alunoRemovido = null;
                try {
                    alunoRemovido = dao.remover(idAluno);
                    JOptionPane.showMessageDialog(null, "Aluno " + alunoRemovido.getNome() + " removido com sucesso!");
                    modelo.removeRow(linhaSelecionada);
                }catch (Exception e){
                    logger.log(Level.SEVERE, "Exception: " + e.getClass().getName() + " - " + e.getMessage());
                    ComponentUtils.showError(this, "Ocorreu um erro ao remover o aluno: " + e.getMessage());
                }
            }
        });
    }

    protected void configurarListenerBotaoEditar() {
        btEditar.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idAluno = (String) tabela
                        .getValueAt(linhaSelecionada, 0);
                EditarAluno ea =
                        new EditarAluno(modelo, idAluno, linhaSelecionada);
                ea.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "É necesário selecionar um registro");
            }
        });
    }

    protected void configurarListenerBotaoInserir() {
        btInserir.addActionListener(a -> {
            InsereAluno ia = new InsereAluno(modelo);
            ia.setVisible(true);
        });
    }

    /*
        método para ser chamado quando a tela de Alunos tiver
        que ser renderizada com TODOS os alunos
     */
    public static void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);
        AlunoDAO dao = new AlunoDAO();
        dao.listarTodos().forEach(a -> {
            modelo.addRow(new Object[]{a.getId(), a.getNome(), a.getRa(), a.getTelefone(),
            a.getDataNascimento(), a.getEmail()});
        });
    }

    /*
        método para ser chamado quando a tela de Alunos tiver
        que ser renderizada com Alunos específicos
     */
    public static void pesquisar(DefaultTableModel modelo, List<Aluno> alunos) {
        modelo.setNumRows(0);
        alunos.forEach(a -> {
            modelo.addRow(new Object[]{a.getId(), a.getNome(), a.getRa(), a.getTelefone(),
                    a.getDataNascimento(), a.getEmail()});
        });
    }
}
