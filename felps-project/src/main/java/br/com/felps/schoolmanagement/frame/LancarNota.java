package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.dao.NotaDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LancarNota extends JFrame {

    protected DefaultTableModel modelo = new DefaultTableModel();
    protected JLabel lbMateria;
    protected JTextField txMateria;
    protected JLabel lbNota;
    protected JTextField txNota;
    protected JLabel lbNomeProva;
    protected JTextField txNomeProva;
    protected Usuario usuario;
    protected Aluno aluno;
    protected JButton btSalvar;
    protected JButton btLimpar;

    public LancarNota(DefaultTableModel md, Aluno aluno) {
        super("Lançar Nota");
        this.aluno = aluno;
        this.modelo = md;
        criaJanela();
    }

    public void criaJanela() {
        btSalvar = new JButton("Salvar");
        btLimpar = new JButton("Limpar");

        lbMateria = ComponentUtils.Labels.getMateriaLabel();
        txMateria = ComponentUtils.TextFields.getMateriaTextField();
        lbNota = ComponentUtils.Labels.getNotaLabel();
        txNota = ComponentUtils.TextFields.getNotaTextField();
        lbNomeProva = ComponentUtils.Labels.getProvaLabel();
        txNomeProva = ComponentUtils.TextFields.getProvaTextField();

        Container pane = getContentPane();
        
        pane.setLayout(new MigLayout());
        pane.add(lbMateria);
        pane.add(txMateria, "wrap");
        pane.add(lbNota);
        pane.add(txNota, "wrap");
        pane.add(lbNomeProva);
        pane.add(txNomeProva, "wrap");
        pane.add(btLimpar, "span, align center");
        pane.add(btSalvar, "span, align center");

        
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        btSalvar.addActionListener(a -> {
            Nota nota = Nota.builder()
                    .valor(Double.valueOf(txNota.getText()))
                    .materia(txMateria.getText())
                    .nomeProva(txNomeProva.getText())
                    .build();

            NotaDAO notaDao = new NotaDAO();
            notaDao.salvarNota(nota);

            AlunoDAO alunoDAO = new AlunoDAO();

            aluno.getNotas().add(nota);
            alunoDAO.atualizar(aluno);

            nota.setAluno(aluno);
            notaDao.atualizar(nota);

            ListaNotas.pesquisar(modelo, aluno);

            setVisible(false);
        });

        btLimpar.addActionListener(a -> {
            txMateria.setText("");
            txNota.setText("");
        });
    }
}
