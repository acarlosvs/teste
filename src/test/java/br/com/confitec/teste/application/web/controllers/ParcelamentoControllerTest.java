package br.com.confitec.teste.application.web.controllers;

import br.com.confitec.teste.commons.dto.request.CoberturaDTO;
import br.com.confitec.teste.commons.dto.request.OpcaoParcelamentoDTO;
import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.DadosDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;
import br.com.confitec.teste.services.ParcelamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParcelamentoControllerTest {

    @Mock
    private ParcelamentoService parcelamentoService;

    @InjectMocks
    private ParcelamentoController parcelamentoController;

    @Test
    void deveRetornarParcelamento() {
        List<CoberturaDTO> coberturasLista = new ArrayList<>();
        coberturasLista.add(new CoberturaDTO(1, BigDecimal.valueOf(50)));

        List<OpcaoParcelamentoDTO> opcaoParcelamentoLista = new ArrayList<>();
        opcaoParcelamentoLista.add(new OpcaoParcelamentoDTO(1, 1, BigDecimal.ZERO));

        SeguroDTO seguroDTO = new SeguroDTO(coberturasLista, opcaoParcelamentoLista);

        List<DadosDTO> dados = new ArrayList<>();
        dados.add(new DadosDTO(1, BigDecimal.valueOf(500), BigDecimal.valueOf(500), BigDecimal.valueOf(500)));
        ParcelamentoDTO parcelamentoDTO = new ParcelamentoDTO(dados);

        when(parcelamentoService.criarPlanoParcelaemento(seguroDTO)).thenReturn(parcelamentoDTO);

        ResponseEntity<ParcelamentoDTO> actual = parcelamentoController.parcelar(seguroDTO);

        assertEquals(parcelamentoDTO, actual.getBody());
    }
}
