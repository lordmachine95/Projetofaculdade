package br.com.felps.schoolmanagement.usecase.aluno;

import br.com.felps.schoolmanagement.dao.AlunoDAO;
import br.com.felps.schoolmanagement.dao.TurmaDAO;
import br.com.felps.schoolmanagement.entity.Aluno;
import br.com.felps.schoolmanagement.entity.Turma;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InsereAlunoTurmaService {

    private JFrame component;
    private static final Logger LOGGER = Logger.getLogger(InsereAlunoTurmaService.class.getName());
    public InsereAlunoTurmaService(JFrame component) {
        this.component = component;
    }

    public void insereAlunoNaTurma(Turma turma, String cpfAluno){
        if (Objects.isNull(cpfAluno) || StringUtils.isEmpty(cpfAluno)){
            
        }
        AlunoDAO alunoDAO;
        try {
            alunoDAO = new AlunoDAO();
            Aluno aluno = alunoDAO.obtemAlunoPorCpf(cpfAluno);
            if (Objects.nonNull(aluno.getTurma())){
                JOptionPane.showMessageDialog(component, "Esse aluno já está vinculado a uma turma", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                aluno.setTurma(turma);
                alunoDAO.atualizar(aluno);
                JOptionPane.showMessageDialog(component, "Aluno vinculado a turma: " + turma.getNumeroTurma() + " com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e){
            JOptionPane.showMessageDialog(component, "Erro ao vincular aluno na turma", "Erro", JOptionPane.ERROR_MESSAGE);
            LOGGER.log(Level.SEVERE, "Erro ao vincular aluno na turma: " + e.getMessage());
        }
    }
}
