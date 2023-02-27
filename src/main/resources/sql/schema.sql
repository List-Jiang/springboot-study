create user if not exists root password 'root';
alter user root admin true;
drop table if exists demo;
create table if not exists demo
(
    id           int          not null comment '主键ID'
        primary key,
    name         varchar(100) null comment '姓名',
    key_word     varchar(100) null comment '关键词',
    punch_time   datetime     null comment '打卡时间',
    salary_money varchar(100) null comment '工资',
    bonus_money  varchar(100) null comment '奖金',
    sex          tinyint(1)   null comment '性别 {男:1,女:2}',
    age          tinyint      null comment '年龄',
    birthday     date         null comment '生日',
    email        varchar(100) null comment '邮箱',
    content      varchar(100) null comment '个人简介',
    create_by    varchar(100) null comment '创建人',
    create_time  timestamp    null comment '创建时间',
    update_by    varchar(100) null comment '修改人',
    update_time  timestamp    null comment '修改时间',
    sys_org_code varchar(100) null comment '所属部门编码',
    status       tinyint(1)   null comment '有效状态，0无效，1，有效'
)
    comment 'Demo对象';

drop table if exists permissions;
create table if not exists permissions
(
    id              bigint       not null
        primary key,
    permissionsName varchar(100) not null comment '权限名'
);

drop table if exists role;
create table if not exists role
(
    id       bigint       not null
        primary key,
    roleName varchar(100) not null comment '角色名'
);

drop table if exists role_permission;
create table if not exists role_permission
(
    id            bigint not null
        primary key,
    role_id       bigint null,
    permission_id bigint null
);

drop table if exists user;
create table if not exists user
(
    id          bigint       not null
        primary key,
    name        varchar(100) not null comment '姓名',
    age         tinyint      not null comment '年龄',
    email       varchar(100) not null comment '邮箱',
    sex         tinyint(1)   not null comment '性别',
    status      tinyint(1)   not null comment '有效状态',
    account     varchar(100) not null comment '登录账号',
    password    varchar(100) not null comment '登录密码',
    create_date timestamp    not null comment '创建时间',
    update_date timestamp    not null comment '修改时间'
);

drop table if exists user_role;
create table if not exists user_role
(
    id      bigint not null
        primary key,
    user_id bigint null,
    role_id bigint null
);

