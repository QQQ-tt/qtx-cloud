CREATE TABLE `sys_user_role`
(
    `id`          int                                                          NOT NULL AUTO_INCREMENT,
    `user_card`   int                                                                   DEFAULT NULL COMMENT '用户账号',
    `role_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '角色id',
    `delete_flag` bit(1)                                                       NOT NULL DEFAULT b'0' COMMENT '是否删除 0:否 1:是',
    `create_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
    `create_on`   datetime                                                     NOT NULL COMMENT '创建时间',
    `update_by`   varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci          DEFAULT NULL COMMENT '修改人',
    `update_on`   datetime                                                              DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci COMMENT ='用户角色关系表';