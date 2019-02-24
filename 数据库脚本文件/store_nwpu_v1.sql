/*
 Navicat Premium Data Transfer

 Source Server         : MySQL80
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : store_nwpu_v1

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 23/02/2019 14:44:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `aid` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL,
  PRIMARY KEY (`aid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `cid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `cname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('172934bd636d485c98fd2d3d9cccd409', '人文社科');
INSERT INTO `category` VALUES ('1A06F576DE4304E13AD5C573F9C6A99D', '青春文学');
INSERT INTO `category` VALUES ('2911979599C2C4BC1A7BCCFC41A7D8B3', '经济管理');
INSERT INTO `category` VALUES ('306F576DE4304E13AD5C573F9C6A99D3', '成功励志');
INSERT INTO `category` VALUES ('5911979599C2C4BC1A7BCCFC41A7D8B9', '幽默搞笑');

-- ----------------------------
-- Table structure for orderitem
-- ----------------------------
DROP TABLE IF EXISTS `orderitem`;
CREATE TABLE `orderitem`  (
  `itemid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `quantity` int(11) NULL DEFAULT NULL,
  `total` double NULL DEFAULT NULL,
  `pid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `oid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`itemid`) USING BTREE,
  INDEX `order_item_fk_0001`(`pid`) USING BTREE,
  INDEX `order_item_fk_0002`(`oid`) USING BTREE,
  CONSTRAINT `order_item_fk_0001` FOREIGN KEY (`pid`) REFERENCES `product` (`pid`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `order_item_fk_0002` FOREIGN KEY (`oid`) REFERENCES `orders` (`oid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orderitem
-- ----------------------------
INSERT INTO `orderitem` VALUES ('4F2BEA0F5512470A880534B892D9B5E8', 1, 28.66, '1', '30824626F4054F5B8D44BD460EFE5FF5');
INSERT INTO `orderitem` VALUES ('5C1B318B8B5C41C4AD2FE486D7CCAC0B', 2, 139.98, '10', 'AF3DE68FA1794D00B02D65D0B14AAFCD');
INSERT INTO `orderitem` VALUES ('9739815419494F11BB22FA1DB0151698', 1, 28.66, '1', 'AF3DE68FA1794D00B02D65D0B14AAFCD');
INSERT INTO `orderitem` VALUES ('C7FB540E05C649CCB1146D41BC7F5646', 1, 69.99, '10', '30824626F4054F5B8D44BD460EFE5FF5');
INSERT INTO `orderitem` VALUES ('D497A295E1EC411D8E4FDD789520288B', 1, 53.1, '11', '30824626F4054F5B8D44BD460EFE5FF5');

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `oid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `ordertime` datetime(0) NULL DEFAULT NULL,
  `total` double NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `address` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`oid`) USING BTREE,
  INDEX `order_fk_0001`(`uid`) USING BTREE,
  CONSTRAINT `order_fk_0001` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES ('30824626F4054F5B8D44BD460EFE5FF5', '2019-02-21 22:51:15', 151.75, 4, NULL, NULL, NULL, '6DEF089FAC3D415FB629F32245FBC8EC');
INSERT INTO `orders` VALUES ('AF3DE68FA1794D00B02D65D0B14AAFCD', '2019-02-22 15:56:27', 168.64, 1, NULL, NULL, NULL, '6DEF089FAC3D415FB629F32245FBC8EC');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `pid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pname` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `market_price` double NULL DEFAULT NULL,
  `shop_price` double NULL DEFAULT NULL,
  `pimage` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pdate` date NULL DEFAULT NULL,
  `is_hot` int(11) NULL DEFAULT NULL,
  `pdesc` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `pflag` int(11) NULL DEFAULT 0,
  `cid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`pid`) USING BTREE,
  INDEX `product_fk_0001`(`cid`) USING BTREE,
  CONSTRAINT `product_fk_0001` FOREIGN KEY (`cid`) REFERENCES `category` (`cid`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('1', '作家榜经典:月亮与六便士', 33.8, 28.66, 'products/1/c_0001.jpg', '2019-01-01', 1, '“满地都是六便士，他却抬头看见了月亮。”银行家查尔斯，人到中年，事业有成，为了追求内心隐秘的绘画梦想，突然抛妻别子，弃家出走。他深知：人的每一种身份都是一种自我绑架，唯有失去是通向自由之途。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('10', '三体(1-3)(套装共3册)', 79.99, 69.99, 'products/1/c_0002.jpg', '2019-01-01', 1, '文化大革命如火如荼进行的同时，军方探寻外星文明的绝秘计划“红岸工程”取得了突破性进展。但在按下发射键的那一刻，历经劫难的叶文洁没有意识到，她彻底改变了人类的命运。\r\n地球文明向宇宙发出的啼鸣声，以太阳为中心，以光速向宇宙深处飞驰……\r\n\r\n四光年外，“三体文明”正苦苦挣扎——三颗无规则运行的太阳主导下的百余次毁灭与重生逼迫他们逃离母星。而恰在此时，他们接收到了地球发来的信息。\r\n在运用超技术锁死地球人的基础科学之后，三体人庞大的宇宙舰队开始向地球进发……人类的末日悄然来临。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('11', '薛兆丰经济学讲义', 69.98, 53.1, 'products/1/c_0003.jpg', '2019-01-01', 1, '我们每天收到无数纷繁复杂的信息，看到各种光怪陆离的现象，世界是复杂的。\r\n世界又并不复杂，只是你需要一双慧眼。经济学是一种帮助你成为明白人的智慧，它是观察世界的视角和态度，而不是一堆函数、公式和图表。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('13', '兰登双语阅读系列(共12册)', 99.99, 55.53, 'products/1/c_0004.jpg', '2019-01-01', 1, '兰登书屋经典英语读物，开启孩子英语阅读之旅。全套共12册图书，开篇重点词汇释义，帮助孩子扫除阅读障碍；前有英语原文，帮孩子融入英文情境；后附对照翻译，帮家长讲解故事原意；配套英音音频，帮助孩子听清英语发音，助力孩子说出一口漂亮英语。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('15', '流浪地球-刘慈欣 (作者)', 99.99, 66.66, 'products/1/c_0011.jpg', '2019-01-01', 1, '《流浪地球》是人类科幻史上极具代表性的作品，绽放了“中国想象力”。《流浪地球》讲述的内容是：科学家们发现太阳将膨胀为一颗红巨星，期间地球表面上的一切将毁灭殆尽，于是他们试图建造能将地球发射到其他星球的巨大引擎，以保证人类长远生存。……庞大的地球逃脱计划开始实施。与此同时，一件又一件曾经无比熟悉的事物从人类身边消失，疑惑和猜忌在人类当中引发叛乱之火，道德和伦理不复存在。在太阳最后灭亡的瞬间，一切都平息了，每个人怀揣着恐惧和希望踏上漫长的流浪之旅……《流浪地球》作品场面宏大，描写细腻，富有人文情怀。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('19', '岛上书店（精装）', 99.99, 66.66, 'products/1/c_0006.jpg', '2019-01-01', 1, '岛上书店是间维多利亚风格的小屋，门廊上挂着褪色的招牌，上面写着： 没有谁是一座孤岛，每本书都是一个世界。小岛上的几个生命紧紧相依，走出了人生的困境，而所有对书和生活的热爱都周而复始，愈加汹涌。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('2', '北欧众神', 99.99, 66.66, 'products/1/c_0007.jpg', '2019-01-01', 1, '雷神托尔的锤子来自何处？\r\n诸神之父奥丁为何失去一只眼睛？\r\n亦正亦邪的洛基究竟是什么身份？\r\n诞生两千年来，历经多次演绎，北欧神话的本源已越来越模糊。而今，幻想文学大师尼尔•盖曼厘清了北欧神话的脉络，以全新的笔法，再现了这一辉煌壮丽的神话体系。\r\n从世界之树到九大世界，从一切的诞生到诸神的黄昏，从青春的金苹果到巨狼芬尼尔……在盖曼笔下，故事重获新生，众神逐个复活。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('22', '深度思考:不断逼近问题的本质', 99.99, 66.66, 'products/1/c_0008.jpg', '2019-01-01', 1, '深度思考就是不断逼近问题的本质\r\n\r\n事情来了就做，很少去想为什么做，怎么做，以及做了之后想得到什么结果？\r\n笃信天道酬勤，大部分时间用来埋头苦干，却鲜少抬头看路？\r\n习惯于用战术上的勤奋来掩饰战略上的懒惰？用深度思考连接一切，是未来10年最有价值的认知升级与自我精进的模式，是最具竞争力的优势。', 0, '172934bd636d485c98fd2d3d9cccd409');
INSERT INTO `product` VALUES ('23', '原则（精装）', 66.66, 20.19, 'products/1/c_0009.jpg', '2019-01-01', 1, '瑞·达利欧是全世界投资家、企业家之一，对冲基金公司桥水创始人。桥水创立至今为客户赚取的收益远远超过历史上任何一家对冲基金。达利欧认为桥水的成功源自他所奉行的一套原则，而这些原则也是他一生中学到的最重要的东西。', 0, '172934bd636d485c98fd2d3d9cccd409');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `uid` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `sex` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT 0,
  `code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('6DEF089FAC3D415FB629F32245FBC8EC', 'zhangsan', 'zhangsan', '张三', 'yylin7239@163.com', '17862819206', '2019-02-01', '男', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
