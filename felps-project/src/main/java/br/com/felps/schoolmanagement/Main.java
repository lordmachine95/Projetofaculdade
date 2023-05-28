package br.com.felps.schoolmanagement;

import br.com.felps.schoolmanagement.config.ApplicationConfiguration;
import br.com.felps.schoolmanagement.frame.Login;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e.getMessage());
        }


        new ApplicationConfiguration();
        new Login();
    }

}