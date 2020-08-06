# create table
create table log_info (
      id char(50) not null ,
      country_name char(200),
      group_name char(200),
      organization_name char(200),
      company_name char(200),
      project_name char(200),
      module_name char(200),
      function_name char(200),
      class_name char(200),
      method_name char(200),
      method_type char(200),
      url text,
      request_params text,
      result text,
      remark char(200),
      cost int,
      ip char(100),
      user_id char(200),
      user_name char(200),
      client_type char(200),
      user_agent char(200),
      log_type int,
      gmt_start timestamp without time zone,
      gmt_end timestamp without time zone,
      gmt_create timestamp without time zone,
      gmt_modified timestamp without time zone
);

# add comment
comment on column log_info.country_name is '';
comment on column log_info.group_name is '';
comment on column log_info.organization_name is '';
comment on column log_info.company_name is '';
comment on column log_info.project_name is '';
comment on column log_info.module_name is '';
comment on column log_info.function_name is '';
comment on column log_info.class_name is '';
comment on column log_info.method_name is '';
comment on column log_info.method_type is '';
comment on column log_info.url is '';
comment on column log_info.request_params is '';
comment on column log_info.result is '';
comment on column log_info.remark is '';
comment on column log_info.cost is '';
comment on column log_info.ip is '';
comment on column log_info.user_id is '';
comment on column log_info.user_name is '';
comment on column log_info.client_type is '';
comment on column log_info.user_agent is '';
comment on column log_info.log_type is '';
comment on column log_info.gmt_start is '';
comment on column log_info.gmt_end is '';
comment on column log_info.gmt_create is '';
comment on column log_info.gmt_modified is '';
