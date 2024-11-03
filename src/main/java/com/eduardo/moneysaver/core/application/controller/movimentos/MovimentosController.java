package com.eduardo.moneysaver.core.application.controller.movimentos;

import com.eduardo.moneysaver.core.application.controller.movimentos.dto.MovimentoResp;
import com.eduardo.moneysaver.core.application.controller.movimentos.dto.NewMovimentoDto;
import com.eduardo.moneysaver.core.application.service.movimento.MovimentoService;
import com.eduardo.moneysaver.core.domain.model.Movimento;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Controller
@RequestMapping("/movimentos")
public class MovimentosController {

    private final MovimentoService movimentoService;

    @Autowired
    public MovimentosController(MovimentoService movimentoService) {
        this.movimentoService = movimentoService;
    }

    @GetMapping
    public ResponseEntity<List<MovimentoResp>> listMovimentos() {
        return ResponseEntity.ok(this.movimentoService.listMovimentos());
    }

    @PostMapping
    public ResponseEntity<MovimentoResp> createMovimento(@RequestBody @Valid NewMovimentoDto newMovimentoDto, UriComponentsBuilder builder) {
        var movimento = this.movimentoService.createMovimento(newMovimentoDto);
        var uri = builder.path("/movimentos/{id}").buildAndExpand(movimento.id()).toUri();
        return ResponseEntity.created(uri).body(movimento);
    }
}
