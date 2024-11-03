package com.eduardo.moneysaver.core.application.service.conta;

import com.eduardo.moneysaver.core.application.controller.contas.dto.ContaResp;
import com.eduardo.moneysaver.core.application.controller.contas.dto.NewContaDto;

import java.util.List;

public interface ContaService {
    List<ContaResp> listContas();

    ContaResp createConta(NewContaDto newContaDto);
}
