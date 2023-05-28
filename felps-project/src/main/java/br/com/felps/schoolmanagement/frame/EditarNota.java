package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.NotaDAO;
import br.com.felps.schoolmanagement.dao.UsuarioDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class EditarNota extends EditarFrame {

    private DefaultTableModel modelo =
            new DefaultTableModel();
    private JPanel pane;
    private JButton btSalvar;
    private JButton btLimpar;
    private JLabel lbNota;
    private JLabel lbMateria;
    private JLabel lbProva;
    private JTextField txNota;
    private JTextField txMateria;
    private JTextField txProva;
    private Aluno aluno;
    private Nota nota;
    private int linhaSelecionada;
    public EditarNota(DefaultTableModel modelo,
                      Aluno aluno, Nota nota, int linhaSelecionada) {
        super("Editar Nota");
        this.modelo = modelo;
        this.nota = nota;
        this.aluno = aluno;
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
        lbMateria = ComponentUtils.Labels.getMateriaLabel();
        txMateria = ComponentUtils.TextFields.getMateriaTextField();
        lbNota = ComponentUtils.Labels.getNotaLabel();
        txNota = ComponentUtils.TextFields.getNotaTextField();
        lbProva = ComponentUtils.Labels.getProvaLabel();
        txProva = ComponentUtils.TextFields.getProvaTextField();

        txNota.setText(String.valueOf(nota.getValor()));
        txMateria.setText(nota.getMateria());
        txProva.setText(nota.getNomeProva());
    }

    @Override
    protected void configurarPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());
        pane.add(lbMateria);
        pane.add(txMateria, "wrap");
        pane.add(lbNota);
        pane.add(txNota, "wrap");
        pane.add(lbProva);
        pane.add(txProva, "wrap");
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
            nota.setMateria(txMateria.getText());
            nota.setValor(Double.valueOf(txNota.getText()));
            nota.setNomeProva(txProva.getText());

            NotaDAO notaDAO = new NotaDAO();
            notaDAO.atualizar(nota);

            modelo.removeRow(linhaSelecionada);
            modelo.addRow(new Object[]{nota.getId(), nota.getMateria(), nota.getNomeProva(),
                    nota.getValor()});

            setVisible(false);
        });
    }
    private void configurarBotaoLimparListener() {
        btLimpar.addActionListener(a -> {
            txMateria.setText("");
            txNota.setText("");
            txProva.setText("");
        });
    }
    @Override
    protected boolean dadosInformadosSaoValidos() {
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txNota)){
            ComponentUtils.showError(this, "Campo nota não pode ser vazio.");
            return false;
        }

        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txMateria)){
            ComponentUtils.showError(this, "Campo Materia não pode ser vazio.");
            return false;
        }
        if (ComponentUtils.valorDoComponenteEhNuloOuVazio(txProva)){
            ComponentUtils.showError(this, "Campo Prova não pode ser vazio.");
            return false;
        }
        return true;
    }
}
