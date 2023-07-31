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

 Date: 31/07/2023 10:05:59
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
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB AUTO_INCREMENT = 50 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- View structure for v_confirm_order_info
-- ----------------------------
DROP VIEW IF EXISTS `v_confirm_order_info`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `v_confirm_order_info` AS select `o_order_info`.`price` AS `price`,`o_order_info`.`amount` AS `amount`,`o_order_info`.`goods_id` AS `goods_id`,`g_goods_info`.`goods_describe` AS `goods_describe`,`o_order_info`.`order_id` AS `order_id`,`g_goods_img`.`url` AS `url` from ((`o_order_info` join `g_goods_info` on((`o_order_info`.`goods_id` = `g_goods_info`.`id`))) join `g_goods_img` on((`g_goods_info`.`id` = `g_goods_img`.`goods_id`))) where (`g_goods_img`.`order` = 0);

SET FOREIGN_KEY_CHECKS = 1;
