package br.com.felps.schoolmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TB_NOTAS")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private double valor;
    private String nomeProva;
    private String materia;
    @ManyToOne
    public Aluno aluno;

}
