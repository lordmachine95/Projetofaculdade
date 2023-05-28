package br.com.felps.schoolmanagement.frame;

import br.com.felps.schoolmanagement.entity.Usuario;
import br.com.felps.schoolmanagement.util.UsuarioThreadLocal;

import javax.swing.*;

public class Menu extends JFrame {

    protected Usuario usuario;
    protected JMenu menu;
    public Menu(Usuario usuario){
        this.usuario = usuario;
        initUI();
    }

    protected void initUI() {
        createMenuBar();
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    protected void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu alunosMenu = new JMenu("Alunos");
        addAlunosMenuItens(alunosMenu);

        JMenu usuariosMenu = new JMenu("Usuarios");
        addUsuariosMenuItens(usuariosMenu);

        JMenu notasMenu = new JMenu("Notas");
        addNotasMenuItens(notasMenu);

        JMenu cursosMenu = new JMenu("Cursos");
        addCursoMenuItens(cursosMenu);

        JMenu turmasMenu = new JMenu("Turmas");
        addTurmaMenuItens(turmasMenu);

        JMenu mensalidadesMenu = new JMenu("Mensalidades");
        addMensalidadesMenuItens(mensalidadesMenu);

        if (this.usuario.isAdministrador()){
            menuBar.add(alunosMenu);
            menuBar.add(usuariosMenu);
            menuBar.add(turmasMenu);
            menuBar.add(cursosMenu);

        }

        if (this.usuario.isProfessor()){
            menuBar.add(alunosMenu);
        }

        if (this.usuario.isUsuarioAluno()){
            menuBar.add(notasMenu);
            menuBar.add(mensalidadesMenu);
        }

        setJMenuBar(menuBar);
    }

    protected void addTurmaMenuItens(JMenu turmasMenu) {
        JMenuItem gerenciarTurmasMenuItem = new JMenuItem("Gerenciar");
        gerenciarTurmasMenuItem.setToolTipText("Gerenciar Turmas");
        gerenciarTurmasMenuItem.addActionListener(e -> {
            ListaTurma listarTurmas = new ListaTurma(this.usuario);
        });
        turmasMenu.add(gerenciarTurmasMenuItem);
    }

    protected void addCursoMenuItens(JMenu cursosMenu) {
        JMenuItem cursosMenuItem = new JMenuItem("Gerenciar");
        cursosMenuItem.setToolTipText("Gerenciar Cursos");
        cursosMenuItem.addActionListener(e -> {
            ListaCursos lc = new ListaCursos(this.usuario);
        });
        cursosMenu.add(cursosMenuItem);
    }

    protected void addMensalidadesMenuItens(JMenu mensalidadesMenu) {
        JMenuItem mensalidadesMenuItem = new JMenuItem("Mensalidades");
        mensalidadesMenuItem.setToolTipText("Gerenciar minhas Mensalidades");
        mensalidadesMenuItem.addActionListener(e -> {
            ListaMensalidades lm = new ListaMensalidades(this.usuario);
        });
        mensalidadesMenu.add(mensalidadesMenuItem);
    }

    protected void addNotasMenuItens(JMenu notasMenu) {
        JMenuItem minhasNotasMenuItem = new JMenuItem("Minhas Notas");
        minhasNotasMenuItem.setToolTipText("Gerenciar minhas Notas");
        minhasNotasMenuItem.addActionListener(e -> {
            ListaNotas ln = new ListaNotasUsuario(UsuarioThreadLocal.current.get());
        });
        notasMenu.add(minhasNotasMenuItem);
    }


    protected void addUsuariosMenuItens(JMenu usuariosMenu) {
        JMenuItem adicionarMenuItem = new JMenuItem("Gerenciar");
        adicionarMenuItem.setToolTipText("Gerenciar Usuários");
        adicionarMenuItem.addActionListener((event) -> {
            ListaUsuario la = new ListaUsuario();
        });
        usuariosMenu.add(adicionarMenuItem);
    }

    protected void addAlunosMenuItens(JMenu alunosMenu) {
        JMenuItem adicionarMenuItem = new JMenuItem("Gerenciar");
        adicionarMenuItem.setToolTipText("Gerenciar Alunos");
        adicionarMenuItem.addActionListener((event) -> {
            ListaAluno la = new ListaAluno();
        });
        alunosMenu.add(adicionarMenuItem);
    }

}
