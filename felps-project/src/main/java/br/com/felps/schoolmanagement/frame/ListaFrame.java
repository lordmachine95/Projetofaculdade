package br.com.felps.schoolmanagement.frame;

import javax.swing.*;

public abstract class ListaFrame extends JFrame {

    public ListaFrame(String title) {
        super(title);
    }
    protected abstract void inicializar();
    protected abstract void inicializarTabela();
    protected abstract void inicializarComponentes();
    protected abstract void configurarPainel();
    protected abstract void configurarListeners();
}
