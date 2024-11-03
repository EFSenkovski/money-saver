package com.eduardo.moneysaver.core.application.service.conta;

import com.eduardo.moneysaver.common.CurrentUserProvider;
import com.eduardo.moneysaver.core.application.controller.contas.dto.ContaResp;
import com.eduardo.moneysaver.core.application.controller.contas.dto.NewContaDto;
import com.eduardo.moneysaver.core.domain.model.Conta;
import com.eduardo.moneysaver.core.domain.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContaServiceImpl implements ContaService {

    private final ContaRepository contaRepository;

    private final CurrentUserProvider currentUserProvider;

    @Autowired
    public ContaServiceImpl(ContaRepository contaRepository, CurrentUserProvider currentUserProvider) {
        this.contaRepository = contaRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @Override
    public List<ContaResp> listContas() {
        var user = currentUserProvider.getCurrentUser();
        var contas = this.contaRepository.findAllByUser(user);
        return contas.stream()
                .map(ContaResp::new)
                .collect(Collectors.toList());
    }

    @Override
    public ContaResp createConta(NewContaDto newContaDto) {
        var conta = this.contaRepository.save(Conta.newConta()
                .nome(newContaDto.nome())
                .saldo(newContaDto.saldo())
                .user(this.currentUserProvider.getCurrentUser())
                .build());
        return new ContaResp(conta);
    }


}
