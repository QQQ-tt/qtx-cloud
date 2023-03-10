CREATE TABLE `ac_node`
(
    `id`             int                                                          NOT NULL AUTO_INCREMENT,
    `ac_name_id`     int                                                          NOT NULL,
    `node_name`      varchar(50)                                                           DEFAULT NULL COMMENT '节点名称',
    `node_group`     int                                                                   DEFAULT NULL COMMENT '节点组合',
    `node_rate_pass` decimal(10, 2)                                                        DEFAULT NULL COMMENT '节点通过率',
    `business_info`  varchar(50)                                                           DEFAULT NULL COMMENT '实际关联业务',
    `business_mean`  varchar(255)                                                          DEFAULT NULL COMMENT '业务含义',
    `delete_flag`    bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`      datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`      varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`      datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程节点表';