package br.com.confitec.teste.services.impl;

import br.com.confitec.teste.commons.dto.request.OpcaoParcelamentoDTO;
import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.DadosDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;
import br.com.confitec.teste.services.ParcelamentoService;
import br.com.confitec.teste.commons.exceptions.QuantidadeParcelasDiverventeException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParcelamentoServiceImpl implements ParcelamentoService {

    public ParcelamentoDTO criarPlanoParcelaemento(SeguroDTO seguroDTO) {
        double valorTotalCorberturas = seguroDTO.getListCobertura()
                .stream()
                .mapToDouble(c -> c.getValor().doubleValue())
                .sum();

        List<DadosDTO> dadosDTO = new ArrayList<>();
        seguroDTO.getListOpcaoParcelamento().forEach(opcaoParcelamento ->
            dadosDTO.addAll(criarParcelamento(BigDecimal.valueOf(valorTotalCorberturas), opcaoParcelamento))
        );

        return new ParcelamentoDTO(dadosDTO);
    }

    private List<DadosDTO> criarParcelamento(BigDecimal valorTotalCorberturas, OpcaoParcelamentoDTO opcaoParcelamento) {
        Integer quantidadeMinimaParcelas = opcaoParcelamento.getQuantidadeMinimaParcelas();
        Integer quantidadeMaximaParcelas = opcaoParcelamento.getQuantidadeMaximaParcelas();
        BigDecimal taxaJuros = opcaoParcelamento.getJuros();

        if (quantidadeMaximaParcelas < quantidadeMinimaParcelas)
            throw new QuantidadeParcelasDiverventeException();

        List<DadosDTO> dadosDTO = new ArrayList<>();
        for (int i = quantidadeMinimaParcelas; i <= quantidadeMaximaParcelas; i++) {
            BigDecimal valorMontante = calcularMontante(valorTotalCorberturas, taxaJuros, i);
            BigDecimal valorParcelas = valorMontante.divide(BigDecimal.valueOf(i), RoundingMode.DOWN);
            BigDecimal valorSomaParcelas = valorParcelas.multiply(BigDecimal.valueOf(i));
            BigDecimal valorPrimeiraParcela = valorParcelas.add(valorMontante.subtract(valorSomaParcelas));

            dadosDTO.add(new DadosDTO(i, valorPrimeiraParcela, valorParcelas, valorMontante));
        }

        return dadosDTO;
    }

    private BigDecimal calcularMontante(BigDecimal valorTotal, BigDecimal taxaJuros,Integer parcelas) {
        return valorTotal.multiply(taxaJuros.add(BigDecimal.ONE).pow(parcelas)).setScale(2, RoundingMode.HALF_EVEN);
    }
}
