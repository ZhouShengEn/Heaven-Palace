-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '角色名称',
    `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '角色编码',
    `org_id` bigint NOT NULL COMMENT '组织id',
    `parent_id` bigint DEFAULT -1  COMMENT '父级角色id',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='用户角色表' ROW_FORMAT = Dynamic;
