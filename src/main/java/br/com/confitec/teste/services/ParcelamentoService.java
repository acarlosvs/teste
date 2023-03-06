package br.com.confitec.teste.services;

import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;

public interface ParcelamentoService {

    ParcelamentoDTO criarPlanoParcelaemento(SeguroDTO seguroDTO);
}
