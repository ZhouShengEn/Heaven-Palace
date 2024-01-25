-- ----------------------------
-- Table structure for base_user
-- ----------------------------
DROP TABLE IF EXISTS `base_user`;
CREATE TABLE `base_user`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '用户名称',
    `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '用户密码',
    `birthday` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户生日',
    `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
    `mobile_phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '移动手机号',
    `tel_phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '电话号码',
    `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    `avatar` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片地址',
    `sex` tinyint(1) DEFAULT NULL COMMENT '性别：0-男；1-女',
    `status` tinyint(2) DEFAULT 1 COMMENT '状态：0-使用中',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `index_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='基础用户信息表' ROW_FORMAT = Dynamic;
