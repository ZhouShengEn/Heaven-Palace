-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '标题',
    `href` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL NOT NULL COMMENT '资源路径',
    `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
    `parent_id` bigint DEFAULT -1  COMMENT '父级菜单id',
    `level` int(10) DEFAULT 0  COMMENT '菜单级别',
    `type` tinyint(3) NOT NULL COMMENT '类型：0-系统；1-路由；2-菜单',
    `status` tinyint(3) DEFAULT 1 COMMENT '状态：0-禁用；1-启用',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint NOT NULL COMMENT '创建人',
    `create_time` datetime(0) NULL NOT NULL COMMENT '创建时间',
    `update_by` bigint NOT NULL COMMENT '修改人',
    `update_time` datetime(0) NULL NOT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='系统菜单表' ROW_FORMAT = Dynamic;
