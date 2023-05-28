package br.com.felps.schoolmanagement.entity;

import br.com.felps.schoolmanagement.domain.TurnoTurma;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "TB_TURMA")
public class Turma {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(unique = true, name = "NUMERO")
    private String numeroTurma;
    @Enumerated(EnumType.STRING)
    private TurnoTurma turno;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "turma")
    private List<Aluno> alunos = new ArrayList<>();
    @ManyToOne
    private Curso curso;
    @Override
    public String toString() {
        return numeroTurma + " (" + turno.getTurno() +") - " + curso.getNome();
    }
}
