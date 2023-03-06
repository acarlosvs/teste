package br.com.confitec.teste.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DadosDTO {
    private Integer quantidadeParcelas;
    private BigDecimal valorPrimeiraParcela;
    private BigDecimal valorDemaisParcelas;
    private BigDecimal valorParcelamentoTotal;
}
