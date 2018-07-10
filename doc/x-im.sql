/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.22 : Database - x-im
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`x-im` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `x-im`;

/*Table structure for table `user_account` */

DROP TABLE IF EXISTS `user_account`;

CREATE TABLE `user_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account` varchar(30) DEFAULT NULL COMMENT '帐号',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `disablestate` int(11) DEFAULT NULL COMMENT '禁用状态（0 启用  1 禁用）',
  `isdel` int(11) DEFAULT NULL COMMENT '是否删除（0未删除1已删除）',
  `createdate` datetime DEFAULT NULL COMMENT '创建日期',
  `updatedate` datetime DEFAULT NULL COMMENT '修改日期',
  `updateuser` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户帐号';

/*Data for the table `user_account` */

insert  into `user_account`(`id`,`account`,`password`,`disablestate`,`isdel`,`createdate`,`updatedate`,`updateuser`) values (3,'1','1',0,0,'2017-11-27 15:08:37','2017-11-27 15:08:37',NULL),(4,'2','2',0,0,'2017-11-27 18:00:14','2017-11-27 18:00:14',NULL),(5,'3','3',0,0,'2017-11-27 18:06:20','2017-11-27 18:06:20',NULL),(6,'4','4',0,0,'2017-11-27 18:12:11','2017-11-27 18:12:11',NULL),(7,'5','5',0,0,'2017-11-27 18:13:18','2017-11-27 18:13:18',NULL),(8,'6','6',0,0,'2017-11-27 18:13:58','2017-11-27 18:13:58',NULL),(9,'7','7',0,0,'2017-11-27 18:14:24','2017-11-27 18:14:24',NULL);

/*Table structure for table `user_department` */

DROP TABLE IF EXISTS `user_department`;

CREATE TABLE `user_department` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `count` int(11) DEFAULT NULL COMMENT '部门人数',
  `level` int(11) DEFAULT NULL COMMENT '等级',
  `parentid` bigint(20) DEFAULT NULL COMMENT '上级部门ID',
  `remark` text COMMENT '备注',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `updateuser` bigint(50) DEFAULT NULL COMMENT '修改人',
  `isdel` int(11) DEFAULT NULL COMMENT '是否删除（0否1是）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='部门';

/*Data for the table `user_department` */

insert  into `user_department`(`id`,`name`,`count`,`level`,`parentid`,`remark`,`createdate`,`updatedate`,`updateuser`,`isdel`) values (1,'总经办',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL),(2,'开发一部',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(3,'开发二部',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL),(4,'财务部',NULL,NULL,1,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `deptid` bigint(11) DEFAULT NULL COMMENT '部门',
  `name` varchar(50) DEFAULT NULL COMMENT '姓名',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `sex` int(11) DEFAULT NULL COMMENT '性别（0女 1男）',
  `birthday` varchar(50) DEFAULT NULL COMMENT '生日',
  `cardid` varchar(20) DEFAULT NULL COMMENT '身份证',
  `signature` varchar(20) DEFAULT NULL COMMENT '签名',
  `school` varchar(50) DEFAULT NULL COMMENT '毕业院校',
  `education` int(11) DEFAULT NULL COMMENT '学历',
  `address` varchar(200) DEFAULT NULL COMMENT '现居住地址',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `remark` text COMMENT '备注',
  `profilephoto` varchar(256) DEFAULT NULL COMMENT '个人头像',
  `createdate` datetime DEFAULT NULL COMMENT '创建时间',
  `createuser` bigint(20) DEFAULT NULL COMMENT '创建人',
  `updatedate` datetime DEFAULT NULL COMMENT '修改时间',
  `updateuser` bigint(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户信息表';

/*Data for the table `user_info` */

insert  into `user_info`(`id`,`uid`,`deptid`,`name`,`nickname`,`sex`,`birthday`,`cardid`,`signature`,`school`,`education`,`address`,`phone`,`email`,`remark`,`profilephoto`,`createdate`,`createuser`,`updatedate`,`updateuser`) values (1,3,1,'张三',NULL,NULL,NULL,NULL,'我的签名',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-11-27 15:08:41',3,'2017-11-27 15:08:41',3),(2,4,4,'李四',NULL,NULL,NULL,NULL,'Ta的签名',NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:00:14',4,'2017-11-27 18:00:14',4),(3,5,4,'王五',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:06:20',5,'2017-11-27 18:06:20',5),(4,6,3,'赵六',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:12:11',6,'2017-11-27 18:12:11',6),(5,7,3,'孙七',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:13:18',7,'2017-11-27 18:13:18',7),(6,8,1,'周八',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:13:58',8,'2017-11-27 18:13:58',8),(7,9,1,'吴九',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'../static/layui/images/0.jpg','2017-11-27 18:14:24',9,'2017-11-27 18:14:24',9);

/*Table structure for table `user_message` */

DROP TABLE IF EXISTS `user_message`;

CREATE TABLE `user_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `senduser` varchar(100) DEFAULT NULL COMMENT '发送人',
  `receiveuser` varchar(100) DEFAULT NULL COMMENT '接收人',
  `groupid` varchar(100) DEFAULT NULL COMMENT '群ID',
  `isread` int(11) DEFAULT NULL COMMENT '是否已读 0 未读  1 已读',
  `type` int(11) DEFAULT NULL COMMENT '类型 0 单聊消息  1 群消息',
  `content` text COMMENT '消息内容',
  `createuser` bigint(20) DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `updatedate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=116 DEFAULT CHARSET=utf8;

/*Data for the table `user_message` */

insert  into `user_message`(`id`,`senduser`,`receiveuser`,`groupid`,`isread`,`type`,`content`,`createuser`,`createdate`,`updatedate`) values (29,'DA0875E968961ED8E516B9209AEF424C','8','0',0,1,'测试',NULL,'2017-11-24 10:58:33','2017-11-24 10:58:33'),(30,'DA0875E968961ED8E516B9209AEF424C','8','0',0,1,'测试',NULL,'2017-11-24 10:58:56','2017-11-24 10:58:56'),(31,'269A734E0AC76F7BE6262124BE104BCC','8','0',0,1,'测试什么？\n',NULL,'2017-11-24 10:59:06','2017-11-24 10:59:06'),(112,'3','8','',1,0,'你好face[微笑] ',NULL,'2017-11-29 11:34:39','2017-11-29 11:34:39'),(113,'8','3','',1,0,'你好，在干什么呢face[疑问] ',NULL,'2017-11-29 11:35:31','2018-07-10 10:35:48'),(114,'3','8','',1,0,'img[/upload/img/temp/3ed0460ec-82fa-4f46-8373-ccfa1742bf89.jpg?1511926548410]',NULL,'2017-11-29 11:35:48','2017-11-29 11:35:48'),(115,'8','3','',1,0,'你发的这个图好挫face[偷笑] ',NULL,'2017-11-29 11:36:30','2018-07-10 10:35:48');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
