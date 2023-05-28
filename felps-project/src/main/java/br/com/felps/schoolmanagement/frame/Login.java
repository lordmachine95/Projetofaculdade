package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.dao.UsuarioDAO;
import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.exception.InvalidUserException;
import br.com.felps.schoolmanagement.util.UsuarioThreadLocal;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {

    private JLabel loginLabel;
    private JTextField loginTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordTextField;
    private JButton loginButton;
    private UsuarioDAO usuarioDAO;
    public Login(){
        init();
    }

    protected void init() {

        Container pane = getContentPane();
        pane.setLayout(new MigLayout());

        loginTextField = new JTextField(20);
        loginLabel = new JLabel("Login: ");
        passwordTextField = new JPasswordField(20);
        passwordLabel = new JLabel("Senha: ");
        loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            Usuario usuario = null;
            usuarioDAO = new UsuarioDAO();
            try {
                usuario = usuarioDAO.efetuarLogin(loginTextField.getText(), passwordTextField.getText());
                UsuarioThreadLocal.current.set(usuario);
            } catch (InvalidUserException ex) {
                JOptionPane.showMessageDialog(this, "Dados incorretos. Verifique.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dispose();

            Menu menu = new Menu(usuario);

        });

        pane.add(loginLabel);
        pane.add(loginTextField, "wrap");
        pane.add(passwordLabel);
        pane.add(passwordTextField, "wrap");
        pane.add(loginButton, "span, align center");

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
