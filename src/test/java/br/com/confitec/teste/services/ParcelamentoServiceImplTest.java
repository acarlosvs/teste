package br.com.confitec.teste.services;

import br.com.confitec.teste.commons.dto.request.CoberturaDTO;
import br.com.confitec.teste.commons.dto.request.OpcaoParcelamentoDTO;
import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;
import br.com.confitec.teste.commons.exceptions.QuantidadeParcelasDiverventeException;
import br.com.confitec.teste.services.impl.ParcelamentoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ParcelamentoServiceImplTest {

    @InjectMocks
    ParcelamentoServiceImpl parcelamentoService;

    @Test
    void deveRealizarParcelamento() {
        BigDecimal valorCobertura = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_EVEN);
        List<CoberturaDTO> coberturasLista = new ArrayList<>();
        coberturasLista.add(new CoberturaDTO(1, valorCobertura));

        List<OpcaoParcelamentoDTO> opcaoParcelamentoLista = new ArrayList<>();
        opcaoParcelamentoLista.add(new OpcaoParcelamentoDTO(1, 1, BigDecimal.ZERO));

        SeguroDTO seguroDTO = new SeguroDTO(coberturasLista, opcaoParcelamentoLista);

        ParcelamentoDTO parcelamentoDTO = parcelamentoService.criarPlanoParcelaemento(seguroDTO);

        assertEquals(valorCobertura, parcelamentoDTO.getDados().get(0).getValorParcelamentoTotal());
    }

    @Test
    void deveRetonarErroDeValidacaoAoEnviarParcelamento() {
        BigDecimal valorCobertura = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_EVEN);
        List<CoberturaDTO> coberturasLista = new ArrayList<>();
        coberturasLista.add(new CoberturaDTO(1, valorCobertura));

        List<OpcaoParcelamentoDTO> opcaoParcelamentoLista = new ArrayList<>();
        opcaoParcelamentoLista.add(new OpcaoParcelamentoDTO(2, 1, BigDecimal.ZERO));

        SeguroDTO seguroDTO = new SeguroDTO(coberturasLista, opcaoParcelamentoLista);

        assertThrows(QuantidadeParcelasDiverventeException.class, () -> parcelamentoService.criarPlanoParcelaemento(seguroDTO));
    }
}
