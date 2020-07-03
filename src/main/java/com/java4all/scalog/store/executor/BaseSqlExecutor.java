package com.java4all.scalog.store.executor;

import com.java4all.scalog.store.LogInfoDto;

/**
 * @author wangzhongxiang
 */
public interface BaseSqlExecutor {

    /**
     * insert
     *
     * @param logInfoDto
     * @throws Exception
     */
    void insert(LogInfoDto logInfoDto) throws Exception;
}
