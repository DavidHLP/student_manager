-- MySQL dump 10.13  Distrib 8.0.36, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: student_db
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bed`
--

DROP DATABASE IF EXISTS student_db;
create database student_db;
use student_db;

DROP TABLE IF EXISTS `bed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bed` (
                       `id` int NOT NULL AUTO_INCREMENT COMMENT '床位ID',
                       `dormitory_id` int NOT NULL COMMENT '所属宿舍ID',
                       `bed_number` int NOT NULL COMMENT '床位编号',
                       `user_id` int DEFAULT NULL COMMENT '床位所使用的用户ID',
                       `status` enum('available','occupied') DEFAULT 'available' COMMENT '床位状态',
                       `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                       `bed_info` varchar(255) DEFAULT NULL COMMENT '床位信息 编码规则为 学院id-书院id-宿舍id-床位号',
                       PRIMARY KEY (`id`),
                       KEY `bed_ibfk_1` (`dormitory_id`),
                       CONSTRAINT `bed_ibfk_1` FOREIGN KEY (`dormitory_id`) REFERENCES `dormitory` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='床位表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bed`
--

LOCK TABLES `bed` WRITE;
/*!40000 ALTER TABLE `bed` DISABLE KEYS */;
INSERT INTO `bed` VALUES (8,2,1,3,'available','2024-12-26 17:50:30','2024-12-27 23:38:14','1-2-1'),(9,2,2,5,'available','2024-12-26 17:50:30','2024-12-27 23:38:14','1-2-2'),(10,2,3,5,'available','2024-12-26 17:50:30','2024-12-27 23:38:14','1-2-3'),(11,2,4,6,'available','2024-12-26 17:50:30','2024-12-27 23:38:14','1-2-4'),(15,3,1,11,'available','2024-12-26 17:50:30','2024-12-27 23:15:25','2-3-1'),(16,3,2,12,'available','2024-12-26 17:50:30','2024-12-27 22:06:13','2-3-2'),(17,3,3,13,'available','2024-12-26 17:50:30','2024-12-27 23:15:22','2-3-3'),(18,3,4,4,'available','2024-12-26 17:50:30','2024-12-27 23:15:11','2-3-4'),(34,13,1,8,'available','2024-12-27 22:57:35','2024-12-27 23:38:31','2-13-1'),(35,13,2,6,'occupied','2024-12-27 22:57:35','2024-12-27 23:38:31','2-13-2'),(36,13,3,NULL,'available','2024-12-27 22:57:35','2024-12-27 23:38:31','2-13-3'),(37,13,4,NULL,'available','2024-12-27 22:57:35','2024-12-27 23:38:31','2-13-4');
/*!40000 ALTER TABLE `bed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `college` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(255) NOT NULL COMMENT '书院名称',
                           `description` text COMMENT '书院描述',
                           `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `department_id` int NOT NULL COMMENT '所属部门ID',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='书院表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'书院一','注重人文素质教育的书院','2024-12-26 17:48:54','2024-12-27 17:33:18',105),(2,'书院二','以理学为主的综合书院','2024-12-26 17:48:54','2024-12-27 17:33:18',104);
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
                          `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
                          `course_name` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '课程名称',
                          `credit` float(10,1) NOT NULL DEFAULT '2.0' COMMENT '学分',
                          `status` int NOT NULL DEFAULT '1' COMMENT '状态 0 草稿 1 已发布 2 已关闭',
                          `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '课程简介',
                          `teacher_id` int NOT NULL DEFAULT '0' COMMENT '教师',
                          `capacity` int NOT NULL DEFAULT '0' COMMENT '限选人数',
                          `location` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '上课地点',
                          `course_time` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '上课时间',
                          `term` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '学期',
                          PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,'大数据项目综合开发实践',2.0,1,'大数据项目综合开发实践',2,5,'G1-207','1-18周 周二78节，周五34节','2024-2025-1'),(2,'Web程序设计',2.0,1,'Web程序设计',2,5,'G1-207','1-18周 周二78节，周五34节','2024-2025-1'),(3,'内存计算与大数据分析',2.0,1,'内存计算与大数据分析',2,5,'G4-208','1-18周 周一12节','2024-2025-2'),(4,'Web程序设计（Python）',2.0,1,'Web程序设计（Python）',2,5,'G4-208','1-18周 周一12节','2024-2025-2'),(5,'Web课程设计（Python）',2.0,1,'Web课程设计（Python）',2,5,'G4-208','1-18周 周一12节','2024-2025-2'),(6,'离散数学',2.0,1,'123123',1,50,'G4-108','1-18周 星期一12节','2022-2023-1');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dept`
--

DROP TABLE IF EXISTS `dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dept` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '部门id',
                        `pid` int NOT NULL DEFAULT '0' COMMENT '父部门id',
                        `dep_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
                        `status` int NOT NULL DEFAULT '1' COMMENT '部门状态',
                        `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=149 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dept`
--

LOCK TABLES `dept` WRITE;
/*!40000 ALTER TABLE `dept` DISABLE KEYS */;
INSERT INTO `dept` VALUES (101,0,'洛阳师范学院',1,'1'),(102,101,'教学单位',1,''),(103,101,'管理机构',1,''),(104,102,'文学院',1,''),(105,102,'历史文化学院',1,''),(106,102,'法学与社会学院',1,''),(107,102,'数学科学学院',1,''),(108,102,'物理与电子信息学院',1,''),(109,102,'化学化工学院',1,''),(110,102,'外国语学院',1,''),(111,102,'体育学院',1,''),(112,102,'音乐学院',1,''),(113,102,'美术与艺术设计学院',1,''),(114,102,'信息技术学院',1,''),(115,102,'教育科学学院',1,''),(116,102,'生命科学学院',1,''),(117,102,'商学院',1,''),(118,102,'国际教育学院',1,''),(119,102,'软件职业技术学院',1,''),(120,102,'公共外语教研部',1,''),(121,102,'公共体育教研部',1,''),(122,102,'公共艺术教研部',1,''),(123,102,'马克思主义学院',1,''),(124,102,'继续教育学院',1,''),(125,102,'学前教育学院',1,''),(126,102,'地理与旅游学院',1,''),(127,102,'新闻与传播学院',1,''),(128,102,'艺术设计学院',1,''),(129,102,'电子商务学院',1,''),(130,102,'食品与药品学院',1,''),(134,103,'科研处',1,''),(140,103,'学生处',1,''),(145,103,'财务处',1,''),(146,103,'人事处',1,''),(148,103,'教务处',1,'');
/*!40000 ALTER TABLE `dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dormitory`
--

DROP TABLE IF EXISTS `dormitory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dormitory` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `name` varchar(255) NOT NULL COMMENT '宿舍名称',
                             `building` varchar(255) NOT NULL COMMENT '楼栋',
                             `college_id` int NOT NULL COMMENT '所属书院ID',
                             `capacity` int NOT NULL COMMENT '容纳人数',
                             `occupied` int NOT NULL COMMENT '已入住人数',
                             `created_at` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                             PRIMARY KEY (`id`),
                             KEY `college_id` (`college_id`),
                             CONSTRAINT `dormitory_ibfk_1` FOREIGN KEY (`college_id`) REFERENCES `college` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='宿舍表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dormitory`
--

LOCK TABLES `dormitory` WRITE;
/*!40000 ALTER TABLE `dormitory` DISABLE KEYS */;
INSERT INTO `dormitory` VALUES (2,'宿舍B','2栋',1,4,0,'2024-12-26 17:50:19','2024-12-27 23:38:14'),(3,'宿舍C','3栋',2,4,0,'2024-12-26 17:50:19','2024-12-27 23:15:25'),(13,'测试数据','2栋 ',2,4,1,'2024-12-27 22:57:35','2024-12-27 23:38:31');
/*!40000 ALTER TABLE `dormitory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enrollment`
--

DROP TABLE IF EXISTS `enrollment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `enrollment` (
                              `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
                              `course_id` int NOT NULL COMMENT '课程ID',
                              `student_id` int NOT NULL COMMENT '学生ID',
                              `enrollment_time` datetime NOT NULL COMMENT '选课日期',
                              `status` int NOT NULL COMMENT '状态 0 已选 1 已确认 2 已取消',
                              `score` int NOT NULL DEFAULT '0' COMMENT '评分',
                              PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enrollment`
--

LOCK TABLES `enrollment` WRITE;
/*!40000 ALTER TABLE `enrollment` DISABLE KEYS */;
INSERT INTO `enrollment` VALUES (9,1,1,'2024-12-05 14:34:04',2,0),(13,2,1,'2024-12-05 16:24:14',2,0),(14,1,3,'2024-12-06 11:22:11',2,0),(16,3,3,'2024-12-06 11:22:18',1,0),(17,6,3,'2024-12-06 11:23:10',1,0);
/*!40000 ALTER TABLE `enrollment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
                        `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
                        `pid` int NOT NULL DEFAULT '0' COMMENT '父菜单ID',
                        `order_num` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
                        `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '路由地址',
                        `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '组件路径',
                        `menu_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'M' COMMENT '菜单类型（M目录 C菜单 F按钮）',
                        `status` int NOT NULL DEFAULT '1' COMMENT '菜单应用状态（0正常 1停用）',
                        `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1064 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'首页',0,1,'/','','M',1,''),(3,'选课',0,2,'/course','','M',1,''),(9,'系统管理',0,3,'/sys','','M',1,''),(100,'工作台',1,1,'/dashboard','','C',1,''),(300,'课程管理',3,1,'/course/course','','C',1,''),(301,'选课',3,1,'/course/selection','','C',1,''),(302,'我的课程',3,1,'/course/my','','C',1,''),(900,'用户管理',9,1,'/sys/user','','C',1,''),(901,'角色管理',9,2,'/sys/role','','C',1,''),(902,'菜单管理',9,3,'/sys/menu','','C',1,''),(903,'部门管理',9,4,'/sys/dept','','C',1,''),(904,'个人信息',9,0,'/profile','','C',1,''),(1000,'查询',900,1,'/api/user/list','','F',1,''),(1001,'新增',900,2,'/api/user/add','','F',1,''),(1002,'修改',900,3,'/api/user/edit','','F',1,''),(1003,'删除',900,4,'/api/user/del','','F',1,''),(1005,'解除锁定',900,6,'/api/user/unlock','','F',1,''),(1006,'重置密码',900,7,'/api/user/resetpwd','','F',1,''),(1007,'查询',901,1,'/api/role/list','','F',1,''),(1008,'新增',901,2,'/api/role/add','','F',1,''),(1009,'修改',901,3,'/api/role/edit','','F',1,''),(1010,'删除',901,4,'/api/role/del','','F',1,''),(1012,'查询',902,1,'/api/menu/list','','F',1,''),(1013,'新增',902,2,'/api/menu/add','','F',1,''),(1014,'修改',902,3,'/api/menu/edit','','F',1,''),(1015,'删除',902,4,'/api/menu/del','','F',1,''),(1016,'查询',903,1,'/api/department/list','','F',1,''),(1017,'新增',903,2,'/api/department/add','','F',1,''),(1018,'修改',903,3,'/api/department/edit','','F',1,''),(1019,'删除',903,4,'/api/department/del','','F',1,''),(1020,'修改密码',904,1,'/api/user/updatepwd','','F',1,''),(1021,'选择',902,0,'/api/menu/treelist','','F',1,''),(1022,'查询',300,1,'/api/course/list','','F',1,''),(1023,'新增',300,2,'/api/course/add','','F',1,''),(1024,'修改',300,3,'/api/course/edit','','F',1,''),(1025,'删除',300,4,'/api/course/del','','F',1,''),(1026,'查看',302,1,'/api/course/my','','F',1,''),(1027,'查看',301,1,'/api/course/avalible','','F',1,''),(1028,'更新',301,1,'/api/course/refresh','','F',1,''),(1029,'选课',301,1,'/api/course/select','','F',1,''),(1030,'查看学生',300,1,'/api/course/students','','F',1,''),(1031,'操作',300,1,'/api/course/confirm','','F',1,''),(1032,'学期管理',3,0,'/course/term','','C',1,''),(1033,'查询',1032,0,'/api/term/list','','F',1,''),(1034,'新增',1032,2,'/api/term/add','','F',1,''),(1035,'修改',1032,3,'/api/term/edit','','F',1,''),(1036,'删除',1032,4,'/api/term/del','','F',1,''),(1037,'学校宿舍管理',0,4,'/schooldormitory ','','M',1,'宿舍管理'),(1038,'添加',1038,0,'/api/college/add','','F',1,''),(1039,'学生管理',1037,0,'/schooldormitory/student','','C',1,''),(1040,'宿舍管理',1037,0,'/schooldormitory/dormitory','','C',1,''),(1044,'删除',1040,0,'/api/dormitory/del','','F',1,''),(1045,'编辑',1040,0,'/api/dormitory/edit','','F',1,''),(1047,'添加',1040,0,'/api/dormitory/add','','F',1,''),(1048,'查询',1040,0,'/api/dormitory/list','','F',1,''),(1049,'编辑',1038,0,'/api/college/edit','','F',1,''),(1050,'删除',1038,0,'/api/college/del','','F',1,''),(1051,'书院管理',1037,0,'/schooldormitory/college','','C',1,''),(1052,'查询',1051,0,'/api/college/list','','F',1,''),(1053,'添加',1051,0,'/api/college/add','','F',1,''),(1054,'编辑',1051,0,'/api/college/edit','','F',1,''),(1055,'删除',1051,0,'/api/college/del','','F',1,''),(1056,'全查',1051,0,'/api/college/all','','F',1,''),(1057,'部门全查',1051,0,'/api/dept/all','','F',1,''),(1058,'查询',1039,0,'/api/student/list','','F',1,''),(1059,'添加',1039,0,'/api/student/add','','F',1,''),(1060,'编辑',1039,0,'/api/student/edit','','F',1,''),(1061,'删除',1039,0,'/api/student/del','','F',1,''),(1062,'床位修改',1039,0,'/api/bed/edit','','F',1,''),(1063,'退出床位',1039,0,'/api/bed/del','','F',1,'');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
                        `id` int NOT NULL AUTO_INCREMENT COMMENT '角色ID',
                        `role_name` varchar(60) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
                        `rules` text COLLATE utf8mb4_general_ci,
                        `remark` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '备注',
                        `status` int NOT NULL DEFAULT '1' COMMENT '部门状态',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'管理员','3,300,1024,1031,1030,1025,1023,1022,301,1029,1028,1027,302,1026,1032,1034,1036,1033,1035,9,903,1019,1018,1017,1016,904,1020,902,1021,1015,1014,1013,1012,901,1010,1009,1007,1008,900,1000,1001,1002,1003,1005,1006,1,100,1037,1051,1053,1054,1055,1056,1057,1052,1040,1044,1048,1047,1045,1039,1061,1060,1059,1058','管理员',1),(2,'教师','1,100,3,300,1024,1025,1030,1031,1022,1023,9,904,1020','课程负责人',1),(3,'学生','3,302,1026,301,1029,1028,1027,9,904,1020,1,100','学生',1);
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `term`
--

DROP TABLE IF EXISTS `term`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `term` (
                        `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID',
                        `term_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '学期名称',
                        `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '备注',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `term`
--

LOCK TABLES `term` WRITE;
/*!40000 ALTER TABLE `term` DISABLE KEYS */;
INSERT INTO `term` VALUES (5,'2022-2023-1','2022-2023-1');
/*!40000 ALTER TABLE `term` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
                        `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `role_id` int NOT NULL DEFAULT '0' COMMENT '角色ID',
                        `username` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '登录名（学号、工号）',
                        `realname` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '姓名',
                        `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '密码',
                        `status` int NOT NULL DEFAULT '1' COMMENT '状态  0：停用   1：正常',
                        `grade` int NOT NULL DEFAULT '0' COMMENT '年级',
                        `class_name` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '班级',
                        `pwd_question` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '密码问题',
                        `pwd_answer` varchar(250) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '密码答案',
                        `token` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL DEFAULT '' COMMENT '登录凭证',
                        `login_failure` int NOT NULL DEFAULT '0' COMMENT '连续密码输入错误次数',
                        `prevent_time` int NOT NULL DEFAULT '0' COMMENT '锁定时间',
                        `dep_id` int NOT NULL DEFAULT '0' COMMENT '部门ID',
                        PRIMARY KEY (`id`) USING BTREE,
                        UNIQUE KEY `username` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,1,'admin','管理员','123123',1,0,'','x','y','27b0e46e-8cba-4dfe-ba36-9821601a8a33',0,1733456661,148),(2,2,'teacher','教师','123456',1,0,'','','','d304aa6f-c155-4f7e-ac01-33ab9fc75914',0,0,114),(3,3,'student','学生','123456',1,0,'计算机1班','','','40ef0b10-23f9-463b-9df3-60f28821aced',0,0,114),(4,3,'20231001','张三','password1',1,0,'计算机1班','我的生日','0101','',0,0,1),(5,3,'20231002','李四','password2',1,0,'计算机1班','我的宠物名字','旺财','',0,0,1),(6,3,'20231003','王五','password3',1,0,'计算机1班','我的小学名字','育才小学','',0,0,1),(7,3,'20231004','赵六','password4',1,0,'计算机2班','我的最爱颜色','蓝色','',0,0,1),(8,3,'20231005','孙七','password5',1,0,'计算机2班','我的家乡','北京','',0,0,1),(9,3,'20231006','周八','password6',1,0,'计算机2班','我最爱的老师名字','李老师','',0,0,1),(10,3,'20231007','吴九','password7',1,0,'计算机3班','我喜欢的运动','篮球','',0,0,1),(11,3,'20231008','郑十','password8',1,0,'计算机3班','我喜欢的食物','苹果','',0,0,1),(12,3,'20231009','王十一','password9',1,0,'计算机3班','我的车牌号码','京A12345','',0,0,1),(13,3,'20231010','赵十二','password10',1,0,'计算机3班','我的幸运数字','7','',0,0,1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-27 23:40:40