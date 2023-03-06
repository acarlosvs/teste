package br.com.confitec.teste.commons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CoberturaDTO {
    @NotNull
    private Integer cobertura;

    @Min(value = 0, message = "O valor da cobertura não pode ser negativo")
    private BigDecimal valor;
}
