package br.com.confitec.teste.commons.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeguroDTO {
    @Valid
    @NotEmpty
    private List<CoberturaDTO> listCobertura;

    @Valid
    @NotEmpty
    private List<OpcaoParcelamentoDTO> listOpcaoParcelamento;
}
