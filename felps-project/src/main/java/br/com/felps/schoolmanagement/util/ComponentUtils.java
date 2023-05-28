package br.com.felps.schoolmanagement.util;

import br.com.felps.schoolmanagement.domain.TipoDiploma;
import br.com.felps.schoolmanagement.domain.TurnoTurma;
import br.com.felps.schoolmanagement.entity.Turma;
import lombok.experimental.UtilityClass;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@UtilityClass
public class ComponentUtils {

    public static final String NOME_LABEL_TEXT = "         Nome.:   ";
    public static final int NOME_TEXT_FIELD_SIZE = 30;
    public static final String TELEFONE_LABEL_TEXT = "         Telefone.:   ";
    public static final int TELEFONE_TEXT_FIELD_SIZE = 11;
    public static final String EMAIL_LABEL_TEXT = "         Email.:   ";
    public static final int EMAIL_TEXT_FIELD_SIZE = 20;
    public static final String RA_LABEL_TEXT = "         RA.:   ";
    public static final int RA_TEXT_FIELD_SIZE = 10;
    public static final String DT_NASC_LABEL_TEXT = "     Data de Nascimento.:    ";
    public static final int DT_NASC_TEXT_FIELD_SIZE = 10;
    public static final String ID_LABEL_TEXT = "         Id.:   ";
    public static final int ID_TEXT_FIELD_SIZE = 1;
    public static final String SENHA_LABEL_TEXT = "         Senha.:   ";
    public static final String CONFIRMAR_SENHA_LABEL_TEXT = "         Confirmar Senha.:   ";
    public static final int SENHA_TEXT_FIELD_SIZE = 10;
    public static final String MATERIA_LABEL_TEXT = "         Materia.:   ";
    public static final int MATERIA_TEXT_FIELD_SIZE = 10;
    public static final String NOTA_LABEL_TEXT = "         Nota.:   ";
    public static final int NOTA_TEXT_FIELD_SIZE = 5;
    public static final String NOME_PROVA_LABEL_TEXT = "         Prova.:   ";
    public static final int NOME_PROVA_TEXT_FIELD_SIZE = 15;
    public static final String TIPO_DIPLOMA_LABEL_TEXT = "         Tipo do Diploma.:   ";
    protected static final String NUMERO_TURMA_LABEL_TEXT = "         Numero.:   ";
    protected static final int NUMERO_TURMA_TXT_FIELD_SIZE = 10;
    protected static final String TURNO_TURMA_LABEL_TEXT = "          Turno.:   ";
    protected static final int TURNO_TURMA_TXT_FIELD_SIZE = 10;
    protected static final String NOME_CURSO_LABEL_TEXT = "           Curso.:   ";
    protected static final int CURSO_TXT_FIELD_SIZE = 30;
    protected static final String TURMA_LABEL_TEXT = "           Turma.:   ";

    public static class Labels {


        public static JLabel getNomeLabel(){
            return new JLabel(NOME_LABEL_TEXT);
        }
        public static JLabel getTelefoneLabel(){
            return new JLabel(TELEFONE_LABEL_TEXT);
        }
        public static JLabel getEmailLabel(){
            return new JLabel(EMAIL_LABEL_TEXT);
        }
        public static JLabel getSenhaLabel(){
            return new JLabel(SENHA_LABEL_TEXT);
        }
        public static JLabel getConfirmarSenhaLabel(){
            return new JLabel(CONFIRMAR_SENHA_LABEL_TEXT);
        }
        public static JLabel getRaLabel(){
            return new JLabel(RA_LABEL_TEXT);
        }
        public static JLabel getDtNascLabel(){
            return new JLabel(DT_NASC_LABEL_TEXT);
        }
        public static JLabel getIdLabel(){
            return new JLabel(ID_LABEL_TEXT);
        }
        public static JLabel getMateriaLabel(){return new JLabel(MATERIA_LABEL_TEXT);}
        public static JLabel getNotaLabel(){return new JLabel(NOTA_LABEL_TEXT);}
        public static JLabel getProvaLabel(){return new JLabel(NOME_PROVA_LABEL_TEXT);}
        public static JLabel getTipoDiplomaLabel() {
            return new JLabel(TIPO_DIPLOMA_LABEL_TEXT);
        }
        public static JLabel getNumeroTurmaLabel(){
            return new JLabel(NUMERO_TURMA_LABEL_TEXT);
        }
        public static JLabel getTurnoTurmaLabel(){
            return new JLabel(TURNO_TURMA_LABEL_TEXT);
        }
        public static JLabel getNomeCursoLabel(){
            return new JLabel(NOME_CURSO_LABEL_TEXT);
        }

