-- ----------------------------
-- Table structure for system_resource
-- ----------------------------
DROP TABLE IF EXISTS `system_resource`;
CREATE TABLE `system_resource`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '资源编码',
    `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '资源名称',
    `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '资源数据',
    `type` tinyint(3) NOT NULL COMMENT '资源类型：0-uri',
    `status` tinyint(3) DEFAULT 1 COMMENT '状态：0-禁用；1-启用',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='系统资源表' ROW_FORMAT = Dynamic;
