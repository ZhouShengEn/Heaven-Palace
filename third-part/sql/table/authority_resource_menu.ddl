-- ----------------------------
-- Table structure for authority_resource_menu
-- ----------------------------
DROP TABLE IF EXISTS `authority_resource_menu`;
CREATE TABLE `authority_resource_menu`  (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `role_id` bigint NOT NULL COMMENT '角色id',
    `element_id` bigint NOT NULL COMMENT '元素id',
    `element_type` tinyint(3) NOT NULL COMMENT '元素类型：0-resource；1-menu',
    `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `is_delete` tinyint(1) DEFAULT 0 COMMENT 'true-已删除，false-未删除',
    `create_by` bigint DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_by` bigint DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0) DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT='资源菜单授权表' ROW_FORMAT = Dynamic;
