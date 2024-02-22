package com.erudio.model;

import java.math.BigDecimal;

public enum Performance {
    A_DESEJAR {
        @Override
        public BigDecimal pegarPercentualReajuste() {
            return new BigDecimal("0.03");
        }
    },

    BOM {
        @Override
        public BigDecimal pegarPercentualReajuste() {
            return new BigDecimal("0.15");
        }
    },

    OTIMO {
        @Override
        public BigDecimal pegarPercentualReajuste() {
            return new BigDecimal("0.20");
        }
    };

    public abstract BigDecimal pegarPercentualReajuste();
}