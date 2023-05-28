package br.com.felps.schoolmanagement.config;

import br.com.felps.schoolmanagement.dao.*;
import br.com.felps.schoolmanagement.domain.MensalidadeStatus;
import br.com.felps.schoolmanagement.domain.TipoDiploma;
import br.com.felps.schoolmanagement.domain.TurnoTurma;
import br.com.felps.schoolmanagement.entity.*;

import java.util.List;

public class ApplicationConfiguration {

    protected UsuarioDAO usuarioDAO;
    protected AlunoDAO alunoDAO;
    protected NotaDAO notaDAO;
    protected MensalidadeDAO mensalidadeDAO;
    protected CursoDAO cursoDAO;
    protected TurmaDAO turmaDAO;
    protected EnderecoDAO enderecoDAO;
    public ApplicationConfiguration(){
        this.usuarioDAO = new UsuarioDAO();
        this.alunoDAO = new AlunoDAO();
        this.notaDAO = new NotaDAO();
        this.mensalidadeDAO = new MensalidadeDAO();
        this.cursoDAO = new CursoDAO();
        this.enderecoDAO = new EnderecoDAO();
        this.turmaDAO = new TurmaDAO();
        loadUsers();
    }

    protected void loadUsers(){
        usuarioDAO.persist(UsuarioAdministrador.builder()
                .nome("Administrador")
                .cpf("0002")
                .email("admin@admin.com")
                .dataNascimento("01/01/1990")
                .senha("admin")
                .telefone("11912345678")
                .build()
        );
        usuarioDAO.persist(UsuarioProfessor.builder()
                .nome("Professor")
                .cpf("0001")
                .email("professor@professor.com")
                .dataNascimento("01/01/1990")
                .senha("professor")
                .telefone("11912345678")
                .build()
        );

        usuarioDAO.persist(UsuarioAluno.builder()
                .nome("Aluno")
                .cpf("000")
                .email("aluno@aluno.com")
                .dataNascimento("01/01/1990")
                .senha("aluno")
                .telefone("11912345678")
                .build()
        );

        Nota nota = notaDAO.salvarNota(Nota.builder()
                .valor(5.0)
                .materia("POO")
                .nomeProva("POO Conceitos")
                .build());

        Mensalidade mensalidade = mensalidadeDAO.persist(Mensalidade.builder()
                        .ano("2023")
                        .mes("01")
                        .status(MensalidadeStatus.PAGA)
                        .build());

        Curso curso = cursoDAO.persist(Curso.builder()
                .nome("Análise e Desenvolvimento de Sistemas")
                .tipoDiploma(TipoDiploma.TECNOLOGO)
                .build());

        Turma turma = turmaDAO.persist(Turma.builder()
                .numeroTurma("001")
                .turno(TurnoTurma.MANHA)
                .curso(curso)
                .build());

        Endereco endereco = enderecoDAO.persist(Endereco.builder()
                        .uf("SP")
                        .bairro("Dos Casas")
                        .cidade("São Bernardo do Campo")
                        .logradouro("Avenida Presidente João Café Filho")
                        .numero("000")
                .build());

        Aluno aluno = alunoDAO.persist(Aluno.builder()
                .nome("Aluno Teste")
                .ra("123456789")
                .cpf("000")
                .dataNascimento("08/06/1997")
                .email("aluno@aluno.com")
                .telefone("11999999999")
                .notas(List.of(nota))
                .mensalidades(List.of(mensalidade))
                .endereco(endereco)
                .turma(turma)
                .build());

        nota.setAluno(aluno);
        notaDAO.atualizar(nota);
    }
}
