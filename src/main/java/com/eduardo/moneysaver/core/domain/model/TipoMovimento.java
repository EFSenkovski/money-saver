package com.eduardo.moneysaver.core.domain.model;

import lombok.Getter;

@Getter
public enum TipoMovimento {
    E, //ENTRADA
    S; //SAIDA


    public static TipoMovimento getFrom(String tipoString) {
        for (TipoMovimento tipoMovimento : TipoMovimento.values()) {
            if (tipoMovimento.name().equals(tipoString)) {
                return tipoMovimento;
            }
        }
        throw new IllegalArgumentException("Tipo inválido " + tipoString);
    }
}
