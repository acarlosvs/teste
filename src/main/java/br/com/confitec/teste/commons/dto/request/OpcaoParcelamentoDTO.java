package br.com.confitec.teste.commons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpcaoParcelamentoDTO {
    @Min(value = 1, message = "O número de parcelas não pode ser menor que 1")
    private Integer quantidadeMinimaParcelas;

    @Min(value = 1, message = "O número de parcelas não pode ser menor que 1")
    private Integer quantidadeMaximaParcelas;

    @Min(value = 0, message = "O valor do juros não pode ser negativo")
    private BigDecimal juros;
}