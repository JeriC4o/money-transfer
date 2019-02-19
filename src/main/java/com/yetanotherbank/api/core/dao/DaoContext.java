package com.yetanotherbank.api.core.dao;

import org.jooq.impl.DAOImpl;

@FunctionalInterface
public interface DaoContext<T extends DAOImpl> {
    <R> R apply(DaoCallable<T, R> callable);
}
