package com.eduardo.moneysaver.core.infra.scheduler;

import com.eduardo.moneysaver.core.application.port.MovimentosPendentesProcessorPort;
import com.eduardo.moneysaver.core.application.service.movimento.MovimentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class ProcessOnceADayScheduler implements MovimentosPendentesProcessorPort {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessOnceADayScheduler.class);

    private static final long ONE_DAY_IN_MILLIS = 86400000;

    private final MovimentoService movimentoService;

    @Autowired
    public ProcessOnceADayScheduler(MovimentoService movimentoService) {
        this.movimentoService = movimentoService;
    }

    @Override
    @Scheduled(fixedDelay = ONE_DAY_IN_MILLIS)
    @Transactional
    public void processarMovimentosPendentes() {
        LOGGER.info("Processando pendentes!");
        this.movimentoService.processarPendentes();
    }
}
