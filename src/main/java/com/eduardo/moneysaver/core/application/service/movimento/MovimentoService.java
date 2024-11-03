package com.eduardo.moneysaver.core.application.service.movimento;

import com.eduardo.moneysaver.core.application.controller.movimentos.dto.MovimentoResp;
import com.eduardo.moneysaver.core.application.controller.movimentos.dto.NewMovimentoDto;

import java.util.List;

public interface MovimentoService {
    List<MovimentoResp> listMovimentos();

    MovimentoResp createMovimento(NewMovimentoDto newMovimentoDto);

    void processarPendentes();
}
