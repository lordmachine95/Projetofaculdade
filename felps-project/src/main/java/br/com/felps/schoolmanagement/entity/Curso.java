package br.com.felps.schoolmanagement.entity;

import br.com.felps.schoolmanagement.domain.CategoriaCurso;
import br.com.felps.schoolmanagement.domain.TipoDiploma;
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
@Table(name = "TB_CURSO")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    @Enumerated(EnumType.STRING)
    private CategoriaCurso categoria;
    private TipoDiploma tipoDiploma;
    @OneToMany(mappedBy = "curso", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Turma> turmas = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Materia> materias = new ArrayList<>();

    @Override
    public String toString(){
        return nome + " (" + tipoDiploma.getTipoDiploma() + ")";
    }
}
