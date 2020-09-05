package com.java4all.scalog.store.sql;

/**
 * @author wangzhongxiang
 */
public class OracleSql {

    public static final String LOG_TABLE = "log_info";

    public static final String INSERT_LOG_SQL =
            //TODO
            "insert into " + LOG_TABLE +
                    "(`id`, `company_name`, `project_name`, `module_name`, `function_name`, "
                    + "`class_name`, `method_name`, `method_type`, `url`, `request_params`,"
                    + "`result`, `remark`, `cost`, `ip`, `user_id`, "
                    + "`user_name`, `log_type`,`gmt_start`, `gmt_end`,`gmt_create`, `gmt_modified`,`error_message`,`error_stack_trace`)"
                    + " values "
                    + "(?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, ?,"
                    + " ?, ?, ?, ?, now(), now(), ?, ?)";
}
