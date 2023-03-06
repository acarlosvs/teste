package br.com.confitec.teste.commons.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelamentoDTO {
    private List<DadosDTO> dados;
}
