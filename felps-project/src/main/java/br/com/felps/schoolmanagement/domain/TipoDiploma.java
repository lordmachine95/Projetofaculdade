package br.com.felps.schoolmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoDiploma {

    BACHARELADO("Bacharelado"),
    TECNOLOGO("Tecnologo");

    protected String tipoDiploma;
}
