package com.eduardo.moneysaver.core.domain.model;

import com.eduardo.moneysaver.core.application.exception.error.MovimentoJaEfetivadoException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "movimentos")
@Entity(name = "movimentos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Movimento {

    private static final Logger LOGGER = LoggerFactory.getLogger(Movimento.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private Double valor;
    @Enumerated(EnumType.STRING)
    private TipoMovimento tipo;
    @Column(name = "data_efetivacao")
    private LocalDate dataEfetivacao;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;
    @Enumerated(EnumType.STRING)
    private StatusMovimento status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "movimentos_tags", joinColumns = @JoinColumn(name = "movimento_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    private Movimento(Builder builder) {
        descricao = builder.descricao;
        valor = builder.valor;
        tipo = builder.tipo;
        dataEfetivacao = builder.dataEfetivacao;
        conta = builder.conta;
        user = builder.user;
        tags = builder.tags;
        status = builder.status;
        criadoEm = LocalDateTime.now();
    }

    public static Builder newMovimento() {
        return new Builder();
    }

    public void efetivar() {
        LOGGER.info(String.format("Processando movimentação id: %s", this.id));
        if (this.getStatus().equals(StatusMovimento.E))
            throw new MovimentoJaEfetivadoException("Movimentação já efetivada" + this.getId());
        this.status = StatusMovimento.E;
        this.conta.updateSaldo(this.valor, this.tipo);
    }

    public static final class Builder {
        private String descricao;
        private Double valor;
        private TipoMovimento tipo;
        private LocalDate dataEfetivacao;
        private Conta conta;
        private User user;
        private Set<Tag> tags;
        private StatusMovimento status;

        private Builder() {
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder valor(Double valor) {
            this.valor = valor;
            return this;
        }

        public Builder tipo(TipoMovimento tipo) {
            this.tipo = tipo;
            return this;
        }

        public Builder dataEfetivacao(LocalDate dataEfetivacao) {
            this.dataEfetivacao = dataEfetivacao;
            return this;
        }

        public Builder conta(Conta conta) {
            this.conta = conta;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder tags(Set<Tag> tags) {
            this.tags = tags;
            return this;
        }

        public Builder status(StatusMovimento status) {
            this.status = status;
            return this;
        }

        public Movimento build() {
            return new Movimento(this);
        }
    }
}
