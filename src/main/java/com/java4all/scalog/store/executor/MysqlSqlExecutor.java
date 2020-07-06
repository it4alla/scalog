package com.java4all.scalog.store.executor;

import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.store.LogInfoDto;
import com.java4all.scalog.store.sql.MysqlSql;
import com.java4all.scalog.utils.SourceUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Mysql Executor
 * @author wangzhongxiang
 */
@LoadLevel(name = "mysql")
public class MysqlSqlExecutor implements BaseSqlExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MysqlSqlExecutor.class);

    public MysqlSqlExecutor() {
    }

    /**
     * the dataSource from the application context
     */
    @Autowired
    private DataSource dataSource;

    @Override
    public void insert(LogInfoDto dto) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(true);
            PreparedStatement ps = connection.prepareStatement(MysqlSql.INSERT_LOG_SQL);
            ps.setString(1,SourceUtil.generateId());
            ps.setString(2,dto.getCompanyName());
            ps.setString(3,dto.getProjectName());
            ps.setString(4,dto.getModuleName());
            ps.setString(5,dto.getFunctionName());
            ps.setString(6,dto.getClassName());
            ps.setString(7,dto.getMethodName());
            ps.setString(8,dto.getMethodType());
            ps.setString(9,dto.getUrl());
            ps.setString(10,dto.getRequestParams());
            ps.setString(11,dto.getResult());
            ps.setString(12,dto.getRemark());
            ps.setLong(13,dto.getCost());
            ps.setString(14,dto.getIp());
            ps.setString(15,dto.getUserId());
            ps.setString(16,dto.getUserId());
            ps.setString(17,dto.getClientType());
            ps.setString(18,dto.getUserAgent());
            ps.setInt(19,1);
            ps.setString(20,dto.getGmtStart());
            ps.setString(21,dto.getGmtEnd());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("log info insert failed,But it does not affect business logic:{}",e.getMessage(),e);
        } finally {
            SourceUtil.close(connection);
        }
    }
}
