package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Nota;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.ComponentUtils;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MeusDados extends JFrame {
    protected JLabel lbNome;
    protected JLabel lbEmail;
    protected JLabel lbTelefone;

    protected Usuario usuario;

    public MeusDados(Usuario usuario) {
        super("Meus Dados");
        this.usuario = usuario;
        criaPainel();
    }
    protected void criaPainel() {
        Container pane = getContentPane();
        pane.setLayout(new MigLayout());

        lbNome = ComponentUtils.Labels.getNomeLabel();
        lbEmail = ComponentUtils.Labels.getEmailLabel();
        lbTelefone = ComponentUtils.Labels.getTelefoneLabel();

        pane.add(lbNome);
        lbNome = new JLabel(this.usuario.getNome());
        pane.add(lbNome, "wrap");
        pane.add(lbEmail);
        lbEmail = new JLabel(this.usuario.getEmail());
        pane.add(lbEmail, "wrap");
        pane.add(lbTelefone);
        lbTelefone = new JLabel(this.usuario.getTelefone());
        pane.add(lbTelefone, "wrap");

        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }
}
