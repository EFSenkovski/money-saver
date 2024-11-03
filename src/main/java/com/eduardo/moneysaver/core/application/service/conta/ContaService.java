package com.eduardo.moneysaver.core.application.service.conta;

import com.eduardo.moneysaver.core.application.controller.contas.dto.ContaResp;
import com.eduardo.moneysaver.core.application.controller.contas.dto.NewContaDto;
import com.eduardo.moneysaver.core.domain.model.Conta;
import com.eduardo.moneysaver.core.domain.model.Movimento;
import com.eduardo.moneysaver.core.domain.model.TipoMovimento;
import com.eduardo.moneysaver.core.domain.model.User;

import java.util.List;

public interface ContaService {
    List<ContaResp> listContas();
    ContaResp createConta(NewContaDto newContaDto);
}
