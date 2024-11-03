package com.eduardo.moneysaver.core.application.controller.contas;

import com.eduardo.moneysaver.core.application.controller.contas.dto.ContaResp;
import com.eduardo.moneysaver.core.application.controller.contas.dto.NewContaDto;
import com.eduardo.moneysaver.core.application.service.conta.ContaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContasController {

    private final ContaService contaService;

    @Autowired
    public ContasController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping
    public ResponseEntity<List<ContaResp>> listContas() {
        return ResponseEntity.ok(this.contaService.listContas());
    }

    @PostMapping
    public ResponseEntity<ContaResp> createConta(@RequestBody @Valid NewContaDto newContaDto, UriComponentsBuilder builder) {
        var conta = this.contaService.createConta(newContaDto);
        var uri = builder.path("/contas/{id}").buildAndExpand(conta.id()).toUri();
        return ResponseEntity.created(uri).body(conta);
    }

}
