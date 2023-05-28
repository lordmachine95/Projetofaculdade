package br.com.felps.schoolmanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "TB_ALUNOS")
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String ra;
    @Column(unique = true)
    private String cpf;
    @OneToOne
    private Endereco endereco;
    private String telefone;
    private String dataNascimento;
    private String email;
    @ManyToOne
    private Turma turma;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "aluno", orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();
    @OneToMany(fetch = FetchType.EAGER)
    private List<Mensalidade> mensalidades = new ArrayList<>();

}
