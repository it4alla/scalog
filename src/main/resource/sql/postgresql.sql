# create table
create table log_info (
      id char(50) not null ,
      country_name varchar,
      group_name varchar,
      organization_name varchar,
      company_name varchar ,
      project_name varchar ,
      module_name varchar,
      function_name varchar,
      class_name varchar,
      method_name varchar,
      method_type varchar,
      url text,
      request_params text,
      result text,
      remark varchar,
      cost int,
      ip char(100),
      user_id varchar,
      user_name varchar,
      client_type varchar,
      user_agent varchar,
      log_type int,
      gmt_start timestamp without time zone,
      gmt_end timestamp without time zone,
      gmt_create timestamp without time zone,
      gmt_modified timestamp without time zone,
      error_message text,
      error_stack_trace text
);

# add comment
comment on column log_info.country_name is '国家';
comment on column log_info.group_name is '集团名称';
comment on column log_info.organization_name is '组织名称';
comment on column log_info.company_name is '公司名称';
comment on column log_info.project_name is '项目名称';
comment on column log_info.module_name is '模块名称';
comment on column log_info.function_name is '功能名称';
comment on column log_info.class_name is '接口所在类名称';
comment on column log_info.method_name is '方法名称';
comment on column log_info.method_type is '方法类型';
comment on column log_info.url is '接口url';
comment on column log_info.request_params is '接口入参';
comment on column log_info.result is '接口返回值';
comment on column log_info.remark is '备注';
comment on column log_info.cost is '接口耗时';
comment on column log_info.ip is '用户ip';
comment on column log_info.user_id is '用户id';
comment on column log_info.user_name is '用户名称';
comment on column log_info.client_type is '客户端类型';
comment on column log_info.user_agent is '客户端信息';
comment on column log_info.log_type is '此条操作状态：0 正常  1 异常';
comment on column log_info.gmt_start is '操作开始时间';
comment on column log_info.gmt_end is '操作结束时间';
comment on column log_info.gmt_create is '创建时间';
comment on column log_info.gmt_modified is '修改时间';
comment on column log_info.error_message is '错误信息';
comment on column log_info.error_stack_trace is'错误堆栈';

# add index
CREATE index index_company_name ON log_info(company_name);
CREATE index index_project_name ON log_info(project_name);
CREATE index index_user_id ON log_info(user_id);
CREATE index index_gmt_create ON log_info(gmt_create);
