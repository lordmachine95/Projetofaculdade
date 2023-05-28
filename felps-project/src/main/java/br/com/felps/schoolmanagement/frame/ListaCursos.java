package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.usecase.curso.RemoveCursoService;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.logging.Logger;

public class ListaCursos extends ListaFrame {

    private JTable tabela;
    private JScrollPane barraRolagem;
    private JPanel painelFundo;
    private JPanel painelBotoes;
    private JButton btInserir;
    private JButton btEditar;
    private JButton btExcluir;
    private JButton btExibirTurmas;
    private DefaultTableModel modelo = new DefaultTableModel();
    private static Logger logger = Logger.getLogger(ListaCursos.class.getName());

    public ListaCursos(Usuario usuario) {
        super("Cursos");
        inicializar();
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
    protected void inicializarTabela(){
        tabela = new JTable(modelo);
        modelo.addColumn(ComponentUtils.TableColumns.ID);
        modelo.addColumn(ComponentUtils.TableColumns.NOME);
        modelo.addColumn(ComponentUtils.TableColumns.TIPO_DIPLOMA);
        this.pesquisar(modelo);
    }
    @Override
    protected void inicializarComponentes(){
        btInserir = new JButton("Cadastrar Curso");
        btEditar = new JButton("Editar Curso");
        btExcluir = new JButton("Excluir Curso");
        btExibirTurmas = new JButton("Exibir Turmas");
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
        painelBotoes.add(btExibirTurmas);

        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners(){
        configurarBotaoEditarListener();
        configurarBotaoExcluirListener();
        configurarBotaoExibirTurmasListener();
        configurarBotaoInserirListener();
    }
    protected void configurarBotaoExcluirListener(){
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
                RemoveCursoService removeCursoService = new RemoveCursoService(this, tabela, linhaSelecionada, modelo);
                removeCursoService.removerCurso();
            }
        });
    }
    protected void configurarBotaoExibirTurmasListener(){
        btExibirTurmas.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idCurso = (String) tabela
                        .getValueAt(linhaSelecionada, 0);
                CursoDAO cursoDAO = new CursoDAO();
                Curso curso = cursoDAO.obterCursoPorId(idCurso);
                ListaTurma lt = new ListaTurmaCurso(curso);
            }
        });
    }
    protected void configurarBotaoInserirListener(){
        btInserir.addActionListener(a -> {
            InsereCurso ic = new InsereCurso(modelo);
            ic.setVisible(true);
        });
    }

    protected void configurarBotaoEditarListener(){
        btEditar.addActionListener(a -> {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
                String idCurso = (String) tabela
                        .getValueAt(linhaSelecionada, 0);
                CursoDAO cursoDAO = new CursoDAO();
                Curso curso = cursoDAO.obterCursoPorId(idCurso);
                EditarCurso ec =
                        new EditarCurso(modelo, curso, linhaSelecionada);

                ec.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "É necesário selecionar um registro");
            }
        });
    }

    public static void pesquisar(DefaultTableModel modelo) {
        modelo.setNumRows(0);

        CursoDAO cursoDAO = new CursoDAO();

        cursoDAO.listarTodos().forEach(curso -> {
                modelo.addRow(new Object[]{curso.getId(), curso.getNome(), curso.getTipoDiploma().getTipoDiploma()});
            });
    }
}
