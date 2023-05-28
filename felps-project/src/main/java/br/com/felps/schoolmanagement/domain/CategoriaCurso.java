package br.com.felps.schoolmanagement.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum CategoriaCurso {

    TECNOLOGIA("Tecnologia"),
    ADMIN_ECO_NEG("Administração, Economia e Negócios"),
    CIENCIAS_SOCIAIS("Ciencias Sociais"),
    COMUNICACAO_E_MIDIA("Comunicação e Mídia"),
    DIREITO("Direito"),
    EDUCACAO("Educação"),
    SAUDE("Saúde"),
    ENGENHARIA_E_ARQUITETURA("Engenharia e Arquitetura");

    private String categoria;

}
