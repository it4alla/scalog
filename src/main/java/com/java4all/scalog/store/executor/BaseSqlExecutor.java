package com.java4all.scalog.store.executor;

import com.java4all.scalog.store.LogInfoDto;

/**
 * @author wangzhongxiang
 */
public interface BaseSqlExecutor {

    /**
     * insert
     *
     * @param dto
     * @throws Exception
     */
    void insert(LogInfoDto dto) throws Exception;
}
