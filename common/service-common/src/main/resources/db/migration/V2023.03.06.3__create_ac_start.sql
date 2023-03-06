CREATE TABLE `ac_start`
(
    `id`              int                                                          NOT NULL AUTO_INCREMENT,
    `ac_node_id`      int                                                          NOT NULL,
    `submission_time` datetime                                                              DEFAULT NULL COMMENT '提交日期',
    `review_progress` double                                                                DEFAULT '0' COMMENT '审核进度',
    `pass_time`       datetime                                                              DEFAULT NULL COMMENT '审核通过日期',
    `flag`            varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '当前节点结果',
    `this_flag`       bit(1)                                                                DEFAULT b'1' COMMENT '当前节点的本次操作结果',
    `status`          varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '审核状态',
    `status_info`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci         DEFAULT NULL COMMENT '审核状态详情',
    `is_node`         bit(1)                                                                DEFAULT b'0' COMMENT '节点是否开启',
    `is_hidden`       bit(1)                                                                DEFAULT NULL COMMENT '是否隐藏',
    `is_his`          bit(1)                                                                DEFAULT b'0' COMMENT '是否为历史记录',
    `remark`          varchar(255)                                                          DEFAULT NULL COMMENT '备注',
    `delete_flag`     bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`       datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`       datetime                                                              DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='流程启动表';