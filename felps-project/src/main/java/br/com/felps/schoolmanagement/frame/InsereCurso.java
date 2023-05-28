package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.usecase.curso.InsereCursoService;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InsereCurso extends InserirFrame {

    private DefaultTableModel modelo = new DefaultTableModel();
    private JLabel lbNome;
    private JTextField txNome;
    private JLabel lbTipoDiploma;
    private JComboBox cbTipoDiploma;
    private JButton btSalvar;
    private JButton btLimpar;

    public InsereCurso(DefaultTableModel md) {
        super("Novo Curso");
        this.modelo = md;
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
        txNome = ComponentUtils.TextFields.getNomeTextField();
        lbTipoDiploma = ComponentUtils.Labels.getTipoDiplomaLabel();
        cbTipoDiploma = ComponentUtils.ComboBoxes.getTipoDiplomaComboBox();
    }

    @Override
    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbNome);
        pane.add(txNome, "wrap");
        pane.add(lbTipoDiploma);
        pane.add(cbTipoDiploma, "wrap");
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
            String selectedTipoDiploma = (String) cbTipoDiploma.getSelectedItem();
            InsereCursoService insereCursoService = new InsereCursoService(this, modelo, this.txNome.getText(), selectedTipoDiploma);
            insereCursoService.inserirCurso();
        });
    }
    private void configurarBotaoLimparListener(){
        btLimpar.addActionListener(a -> {
            txNome.setText("");
        });
    }

    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txNome)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(cbTipoDiploma)){
            ComponentUtils.showError(this, "Campo nome não pode ser vazio.");
            return false;
        }
        return true;
    }
}
