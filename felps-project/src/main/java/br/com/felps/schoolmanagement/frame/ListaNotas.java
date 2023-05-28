package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListaNotas extends ListaFrame {

    protected JTable tabela;
    protected JScrollPane barraRolagem;
    protected JPanel painelFundo;
    protected JPanel painelBotoes;
    protected JButton btInserir;
    protected JButton btEditar;

    protected Aluno aluno;

    protected DefaultTableModel modelo = new DefaultTableModel();

    protected ListaNotas(String title){
        super(title);
    }
    public ListaNotas(Aluno aluno) {
        super(aluno.getNome() + " - Notas");
        this.aluno = aluno;
        this.inicializar();
    }

    @Override
    protected void inicializar(){
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
        modelo.addColumn(ComponentUtils.TableColumns.MATERIA);
        modelo.addColumn(ComponentUtils.TableColumns.NOME_PROVA);
        modelo.addColumn(ComponentUtils.TableColumns.NOTA);
        pesquisar(modelo, aluno);
    }

    @Override
    protected void inicializarComponentes(){
        btInserir = new JButton("Lançar Nota");
        btEditar = new JButton("Editar Nota");
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

        if (this instanceof ListaNotas){
            painelBotoes.add(btEditar);
        }

        painelFundo.add(BorderLayout.SOUTH, painelBotoes);

        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners() {
        configurarListenerBotaoInserir();
        configurarListenerBotaoEditar();
    }

    private void configurarListenerBotaoInserir(){
        btInserir.addActionListener(a -> {
            LancarNota ln = new LancarNota(modelo, aluno);
            ln.setVisible(true);
        });
    }
    private void configurarListenerBotaoEditar(){
        btEditar.addActionListener(a -> {
            int linhaSelecionada = getLinhaSelecionada();
            if (linhaSelecionada >= 0) {
                String idNota = (String) tabela
                        .getValueAt(linhaSelecionada, 0);

                Nota nota = aluno.getNotas().stream()
                        .filter(n -> n.getId().equals(idNota))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(""));

                EditarNota ea =
                        new EditarNota(modelo, aluno, nota, linhaSelecionada);
                ea.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null,
                        "É necesário selecionar um registro");
            }
        });
    }

    protected int getLinhaSelecionada() {
        int linhaSelecionada = -1;
        linhaSelecionada = tabela.getSelectedRow();
        return linhaSelecionada;
    }

    public static void pesquisar(DefaultTableModel modelo, Aluno aluno) {
        modelo.setNumRows(0);
        aluno.getNotas().forEach(nota -> {
            modelo.addRow(new Object[]{nota.getId(), nota.getMateria(), nota.getNomeProva(), nota.getValor()});
        });
    }

}