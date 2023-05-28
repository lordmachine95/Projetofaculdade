package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ListaMensalidades extends ListaFrame {

    protected JTable tabela;
    protected JScrollPane barraRolagem;
    protected JPanel painelFundo;
    protected Usuario usuario;
    protected DefaultTableModel modelo = new DefaultTableModel();
    public ListaMensalidades(Usuario usuario) {
        super("Notas");
        this.usuario = usuario;
        inicializarTabela();
        //inicializarComponentes();
        configurarPainel();
        //configurarListeners();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    @Override
    protected void inicializar() {

    }

    protected void inicializarTabela() {
        tabela = new JTable(modelo);
        modelo.addColumn(ComponentUtils.TableColumns.ANO_MENSALIDADE);
        modelo.addColumn(ComponentUtils.TableColumns.MES_MENSALIDADE);
        modelo.addColumn(ComponentUtils.TableColumns.VALOR_MENSALIDADE);
        modelo.addColumn(ComponentUtils.TableColumns.STATUS_MENSALIDADE);
        pesquisar(modelo, usuario);
    }

    @Override
    protected void inicializarComponentes() {

    }

    protected void configurarPainel() {
        setLayout(new BorderLayout());
        barraRolagem = new JScrollPane(tabela);
        painelFundo = new JPanel();
        painelFundo.setLayout(new BorderLayout());
        painelFundo.add(BorderLayout.CENTER, barraRolagem);

        getContentPane().add(painelFundo);
    }

    @Override
    protected void configurarListeners() {

    }

    public static void pesquisar(DefaultTableModel modelo, Usuario usuario) {
        modelo.setNumRows(0);
        AlunoDAO dao = new AlunoDAO();
        dao.obtemAlunoPorCpf(usuario.getCpf()).getMensalidades().forEach(m -> {
            modelo.addRow(new Object[]{m.getAno(), m.getMes(), m.getValor(), m.getStatus().getStatus()});
        });
    }
}
