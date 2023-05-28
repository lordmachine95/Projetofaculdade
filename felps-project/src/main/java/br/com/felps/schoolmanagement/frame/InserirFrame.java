package br.com.felps.schoolmanagement.frame;

import javax.swing.*;

public abstract class InserirFrame extends JFrame {

    public InserirFrame(String title) {
        super(title);
    }

    protected abstract void inicializar();
    protected abstract void inicializarComponentes();
    protected abstract void configurarPainel();
    protected abstract void configurarListeners();
    protected abstract boolean dadosInformadosSaoValidos();
}
