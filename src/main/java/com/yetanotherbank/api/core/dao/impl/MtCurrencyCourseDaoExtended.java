package com.yetanotherbank.api.core.dao.impl;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.generated.public_.tables.daos.MtCurrencyCourseDao;

public class MtCurrencyCourseDaoExtended extends MtCurrencyCourseDao {

    public MtCurrencyCourseDaoExtended() {
    }

    public MtCurrencyCourseDaoExtended(Configuration configuration) {
        super(configuration);
    }

    @Override
    public Record2 compositeKeyRecord(Object... values) {
        return super.compositeKeyRecord(values);
    }
}
