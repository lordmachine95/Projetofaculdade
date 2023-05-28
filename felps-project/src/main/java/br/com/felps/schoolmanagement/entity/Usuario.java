package br.com.felps.schoolmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "TB_USERS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="userRole",
        discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("USER")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String email;
    private String dataNascimento;
    private String cpf;
    private String disciplina;
    private String senha;
    private String telefone;

    public boolean isAdministrador() {
        return this.getClass().getAnnotation(DiscriminatorValue.class).value().equals("ADMIN");
    }
    public boolean isProfessor(){
        return this.getClass().getAnnotation(DiscriminatorValue.class).value().equals("PROFESSOR");
    }
    public boolean isUsuarioAluno(){
        return this.getClass().getAnnotation(DiscriminatorValue.class).value().equals("ALUNO");

    }
}
