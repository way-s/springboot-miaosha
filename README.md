## 慕课网springboot秒杀项目 慕课网 https://coding.imooc.com/class/chapter/168.html#Anchor 
### 缓慢更新中 ！！！！ 
### 新增加了登录注册页
` 更新至3-5商品详情，新增新的登录注册页和短信验证码登录功能。 `
### 数据库
` 
CREATE TABLE miaosha_user(
id bigint (20) NOT NULL COMMENT '用户ID,手机号码',
nickname varchar (255) NOT NULL,
passwor varchar (32) DEFAULT NULL COMMENT 'MDS(MDS(pass明文+固定salt) + salt)',
salt varchar (10) DEFAULT NULL,
head varchar (128) DEFAULT NULL COMMENT '头像，云存储ID',
register_date datetime DEFAULT NULL COMMENT '注册时间', 
last_login_date datetime DEFAULT NULL COMMENT '上次登录时间',
login_count int (11) DEFAULT '0' COMMENT '登录次数数',
PRIMARY KEY (id)
)

CREATE TABLE goods(
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT'商品ID',
goods_name varchar(16) DEFAULT NULL COMMENT'商品名称',
goods_title varchar(64) DEFAULT NULL COMMENT'商品标题',
goods_img varchar(64) DEFAULT NULL COMMENT'商品的图片',
goods_detail longtext COMMENT'商品的详情介绍',
goods_price decimal(10.2) DEFAULT '0.00' COMMENT'商品单价',
goods_stock int(11) DEFAULT '0' COMMENT'商品库存，-1表示设没有限制',PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE miaosha_goods(
id bigint(20) NOT NULL AUTO_INCREMENT COMMENT'秒杀的商品表',
goods_id bigint(20) DEFAULT NULL COMMENT'商品ld',
miaosha_price decimal(10.2) DEFAULT '0.00' COMMENT'秒杀价',
stock_count int(11) DEFAULT NULL COMMENT'库存数量',
start_date datetime DEFAULT NULL COMMENT'秒杀开始时间',
end_date datetime DEFAULT NULL COMMENT'秒杀结束时间',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

INSERT INTO miaosha_goods VALUES(1,1,0.01,4,'2020-3-05 15:18:00','2020-03-10 16:00:18')


CREATE TABLE order_info(
id bigint(20) NOT NULL AUTO_INCREMENT,
user_id bigint(20) DEFAULT NULL COMMENT'用户ID',
goods_id bigint(20) DEFAULT NULL COMMENT'商品ID',
delivery_addr_id bigint(20) DEFAULT NULL COMMENT'收获地址ID',
goods_name varchar(16) DEFAULT NULL COMMENT'冗余过来的商品名称',
goods_count int(11) DEFAULT '0' COMMENT'商品数量',
goods_price decimal(10.2) DEFAULT '0.00' COMMENT'商品单价',
order_channel tinyint(4)DEFAULT '0' COMMENT '1pc, 2android, 3ios', `status` tinyint(4) DEFAULT '0' COMMENT'订单状态,0新建未支付,1已支付,2已发货,3已收货,4已退款,5已完成',create_date datetime DEFAULT NULL COMMENT'订单的创建时间',
pay_date datetime DEFAULT NULL COMMENT'支付时间',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

CREATE TABLE miaosha_order(
id bigint(20) NOT NULL AUTO_INCREMENT,
user_id bigint(20) DEFAULT NULL COMMENT'用户ID',
order_id bigint(20) DEFAULT NULL COMMENT'订单ID',
goods_id bigint(20) DEFAULT NULL COMMENT'商品ID',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;


`
