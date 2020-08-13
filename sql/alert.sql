-- 用户地址相关
ALTER TABLE `ims_weixinmao_jz_address`
ADD COLUMN `lat`  decimal(10,6) NULL COMMENT '纬度',
ADD COLUMN `lng`  decimal(10,6) NULL COMMENT '经度';

-- 订单相关
ALTER TABLE `ims_weixinmao_jz_order`
ADD COLUMN  `note_avatar_url` varchar(200) DEFAULT NULL COMMENT '技师图片(冗余)',
ADD COLUMN  `note_name` varchar(200) DEFAULT NULL COMMENT '技师名字(冗余)',
ADD COLUMN `content_id` int(10) NOT NULL COMMENT '项目Id',
ADD COLUMN `content_name` varchar(200) NOT NULL COMMENT '服务项目名字',
ADD COLUMN `content_thumb` varchar(200) DEFAULT NULL COMMENT '项目图(冗余)',
ADD COLUMN `sub_time` varchar(50) NOT NULL COMMENT '预约时间',
ADD COLUMN `traffic_type` int(10) DEFAULT 0 COMMENT '交通方式0:出租 1:公交/地铁',
ADD COLUMN `traffic_reckon_mile` decimal(10,2) null COMMENT '估算公里数',
ADD COLUMN `traffic_price` decimal(10,2) null  COMMENT '交通费用',
ADD COLUMN `daddress` varchar(100) null  COMMENT '详细地址',
ADD COLUMN `updatetime` int(10) null  COMMENT '更新时间';
