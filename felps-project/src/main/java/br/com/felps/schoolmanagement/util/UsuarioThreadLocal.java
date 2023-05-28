package br.com.felps.schoolmanagement.util;

import br.com.felps.schoolmanagement.entity.Usuario;

public class UsuarioThreadLocal {
    public static ThreadLocal<Usuario> current = new ThreadLocal<Usuario>();
}
