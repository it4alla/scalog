package com.java4all.scalog.store.executor;

import com.java4all.scalog.annotation.LoadLevel;
import com.java4all.scalog.store.LogInfoDto;
import com.java4all.scalog.store.sql.MysqlSql;
import com.java4all.scalog.store.sql.PostgreSqlSql;
import com.java4all.scalog.utils.SourceUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wangzhongxiang
 */
@LoadLevel(name = "postgresql")
public class PostgreSqlExecutor implements BaseSqlExecutor{

    private static final Logger LOGGER = LoggerFactory.getLogger(PostgreSqlExecutor.class);

    private DataSource dataSource;

    public PostgreSqlExecutor() {
    }

    public PostgreSqlExecutor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * insert
     */
    @SuppressWarnings("all")
    @Override
    public void insert(LogInfoDto dto) throws Exception {
        DataSource source = dataSource;
        Connection connection = null;
        try {
            connection = source.getConnection();
            connection.setAutoCommit(true);
            PreparedStatement ps = connection.prepareStatement(PostgreSqlSql.INSERT_LOG_SQL);
            ps.setString(1,SourceUtil.generateId());
            ps.setString(2,dto.getCountryName());
            ps.setString(3,dto.getGroupName());
            ps.setString(4,dto.getOrganizationName());
            ps.setString(5,dto.getCompanyName());
            ps.setString(6,dto.getProjectName());
            ps.setString(7,dto.getModuleName());
            ps.setString(8,dto.getFunctionName());
            ps.setString(9,dto.getClassName());
            ps.setString(10,dto.getMethodName());
            ps.setString(11,dto.getMethodType());
            ps.setString(12,dto.getUrl());
            ps.setString(13,dto.getRequestParams());
            ps.setString(14,dto.getResult());
            ps.setString(15,dto.getRemark());
            ps.setLong(16,dto.getCost());
            ps.setString(17,dto.getIp());
            ps.setString(18,dto.getUserId());
            ps.setString(19,dto.getUserId());
            ps.setString(20,dto.getClientType());
            ps.setString(21,dto.getUserAgent());
            ps.setInt(22,1);
            ps.setString(23,dto.getGmtStart());
            ps.setString(24,dto.getGmtEnd());
            ps.execute();
        } catch (SQLException e) {
            LOGGER.error("log info insert failed,But it does not affect business logic:{}",e.getMessage(),e);
        } finally {
            SourceUtil.close(connection);
        }
    }
}
