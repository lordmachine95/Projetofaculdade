package br.com.felps.schoolmanagement.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TurnoTurma {

    MANHA("Manha"),
    TARDE("Tarde"),
    NOITE("Noite");

    protected String turno;
}
