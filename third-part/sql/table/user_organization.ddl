-- ----------------------------
-- Table structure for user_organization
-- ----------------------------
DROP TABLE IF EXISTS `user_organization`;
CREATE TABLE `user_organization`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '组织名称',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `parent_id` bigint DEFAULT -1  COMMENT '父级组织id',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint NOT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL NOT NULL COMMENT '创建时间',
    `update_by` bigint NOT NULL COMMENT '修改人',
    `update_time` datetime(0) NULL NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='用户组织表' ROW_FORMAT = Dynamic;
