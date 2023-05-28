package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.entity.Turma;
import br.com.felps.schoolmanagement.usecase.aluno.InsereAlunoTurmaService;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

/*
    Lista alunos de uma turma específica
    reaproveita a estrutura da Lista de Alunos geral
 */
public class ListaAlunoTurma extends ListaAluno {

    protected Turma turma;
    public ListaAlunoTurma(Turma turma){
        super("Turma " + turma.getNumeroTurma() + " Alunos");
        this.turma = turma;
        this.inicializar();
    }

    @Override
    protected void inicializar() {
        inicializarTabela();
        this.inicializarComponentes();
        this.configurarPainel();
        this.configurarListeners();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    protected void inicializarComponentes(){
        btInserir = new JButton("Inserir");
        btExcluir = new JButton("Excluir");
        painelBotoes = new JPanel();
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelProcurar = new JPanel();
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
        pesquisar(modelo, this.turma.getAlunos());
    }

    @Override
    protected void configurarPainel() {
        setLayout(new BorderLayout());
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);
        painelBotoes.add(btInserir);
        painelBotoes.add(btExcluir);
        painelProcurar.setLayout(new MigLayout());
        painelFundo.add(BorderLayout.SOUTH, painelBotoes);
        painelFundo.add(BorderLayout.NORTH, painelProcurar);
        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners() {
        this.configurarListenerBotaoInserir();
        this.configurarListenerBotaoExcluir();
    }

    @Override
    protected void configurarListenerBotaoInserir() {
        btInserir.addActionListener(action -> {
            String cpf = JOptionPane.showInputDialog(this, "Digite CPF do aluno");
            InsereAlunoTurmaService insereAlunoTurmaService = new InsereAlunoTurmaService(this);
            insereAlunoTurmaService.insereAlunoNaTurma(turma, cpf);
            pesquisar(modelo, turma.getAlunos());
        });
    }
}
