package com.yetanotherbank.api.service;

import org.jooq.impl.DAOImpl;

@FunctionalInterface
public interface DaoContext<T extends DAOImpl> {
    <R> R apply(DaoCallable<T, R> callable);

    @FunctionalInterface
    interface DaoCallable<T extends DAOImpl, R> {
        R call(T dao);
    }
}
