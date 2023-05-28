package br.com.felps.schoolmanagement.frame;

import javax.swing.*;
import java.awt.*;

public abstract class EditarFrame extends JFrame {

    public EditarFrame(String title){
        super(title);
    }

    protected abstract void inicializar();
    protected abstract void inicializarComponentes();
    protected abstract void configurarPainel();
    protected abstract void configurarListeners();
    protected abstract boolean dadosInformadosSaoValidos();


}
