package com.eduardo.moneysaver.core.domain.model;

import com.eduardo.moneysaver.core.application.exception.error.SaldoInsuficienteException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Table(name = "contas")
@Entity(name = "contas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Conta {
    private static final Logger LOGGER = LoggerFactory.getLogger(Conta.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Double saldo;
    @ManyToOne @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "conta")
    private List<Movimento> movimentos;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    private Conta(Builder builder) {
        nome = builder.nome;
        saldo = builder.saldo;
        user = builder.user;
        criadoEm = LocalDateTime.now();
    }

    public void updateSaldo(Double valor, TipoMovimento tipoMovimento) {
        LOGGER.info(String.format("Atualizando saldo da conta %s", id));
        if (tipoMovimento.equals(TipoMovimento.E)) {
            this.saldo += valor;
        } else if (tipoMovimento.equals(TipoMovimento.S)) {
            if (this.saldo < valor) throw new SaldoInsuficienteException("Saldo insuficiente para efetuar a movimentação");
            this.saldo -= valor;
        }
    }

    public static Builder newConta() {
        return new Builder();
    }

    public static final class Builder {
        private String nome;
        private Double saldo;
        private User user;

        private Builder() {
        }

        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder saldo(Double saldo) {
            this.saldo = saldo;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Conta build() {
            return new Conta(this);
        }
    }
}
