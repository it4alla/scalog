package com.java4all.scalog.store.executor;

import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.store.LogInfoDto;

/**
 * Oracle SqlExecutor
 * @author wangzhongxiang
 */
@LoadLevel(name = "oracle")
public class OracleSqlExecutor implements BaseSqlExecutor{

    public OracleSqlExecutor() {
    }

    /**
     * insert
     */
    @Override
    public void insert(LogInfoDto logInfoDto,Object dataSource) throws Exception{
        throw new NoSuchMethodException("Not support Oracle now!");
    }
}
