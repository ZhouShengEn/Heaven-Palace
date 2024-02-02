-- ----------------------------
-- Table structure for auth_client
-- ----------------------------
DROP TABLE IF EXISTS `auth_client`;
CREATE TABLE `auth_client`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '客户端编码',
    `secret` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '服务密钥',
    `login_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '系统登录入口',
    `org_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '组织编码',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='认证客户端表' ROW_FORMAT = Dynamic;
