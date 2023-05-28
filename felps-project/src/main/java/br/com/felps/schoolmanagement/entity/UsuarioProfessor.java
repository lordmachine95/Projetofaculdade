package br.com.felps.schoolmanagement.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Entity
@DiscriminatorValue("PROFESSOR")
@AllArgsConstructor
public class UsuarioProfessor extends Usuario {
}
