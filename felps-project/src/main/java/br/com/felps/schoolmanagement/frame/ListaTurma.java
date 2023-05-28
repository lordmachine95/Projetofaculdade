package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.TurmaDAO;
import br.com.felps.schoolmanagement.entity.*;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ListaTurma extends ListaFrame {

    protected JTable tabela;
    private JScrollPane barraRolagem;

    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JButton btInserir;
    private JButton btEditar;
    private JButton btExcluir;
    private JButton btExibirAlunos;
    private Usuario usuario;
    protected List<Turma> turmas;
    protected Curso curso;
    protected DefaultTableModel modelo = new DefaultTableModel();

    private static Logger logger = Logger.getLogger(ListaAluno.class.getName());

    public ListaTurma(Usuario usuario) {
        super("Turmas");
        this.usuario = usuario;
        this.inicializar();
    }

    public ListaTurma(String title){
        super(title);
    }

    @Override
    protected void inicializar(){
        inicializarTabela();
        inicializarComponentes();
        configurarPainel();
        configurarListeners();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    protected void inicializarTabela(){
        tabela = new JTable(modelo);
        modelo.addColumn(ComponentUtils.TableColumns.ID);
        modelo.addColumn(ComponentUtils.TableColumns.NUMERO_TURMA);
        modelo.addColumn(ComponentUtils.TableColumns.TURNO_TURMA);
        modelo.addColumn(ComponentUtils.TableColumns.CURSO_TURMA);
        pesquisar(modelo, turmas);
    }

    @Override
    protected void inicializarComponentes(){
        btInserir = new JButton("Cadastrar Turma");
        btEditar = new JButton("Editar Turma");
        btExcluir = new JButton("Excluir Turma");
        btExibirAlunos = new JButton("Exibir Alunos");
    }

    @Override
    protected void configurarPainel(){
        setLayout(new BorderLayout());

        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btEditar);
        painelBotoes.add(btExcluir);
        painelBotoes.add(btExibirAlunos);

        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners() {
        configurarBotaoInserirListener();
        configurarBotaoEditarListener();
        configurarBotaoExcluirListener();
        configurarBotaoExibirAlunosListener();
    }

    private void configurarBotaoInserirListener() {
        btInserir.addActionListener(a -> {
            if (Objects.nonNull(curso)) {
                InsereTurma insereTurma = new InsereTurma(modelo, curso);
                return;
            }
            InsereTurma insereTurma = new InsereTurma(modelo);
        });
    }

    private void configurarBotaoExibirAlunosListener() {
        btExibirAlunos.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idTurma = (String) tabela.getValueAt(linhaSelecionada, 0);
                TurmaDAO turmaDAO = new TurmaDAO();
                Turma turma = turmaDAO.obtemTurmasPorId(idTurma);
                ListaAlunoTurma lat = new ListaAlunoTurma(turma);
            } else {
                JOptionPane.showMessageDialog(this, "É necessário selecionar um registro");
            }
        });
    }

    private void configurarBotaoEditarListener() {
        btEditar.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idTurma = (String) tabela.getValueAt(linhaSelecionada, 0);
                TurmaDAO turmaDAO = new TurmaDAO();
                Turma turma = turmaDAO.obtemTurmasPorId(idTurma);
                EditarTurma lat = new EditarTurma(modelo, turma, linhaSelecionada);
            } else {
                JOptionPane.showMessageDialog(this, "É necessário selecionar um registro");
            }
            ;
        });
    }

    private void configurarBotaoExcluirListener(){
        btExcluir.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {

                int resposta =
                        JOptionPane.showConfirmDialog(null, "Você tem certeza de que quer excluir o registro?",
                                "Excluir registro",
                                JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    String idTurma = (String) tabela.getValueAt(linhaSelecionada, 0);
                    TurmaDAO turmaDAO = new TurmaDAO();
                    Turma turmaRemovida;
                    try {
                        turmaRemovida = turmaDAO.remover(idTurma);
                        JOptionPane.showMessageDialog(null, "Turma " + turmaRemovida.getNumeroTurma() + " removido com sucesso!");
                        modelo.removeRow(linhaSelecionada);
                    }catch (Exception e){
                        logger.log(Level.SEVERE, "Exception: " + e.getClass().getName() + " - " + e.getMessage());
                        ComponentUtils.showError(this, "Ocorreu um erro ao remover a turma: " + e.getMessage());
                    }
                }
            }else {
                JOptionPane.showMessageDialog(this, "É necessário selecionar um registro");
            };
        });
    }

    public static void pesquisar(DefaultTableModel modelo, List<Turma> turmas) {

        modelo.setNumRows(0);

        if (Objects.nonNull(turmas)){
            if (turmas.isEmpty()){
                return;
            }
            turmas.forEach(turma -> {
                modelo.addRow(new Object[]{turma.getId(), turma.getNumeroTurma(), turma.getTurno(), turma.getCurso().getNome()});
            });
            return;
        }
        TurmaDAO turmaDao = new TurmaDAO();

        turmaDao.listarTodos().forEach(turma -> {
            modelo.addRow(new Object[]{turma.getId(), turma.getNumeroTurma(), turma.getTurno(), turma.getCurso().getNome()});
        });
    }
}
