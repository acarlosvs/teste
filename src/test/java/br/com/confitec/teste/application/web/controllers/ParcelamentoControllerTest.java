package br.com.confitec.teste.application.web.controllers;

import br.com.confitec.teste.commons.dto.request.CoberturaDTO;
import br.com.confitec.teste.commons.dto.request.OpcaoParcelamentoDTO;
import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.DadosDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;
import br.com.confitec.teste.services.ParcelamentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParcelamentoController.class)
class ParcelamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelamentoService parcelamentoService;

    @Test
    @SneakyThrows
    void deveParcelar() {
        BigDecimal valorCobertura = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_EVEN);
        List<CoberturaDTO> coberturasLista = new ArrayList<>();
        coberturasLista.add(new CoberturaDTO(1, valorCobertura));

        List<OpcaoParcelamentoDTO> opcaoParcelamentoLista = new ArrayList<>();
        opcaoParcelamentoLista.add(new OpcaoParcelamentoDTO(1, 1, BigDecimal.ZERO));

        SeguroDTO seguroDTO = new SeguroDTO(coberturasLista, opcaoParcelamentoLista);

        List<DadosDTO> dados = new ArrayList<>();
        dados.add(new DadosDTO(1, BigDecimal.valueOf(500), BigDecimal.valueOf(500), BigDecimal.valueOf(500)));
        ParcelamentoDTO parcelamentoDTO = new ParcelamentoDTO(dados);

        when(parcelamentoService.criarPlanoParcelaemento(seguroDTO)).thenReturn(parcelamentoDTO);

        mockMvc.perform(post("/parcelamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(seguroDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dados[0].quantidadeParcelas").value(1))
                .andExpect(jsonPath("$.dados[0].valorParcelamentoTotal").value( BigDecimal.valueOf(500)));
    }

    @Test
    @SneakyThrows
    void deveRetornarErroDeValidacao() {
        BigDecimal valorCobertura = BigDecimal.valueOf(50).setScale(2, RoundingMode.HALF_EVEN);
        List<CoberturaDTO> coberturasLista = new ArrayList<>();
        coberturasLista.add(new CoberturaDTO(1, valorCobertura));

        List<OpcaoParcelamentoDTO> opcaoParcelamentoLista = new ArrayList<>();
        opcaoParcelamentoLista.add(new OpcaoParcelamentoDTO(0, 1, BigDecimal.ZERO));

        SeguroDTO seguroDTO = new SeguroDTO(coberturasLista, opcaoParcelamentoLista);

        List<DadosDTO> dados = new ArrayList<>();
        dados.add(new DadosDTO(1, BigDecimal.valueOf(500), BigDecimal.valueOf(500), BigDecimal.valueOf(500)));
        ParcelamentoDTO parcelamentoDTO = new ParcelamentoDTO(dados);

        when(parcelamentoService.criarPlanoParcelaemento(seguroDTO)).thenReturn(parcelamentoDTO);

        mockMvc.perform(post("/parcelamento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(seguroDTO)))
                .andExpect(status().isBadRequest());
    }
}
