package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.CursoDAO;
import br.com.felps.schoolmanagement.dao.NotaDAO;
import br.com.felps.schoolmanagement.domain.TipoDiploma;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Curso;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class EditarCurso extends EditarFrame {

    private DefaultTableModel modelo =
            new DefaultTableModel();
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNome;
    private JTextField txNome;
    private JLabel lbTipoDiploma;
    private JComboBox cbTipoDiploma;
    private Curso curso;
    private int linhaSelecionada;
    public EditarCurso(DefaultTableModel modelo, Curso curso, int linhaSelecionada) {
        super("Editar Curso");
        this.modelo = modelo;
        this.curso = curso;
        this.linhaSelecionada = linhaSelecionada;
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
        txNome.setText(curso.getNome());
        cbTipoDiploma.setSelectedItem(curso.getTipoDiploma().getTipoDiploma());
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

            if (!dadosInformadosSaoValidos()) {
                return;
            }
            String selectedTipoDiploma = (String) cbTipoDiploma.getSelectedItem();

            curso.setNome(txNome.getText());
            curso.setTipoDiploma(TipoDiploma.valueOf(selectedTipoDiploma.toUpperCase()));

            CursoDAO cursoDAO = new CursoDAO();
            cursoDAO.atualizar(curso);

            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{curso.getId(), curso.getNome(), curso.getTipoDiploma().getTipoDiploma()});

            setVisible(false);
        });
    }
    private void configurarBotaoLimparListener() {
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
            ComponentUtils.showError(this, "Campo Tipo Diploma não pode ser vazio.");
            return false;
        }

        return true;
    }
}
