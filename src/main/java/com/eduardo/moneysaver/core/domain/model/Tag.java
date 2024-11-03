package com.eduardo.moneysaver.core.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "tags")
@Entity(name = "tags")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "criado_em")
    private LocalDateTime criadoEm;

    private Tag(Builder builder) {
        descricao = builder.descricao;
        user = builder.user;
        criadoEm = LocalDateTime.now();
    }

    public static Builder newTag() {
        return new Builder();
    }

    public static final class Builder {
        private String descricao;
        private User user;

        private Builder() {
        }

        public Builder descricao(String descricao) {
            this.descricao = descricao;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Tag build() {
            return new Tag(this);
        }
    }
}
