package br.com.felps.schoolmanagement.domain;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MensalidadeStatus {

    PAGA("Paga"),
    ATRASO("Atrasada");

    protected String status;
}
