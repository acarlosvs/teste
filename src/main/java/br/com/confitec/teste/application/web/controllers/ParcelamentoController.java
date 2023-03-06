package br.com.confitec.teste.application.web.controllers;

import br.com.confitec.teste.commons.dto.request.SeguroDTO;
import br.com.confitec.teste.commons.dto.response.ParcelamentoDTO;
import br.com.confitec.teste.services.ParcelamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/parcelamento")
@RequiredArgsConstructor
public class ParcelamentoController {

    private final ParcelamentoService parcelamentoService;

    @PostMapping
    public ResponseEntity<ParcelamentoDTO> parcelar(@Valid @RequestBody SeguroDTO seguroDTO) {
        return ResponseEntity.ok(parcelamentoService.criarPlanoParcelaemento(seguroDTO));
    }
}
