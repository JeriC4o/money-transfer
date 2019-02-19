package com.yetanotherbank.api.core.dao;

import org.jooq.impl.DAOImpl;

@FunctionalInterface
public interface DaoCallable<T extends DAOImpl, R> {
    R call(T dao);
}
