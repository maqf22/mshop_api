/*
 Navicat Premium Data Transfer

 Source Server         : Dev_Database
 Source Server Type    : MySQL
 Source Server Version : 50743 (5.7.43)
 Source Host           : 192.168.10.134:3306
 Source Schema         : mshop

 Target Server Type    : MySQL
 Target Server Version : 50743 (5.7.43)
 File Encoding         : 65001

 Date: 01/08/2023 10:07:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for g_evaluate
-- ----------------------------
DROP TABLE IF EXISTS `g_evaluate`;
CREATE TABLE `g_evaluate`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `content` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `score` int(11) NULL DEFAULT NULL,
  `time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `goods_id` bigint(20) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_evaluate
-- ----------------------------
INSERT INTO `g_evaluate` VALUES (1, '111', 5, '1690015964286', 1, 101);
INSERT INTO `g_evaluate` VALUES (2, '222', 3, '1690015955286', 2, 101);
INSERT INTO `g_evaluate` VALUES (3, '333', 3, '1690015949286', 1, 102);
INSERT INTO `g_evaluate` VALUES (4, '444', 2, '1690015923286', 1, 102);
INSERT INTO `g_evaluate` VALUES (5, '555', 4, '1690015916286', 1, 101);
INSERT INTO `g_evaluate` VALUES (6, '666', 3, '1690015896286', 2, 101);
INSERT INTO `g_evaluate` VALUES (7, '777', 5, '1690015964286', 2, 101);
INSERT INTO `g_evaluate` VALUES (8, '888', 3, '1690015955286', 1, 101);
INSERT INTO `g_evaluate` VALUES (9, '999', 5, '1690015949286', 1, 101);
INSERT INTO `g_evaluate` VALUES (10, '101010', 5, '1690015923286', 1, 101);
INSERT INTO `g_evaluate` VALUES (11, '111111', 2, '1690015916286', 1, 101);
INSERT INTO `g_evaluate` VALUES (12, '121212', 3, '1690015896286', 1, 101);

-- ----------------------------
-- Table structure for g_evaluate_img
-- ----------------------------
DROP TABLE IF EXISTS `g_evaluate_img`;
CREATE TABLE `g_evaluate_img`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `image_url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `evaluate_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_evaluate_img
-- ----------------------------
INSERT INTO `g_evaluate_img` VALUES (1, 'https://cdn.pixabay.com/photo/2023/06/01/13/12/background-8033597_640.png', 1);
INSERT INTO `g_evaluate_img` VALUES (2, 'https://cdn.pixabay.com/photo/2023/04/10/06/32/tulip-7912886_640.jpg', 1);
INSERT INTO `g_evaluate_img` VALUES (3, 'https://cdn.pixabay.com/photo/2023/06/01/13/12/background-8033597_640.png', 2);
INSERT INTO `g_evaluate_img` VALUES (4, 'https://cdn.pixabay.com/photo/2023/06/01/13/12/background-8033597_640.png', 3);

-- ----------------------------
-- Table structure for g_goods_img
-- ----------------------------
DROP TABLE IF EXISTS `g_goods_img`;
CREATE TABLE `g_goods_img`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `url` varchar(300) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `goods_id` bigint(20) UNSIGNED NOT NULL,
  `order` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_goods_img
-- ----------------------------
INSERT INTO `g_goods_img` VALUES (1, 'https://robohash.org/Jack', 1, 0);
INSERT INTO `g_goods_img` VALUES (2, 'https://robohash.org/Rose', 1, 1);
INSERT INTO `g_goods_img` VALUES (3, 'https://robohash.org/Lily', 2, 0);

-- ----------------------------
-- Table structure for g_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `g_goods_info`;
CREATE TABLE `g_goods_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `goods_describe` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `store` int(10) UNSIGNED NULL DEFAULT 0,
  `create_time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `price` decimal(10, 0) NOT NULL,
  `cost` decimal(10, 0) NOT NULL,
  `publish_status` enum('1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1',
  `audit_status` enum('0','1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `production_date` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `shelf_life` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `goods_id` bigint(20) UNSIGNED NOT NULL,
  `version` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of g_goods_info
-- ----------------------------
INSERT INTO `g_goods_info` VALUES (1, 'XIAOMI 12 pro', 900, '1690253485548', 3999, 3999, '1', '1', '2023-06-15', NULL, 101, '1');
INSERT INTO `g_goods_info` VALUES (2, 'Apple iPhone 15 (A2404)', 297, '1690253425548', 7999, 7999, '1', '1', '2023-07-25', NULL, 102, '1');

-- ----------------------------
-- Table structure for o_order
-- ----------------------------
DROP TABLE IF EXISTS `o_order`;
CREATE TABLE `o_order`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `order_money` decimal(18, 2) NOT NULL,
  `actual_money` decimal(18, 2) NULL DEFAULT NULL,
  `point` int(11) NULL DEFAULT 0,
  `status` enum('0','1','2','3') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `create_time` bigint(20) NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `address_id` bigint(20) UNSIGNED NULL DEFAULT NULL,
  `order_no` char(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of o_order
-- ----------------------------

-- ----------------------------
-- Table structure for o_order_info
-- ----------------------------
DROP TABLE IF EXISTS `o_order_info`;
CREATE TABLE `o_order_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `price` decimal(18, 2) NOT NULL,
  `amount` int(11) NOT NULL,
  `goods_id` bigint(20) UNSIGNED NOT NULL,
  `order_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of o_order_info
-- ----------------------------

-- ----------------------------
-- Table structure for u_address
-- ----------------------------
DROP TABLE IF EXISTS `u_address`;
CREATE TABLE `u_address`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `province` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `city` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `street` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `user_id` bigint(20) NULL DEFAULT NULL,
  `is_default` enum('0','1') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_address
-- ----------------------------
INSERT INTO `u_address` VALUES (1, '广东省', '广州市', '黄埔区科学城科学大道33号', '15012430008', 'MaQF', 1, '0');
INSERT INTO `u_address` VALUES (2, '广东省', '广州市', '黄埔区知识城龙湖街', '15012430008', 'MaQF', 1, '1');

-- ----------------------------
-- Table structure for u_user
-- ----------------------------
DROP TABLE IF EXISTS `u_user`;
CREATE TABLE `u_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nick_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user
-- ----------------------------
INSERT INTO `u_user` VALUES (1, '15012430008', 'toor', 'Root');
INSERT INTO `u_user` VALUES (2, '15000000001', '2222', 'Jack');

-- ----------------------------
-- Table structure for u_user_info
-- ----------------------------
DROP TABLE IF EXISTS `u_user_info`;
CREATE TABLE `u_user_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `avatar` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday_year` int(6) NULL DEFAULT NULL,
  `birthday_month` tinyint(2) UNSIGNED NULL DEFAULT NULL,
  `birthday_day` tinyint(2) UNSIGNED NULL DEFAULT NULL,
  `gender` enum('0','1','2') CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0',
  `user_id` bigint(20) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of u_user_info
-- ----------------------------
INSERT INTO `u_user_info` VALUES (1, 'https://cdn.pixabay.com/photo/2022/06/01/05/52/fruit-7234847_640.jpg', 1994, 6, 18, '1', 1);

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `context` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ux_undo_log`(`xid`, `branch_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
INSERT INTO `undo_log` VALUES (36, 5512824037784376664, '192.168.10.133:8091:5512824037784376663', 'serializer=jackson', 0x7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E756E646F2E4272616E6368556E646F4C6F67222C22786964223A223139322E3136382E31302E3133333A383039313A35353132383234303337373834333736363633222C226272616E63684964223A353531323832343033373738343337363636342C2273716C556E646F4C6F6773223A5B226A6176612E7574696C2E41727261794C697374222C5B7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E756E646F2E53514C556E646F4C6F67222C2273716C54797065223A22555044415445222C227461626C654E616D65223A22675F676F6F64735F696E666F222C226265666F7265496D616765223A7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E5461626C655265636F726473222C227461626C654E616D65223A22675F676F6F64735F696E666F222C22726F7773223A5B226A6176612E7574696C2E41727261794C697374222C5B7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E526F77222C226669656C6473223A5B226A6176612E7574696C2E41727261794C697374222C5B7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E4669656C64222C226E616D65223A226964222C226B657954797065223A225052494D4152595F4B4559222C2274797065223A2D352C2276616C7565223A5B226A6176612E6D6174682E426967496E7465676572222C315D7D2C7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E4669656C64222C226E616D65223A2273746F7265222C226B657954797065223A224E554C4C222C2274797065223A342C2276616C7565223A5B226A6176612E6C616E672E4C6F6E67222C3937355D7D5D5D7D5D5D7D2C226166746572496D616765223A7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E5461626C655265636F726473222C227461626C654E616D65223A22675F676F6F64735F696E666F222C22726F7773223A5B226A6176612E7574696C2E41727261794C697374222C5B7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E526F77222C226669656C6473223A5B226A6176612E7574696C2E41727261794C697374222C5B7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E4669656C64222C226E616D65223A226964222C226B657954797065223A225052494D4152595F4B4559222C2274797065223A2D352C2276616C7565223A5B226A6176612E6D6174682E426967496E7465676572222C315D7D2C7B2240636C617373223A22696F2E73656174612E726D2E64617461736F757263652E73716C2E7374727563742E4669656C64222C226E616D65223A2273746F7265222C226B657954797065223A224E554C4C222C2274797065223A342C2276616C7565223A6E756C6C7D5D5D7D5D5D7D7D5D5D7D, 0, '2023-07-28 18:28:45', '2023-07-28 18:28:45', NULL);

-- ----------------------------
-- View structure for v_confirm_order_info
-- ----------------------------
DROP VIEW IF EXISTS `v_confirm_order_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_confirm_order_info` AS select `o_order_info`.`price` AS `price`,`o_order_info`.`amount` AS `amount`,`o_order_info`.`goods_id` AS `goods_id`,`g_goods_info`.`goods_describe` AS `goods_describe`,`o_order_info`.`order_id` AS `order_id`,`g_goods_img`.`url` AS `url` from ((`o_order_info` join `g_goods_info` on((`o_order_info`.`goods_id` = `g_goods_info`.`id`))) join `g_goods_img` on((`g_goods_info`.`id` = `g_goods_img`.`goods_id`))) where (`g_goods_img`.`order` = 0);

SET FOREIGN_KEY_CHECKS = 1;
