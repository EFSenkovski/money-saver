package com.eduardo.moneysaver.core.application.service.movimento;

import com.eduardo.moneysaver.common.CurrentUserProvider;
import com.eduardo.moneysaver.core.application.controller.movimentos.dto.MovimentoResp;
import com.eduardo.moneysaver.core.application.controller.movimentos.dto.NewMovimentoDto;
import com.eduardo.moneysaver.core.domain.model.Movimento;
import com.eduardo.moneysaver.core.domain.model.StatusMovimento;
import com.eduardo.moneysaver.core.domain.model.TipoMovimento;
import com.eduardo.moneysaver.core.domain.repository.ContaRepository;
import com.eduardo.moneysaver.core.domain.repository.MovimentoRepository;
import com.eduardo.moneysaver.core.domain.repository.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimentoServiceImpl implements MovimentoService {

    private final MovimentoRepository movimentoRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ContaRepository contaRepository;
    private final TagRepository tagRepository;

    @Autowired
    public MovimentoServiceImpl(MovimentoRepository movimentoRepository, CurrentUserProvider currentUserProvider, ContaRepository contaRepository, TagRepository tagRepository) {
        this.movimentoRepository = movimentoRepository;
        this.currentUserProvider = currentUserProvider;
        this.contaRepository = contaRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<MovimentoResp> listMovimentos() {
        return this.movimentoRepository.findAllByUser(this.currentUserProvider.getCurrentUser()).stream()
                .map(MovimentoResp::new)
                .toList();
    }

    @Transactional
    @Override
    public MovimentoResp createMovimento(NewMovimentoDto newMovimentoDto) {
        var user = this.currentUserProvider.getCurrentUser();
        var conta = this.contaRepository.findByIdAndUser(newMovimentoDto.contaId(), this.currentUserProvider.getCurrentUser())
                .orElseThrow(() -> new EntityNotFoundException("Conta fornecida é inválida"));

        var movimento = this.movimentoRepository.save(Movimento.newMovimento()
                .user(user)
                .descricao(newMovimentoDto.descricao())
                .valor(newMovimentoDto.valor())
                .conta(conta)
                .dataEfetivacao(newMovimentoDto.dataEfetivacao())
                .status(newMovimentoDto.dataEfetivacao().equals(LocalDate.now()) ? StatusMovimento.E : StatusMovimento.P)
                .tipo(TipoMovimento.getFrom(newMovimentoDto.tipoMovimento()))
                .tags(newMovimentoDto.tags().stream()
                        .map(aLong -> this.tagRepository.findById(aLong)
                                .orElseThrow(() -> new EntityNotFoundException("Uma ou mais tags fornecidas são inválidas")))
                        .collect(Collectors.toSet()))
                .build());

        if (movimento.getStatus().equals(StatusMovimento.E))
            conta.updateSaldo(movimento.getValor(), movimento.getTipo());

        return new MovimentoResp(movimento);

    }

    @Override
    public void processarPendentes() {
        this.movimentoRepository.findAllByStatus(StatusMovimento.P).stream()
                .filter(movimento -> movimento.getDataEfetivacao().equals(LocalDate.now()))
                .sorted(this::entradasAntesDeSaidas)
                .forEach(Movimento::efetivar);
    }

    private int entradasAntesDeSaidas(Movimento o1, Movimento o2) {
        if (o1.getTipo().equals(TipoMovimento.E) && o2.getTipo().equals(TipoMovimento.S)) {
            return -1;
        } else if (o1.getTipo().equals(o2.getTipo())) {
            return 0;
        } else {
            return 1;
        }
    }
}
