package br.com.felps.schoolmanagement.entity;

import br.com.felps.schoolmanagement.domain.MensalidadeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "TB_MENSALIDADES")
public class Mensalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)    
    private String id;
    private String mes;
    private String ano;
    private BigDecimal valor;
    @Enumerated(EnumType.STRING)
    private MensalidadeStatus status;
}