        public static JLabel getTurmaLabel() {
            return new JLabel(TURMA_LABEL_TEXT);
        }
    }

    public static class TextFields {
        public static JTextField getNomeTextField(){
            return new JTextField(NOME_TEXT_FIELD_SIZE);
        }
        public static JPasswordField getSenhaTextField(){
            return new JPasswordField(SENHA_TEXT_FIELD_SIZE);
        }
        public static JPasswordField getConfirmaSenhaTextField() {
            return new JPasswordField(SENHA_TEXT_FIELD_SIZE);
        }
        public static JTextField getTelefoneTextField(){
            return new JTextField(TELEFONE_TEXT_FIELD_SIZE);
        }
        public static JTextField getDtNascTextField(){
            return new JTextField(DT_NASC_TEXT_FIELD_SIZE);
        }
        public static JTextField getIdTextField(){
            return new JTextField(ID_TEXT_FIELD_SIZE);
        }
        public static JTextField getRaTextField(){
            return new JTextField(RA_TEXT_FIELD_SIZE);
        }
        public static JTextField getEmailTextField(){
            return new JTextField(EMAIL_TEXT_FIELD_SIZE);
        }
        public static JTextField getMateriaTextField(){
            return new JTextField(MATERIA_TEXT_FIELD_SIZE);
        }
        public static JTextField getNotaTextField(){
            return new JTextField(NOTA_TEXT_FIELD_SIZE);
        }
        public static JTextField getProvaTextField(){
            return new JTextField(NOME_PROVA_TEXT_FIELD_SIZE);
        }

        public static JTextField getNumeroTurmaTextField() {
            return new JTextField(NUMERO_TURMA_TXT_FIELD_SIZE);
        }

        public static JTextField getTurnoTurmaTextField() {
            return new JTextField(TURNO_TURMA_TXT_FIELD_SIZE);
        }

        public static JTextField getCursoTextField() {
            return new JTextField(CURSO_TXT_FIELD_SIZE);
        }
    }

    public static class ComboBoxes {
        public static JComboBox getTipoDiplomaComboBox(){
            return new JComboBox(Arrays.asList(TipoDiploma.values())
                    .stream()
                    .map(TipoDiploma::getTipoDiploma)
                    .collect(Collectors.toList())
                    .toArray());
        }

        public static JComboBox getTurnoTurmaComboBox() {
            return new JComboBox(Arrays.asList(TurnoTurma.values())
                    .stream()
                    .map(TurnoTurma::getTurno)
                    .collect(Collectors.toList())
                    .toArray());
        }
    }
    public static class TableColumns {
        public static final String ID = "ID";
        public static final String NOME = "Nome";
        public static final String RA = "RA";
        public static final String TELEFONE = "Telefone";
        public static final String DT_NASCIMENTO = "Data de Nascimento";
        public static final String EMAIL = "Email";
        public static final String DISCIPLINA = "Disciplina";
        public static final String SENHA = "Senha";
        public static final String CPF = "Cpf";
        public static final String MATERIA = "Materia";
        public static final String NOTA = "Nota";
        public static final String NOME_PROVA = "Prova";
        public static final String VALOR_MENSALIDADE = "Valor";
        public static final String MES_MENSALIDADE = "Mes";
        public static final String ANO_MENSALIDADE = "Ano";
        public static final String STATUS_MENSALIDADE = "Status";
        public static final String NUMERO_TURMA = "Numero";
        public static final String CURSO_TURMA = "Curso";
        public static final String TURNO_TURMA = "Turno";
        public static final String TIPO_DIPLOMA = "Diploma";
    }
    public static void showError(Component component, String message){
        JOptionPane.showMessageDialog(component, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    public static boolean valorDoComponenteEhNuloOuVazio(Component component){
        if (component instanceof JTextField){
            var tx = (JTextField) component;
            return "".equals(tx.getText()) || Objects.isNull(tx.getText());
        }
        if (component instanceof JComboBox){
            var cb = (JComboBox) component;
            return Objects.isNull(cb.getSelectedItem());
        }
        return true;
    }
}
