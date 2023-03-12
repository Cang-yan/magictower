DROP TABLE IF EXISTS user;
CREATE TABLE user(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    account_id VARCHAR(10) NOT NULL   COMMENT '用户账号' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    password VARCHAR(255)    COMMENT '密码' ,
    name VARCHAR(255)    COMMENT '用户名' ,
    notes VARCHAR(255)    COMMENT '个性签名' ,
    phone VARCHAR(255)    COMMENT '手机号' ,
    real_name VARCHAR(255)    COMMENT '真名' ,
    identity VARCHAR(255)    COMMENT '身份证' ,
    permission INT    COMMENT '权限值，由枚举统一确定' ,
    head VARCHAR(255)    COMMENT '头像' ,
    energy INT    COMMENT '耐力值' ,
    chainWallet VARCHAR(255)    COMMENT '区块链钱包' ,
    PRIMARY KEY (id)
)  COMMENT = '用户表';

DROP TABLE IF EXISTS meta_nft;
CREATE TABLE meta_nft(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    url VARCHAR(255)    COMMENT '地址' ,
    nickname VARCHAR(255)    COMMENT '名字' ,
    introduce VARCHAR(255)    COMMENT '作品介绍' ,
    contract_address VARCHAR(255)    COMMENT '合约地址' ,
    contract_agreement VARCHAR(255)    COMMENT '合约协议' ,
    type INT    COMMENT '类型，枚举 英雄 装备' ,
    profession_type INT    COMMENT '职业枚举' ,
    family_id VARCHAR(32)    COMMENT '系列' ,
    family_name VARCHAR(255)    COMMENT '系列名字' ,
    rank_id VARCHAR(32)    COMMENT '品级id' ,
    author_id VARCHAR(32)    COMMENT '作者信息id' ,
    stock_id VARCHAR(32) NOT NULL   COMMENT '库存id' ,
    PRIMARY KEY (id)
)  COMMENT = '藏品';

DROP TABLE IF EXISTS market;
CREATE TABLE market(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    relation_id VARCHAR(32) NOT NULL   COMMENT '关联id，图片id或者盲盒id' ,
    price DOUBLE    COMMENT '价格' ,
    is_for_sell INT(1) NOT NULL   COMMENT '是否出售，0不卖 1卖' ,
    sell_time DATETIME    COMMENT '开售时间' ,
    family_id VARCHAR(32) NOT NULL   COMMENT '系列Id' ,
    nft_type INT(1)   DEFAULT 0 COMMENT '藏品类型' ,
    now_count INT    COMMENT '现在还有的库存' ,
    limit_purchase INT    COMMENT '限购数量' ,
    version INT    COMMENT '版本号，用来设置乐观锁' ,
    PRIMARY KEY (id)
)  COMMENT = '商城';

DROP TABLE IF EXISTS family;
CREATE TABLE family(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    title VARCHAR(255)    COMMENT '系列名称' ,
    introduction VARCHAR(255)    COMMENT '介绍' ,
    url VARCHAR(255)    COMMENT '系列图片' ,
    contract_address VARCHAR(255)    COMMENT '合约地址' ,
    contract_agreement VARCHAR(255)    COMMENT '合约协议 ECS-711' ,
    PRIMARY KEY (id)
)  COMMENT = '系列';

DROP TABLE IF EXISTS exchange_records;
CREATE TABLE exchange_records(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    meta_nft_id VARCHAR(32) NOT NULL   COMMENT '图片Id' ,
    uu_tag INT    COMMENT '编号' ,
    host_user_id VARCHAR(255)    COMMENT '交易藏品持有者，管理员root，用户' ,
    guest_user_id VARCHAR(32) NOT NULL   COMMENT '购买人,受赠人' ,
    price DOUBLE    COMMENT '价格，转增为0' ,
    type INT    COMMENT '类型枚举 购买的转增的二级交易' ,
    PRIMARY KEY (id)
)  COMMENT = '藏品交易记录';

DROP TABLE IF EXISTS notify;
CREATE TABLE notify(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    title VARCHAR(255)    COMMENT '标题' ,
    content VARCHAR(255)    COMMENT '内容' ,
    PRIMARY KEY (id)
)  COMMENT = '公告通知';

DROP TABLE IF EXISTS ware_house;
CREATE TABLE ware_house(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    uu_tag INT    COMMENT '生成的编号' ,
    meta_nft_id VARCHAR(32) NOT NULL   COMMENT '图片id' ,
    family_id VARCHAR(32)    COMMENT '系列id' ,
    user_status INT(1)   DEFAULT 9 COMMENT '状态 0已获得    2 空投的  3合成的  4寄售 9无意义' ,
    nft_status INT(1)   DEFAULT 0 COMMENT '0未买  1是已买   2已锁定给管理员，查个人仓库的时候先查这个' ,
    is_reserve INT(1)    COMMENT '0是不预留  1是预留' ,
    type INT    COMMENT '藏品类型  枚举  武器道具徽章等' ,
    profession_type INT    COMMENT '职业枚举' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    PRIMARY KEY (id)
)  COMMENT = '藏品仓库';

DROP TABLE IF EXISTS prop;
CREATE TABLE prop(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    url VARCHAR(255)    COMMENT '道具图片' ,
    name VARCHAR(255)    COMMENT '道具名称' ,
    type INT    COMMENT '道具类型  枚举' ,
    introduce VARCHAR(255)    COMMENT '道具介绍' ,
    PRIMARY KEY (id)
)  COMMENT = '道具';

DROP TABLE IF EXISTS prop_hourse;
CREATE TABLE prop_hourse(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    prop_id VARCHAR(32) NOT NULL   COMMENT '道具id' ,
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    count INT    COMMENT '数量' ,
    PRIMARY KEY (id)
)  COMMENT = '道具仓库';

DROP TABLE IF EXISTS invitation_records;
CREATE TABLE invitation_records(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    host_id VARCHAR(32) NOT NULL   COMMENT '邀请人id' ,
    invited_id VARCHAR(32) NOT NULL   COMMENT '被邀请的人id' ,
    is_identified INT(1)   DEFAULT 0 COMMENT '是否被实名认证' ,
    PRIMARY KEY (id)
)  COMMENT = '邀请记录';

DROP TABLE IF EXISTS author;
CREATE TABLE author(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    name VARCHAR(255)    COMMENT '作者名字' ,
    introduce VARCHAR(255)    COMMENT '作者介绍' ,
    head VARCHAR(255)    COMMENT '作者头像' ,
    PRIMARY KEY (id)
)  COMMENT = '作品作者';

DROP TABLE IF EXISTS user_auth;
CREATE TABLE user_auth(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    auth INT    COMMENT '权限值' ,
    role INT    COMMENT '角色 枚举' ,
    PRIMARY KEY (id)
)  COMMENT = '用户权限值';

DROP TABLE IF EXISTS piece;
CREATE TABLE piece(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    meta_nft_id VARCHAR(32)    COMMENT '藏品id' ,
    PRIMARY KEY (id)
)  COMMENT = '碎片';

DROP TABLE IF EXISTS piece_house;
CREATE TABLE piece_house(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    piece_id VARCHAR(32)    COMMENT '碎片id' ,
    count INT    COMMENT '碎片数量' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    PRIMARY KEY (id)
)  COMMENT = '碎片仓库';

DROP TABLE IF EXISTS rank_sort;
CREATE TABLE rank_sort(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    name VARCHAR(10)    COMMENT '品质名字' ,
    quality_code INT    COMMENT '品质代号，好排序' ,
    PRIMARY KEY (id)
)  COMMENT = '品质分级,默认概率写进枚举';

DROP TABLE IF EXISTS piece_com_rule;
CREATE TABLE piece_com_rule(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    piece_id VARCHAR(32)    COMMENT '碎片id' ,
    count_need INT    COMMENT '所需数量' ,
    meta_nft_id VARCHAR(32) NOT NULL   COMMENT '合成后的nft id' ,
    PRIMARY KEY (id)
)  COMMENT = '碎片合成规则';

DROP TABLE IF EXISTS piece_com_records;
CREATE TABLE piece_com_records(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    piece_rule_id VARCHAR(32) NOT NULL   COMMENT '合成id' ,
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    meta_nft_id VARCHAR(32)    COMMENT '合成后的nft  id' ,
    PRIMARY KEY (id)
)  COMMENT = '合成记录';

DROP TABLE IF EXISTS badge_house;
CREATE TABLE badge_house(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    meta_nft_id VARCHAR(32) NOT NULL   COMMENT '徽章id' ,
    user_id VARCHAR(32) NOT NULL   COMMENT '用户id' ,
    PRIMARY KEY (id)
)  COMMENT = '徽章仓库';

DROP TABLE IF EXISTS blind_box_pool;
CREATE TABLE blind_box_pool(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    url VARCHAR(255)    COMMENT '盲盒图片' ,
    nickname VARCHAR(255)    COMMENT '盲盒名字' ,
    introduce VARCHAR(255)    COMMENT '作品介绍' ,
    contract_address VARCHAR(255)    COMMENT '合约地址' ,
    contract_agreement VARCHAR(255)    COMMENT '合约协议' ,
    family_id VARCHAR(32)    COMMENT '系列id' ,
    family_name VARCHAR(255)    COMMENT '系列名字' ,
    author_id VARCHAR(32)    COMMENT '作品信息id' ,
    stock_id VARCHAR(32) NOT NULL   COMMENT '库存id' ,
    PRIMARY KEY (id)
)  COMMENT = '盲盒抽奖池子';

DROP TABLE IF EXISTS stock;
CREATE TABLE stock(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    pre_count INT   DEFAULT 0 COMMENT '最初库存' ,
    now_count INT   DEFAULT 0 COMMENT '现在还能卖的库存' ,
    reserve_count INT   DEFAULT 0 COMMENT '预留库存' ,
    PRIMARY KEY (id)
)  COMMENT = '库存';

DROP TABLE IF EXISTS user_invite;
CREATE TABLE user_invite(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    code VARCHAR(32)    COMMENT '邀请码' ,
    PRIMARY KEY (id)
)  COMMENT = '用户邀请码';

DROP TABLE IF EXISTS control_house;
CREATE TABLE control_house(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    ware_house_id VARCHAR(32)    COMMENT '藏品仓库id' ,
    PRIMARY KEY (id)
)  COMMENT = '管理员仓库';

DROP TABLE IF EXISTS box_meta;
CREATE TABLE box_meta(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    blind_box_id VARCHAR(32)    COMMENT '盲盒id' ,
    meta_nft_id VARCHAR(32)    COMMENT '藏品id' ,
    rank_name VARCHAR(255)    COMMENT '品质' ,
    meta_nft_price DOUBLE    COMMENT '藏品原价' ,
    rank_possibility DOUBLE    COMMENT '品质概率' ,
    real_possibility DOUBLE    COMMENT '实际概率' ,
    stock_id VARCHAR(32)    COMMENT '库存id' ,
    PRIMARY KEY (id)
)  COMMENT = '盲盒藏品绑定关系';

DROP TABLE IF EXISTS exchange_box_records;
CREATE TABLE exchange_box_records(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    blind_box_id VARCHAR(255)    COMMENT '盲盒Id' ,
    host_user_id VARCHAR(32)    COMMENT '交易盲盒持有者id' ,
    guest_user_id VARCHAR(32)    COMMENT '转增人，购买人Id' ,
    price DOUBLE    COMMENT '价格,转增为0' ,
    type INT    COMMENT '枚举  购买的，转增的，二级的 空投的' ,
    count INT    COMMENT '数量' ,
    PRIMARY KEY (id)
)  COMMENT = '盲盒交易记录';

DROP TABLE IF EXISTS box_house;
CREATE TABLE box_house(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    blind_box_id VARCHAR(32)    COMMENT '盲盒id' ,
    count INT   DEFAULT 1 COMMENT '盲盒数量' ,
    user_id VARCHAR(32)    COMMENT '用户Id' ,
    PRIMARY KEY (id)
)  COMMENT = '盲盒仓库';

DROP TABLE IF EXISTS box_uncover_records;
CREATE TABLE box_uncover_records(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    blind_box_id VARCHAR(32)    COMMENT '盲盒Id' ,
    meta_nft_id VARCHAR(32)    COMMENT '图片id' ,
    count INT    COMMENT '数量' ,
    PRIMARY KEY (id)
)  COMMENT = '盲盒抽取记录';

DROP TABLE IF EXISTS invatition_rank;
CREATE TABLE invatition_rank(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    user_id VARCHAR(32)    COMMENT '用户Id' ,
    count INT   DEFAULT 0 COMMENT '邀新人数' ,
    PRIMARY KEY (id)
)  COMMENT = '邀新排行榜';

DROP TABLE IF EXISTS exchange_rank;
CREATE TABLE exchange_rank(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    total_money DOUBLE   DEFAULT 0 COMMENT '花费总钱数' ,
    PRIMARY KEY (id)
)  COMMENT = '交易排行榜';

DROP TABLE IF EXISTS buy_order;
CREATE TABLE buy_order(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    price DOUBLE    COMMENT '金额' ,
    market_id VARCHAR(32)    COMMENT '商品id' ,
    count INT    COMMENT '购买数量' ,
    status INT(1)   DEFAULT 0 COMMENT '状态  0未付款 1已完成 2超时已取消' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    PRIMARY KEY (id)
)  COMMENT = '购买订单';

DROP TABLE IF EXISTS benefit_badge;
CREATE TABLE benefit_badge(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    badge_nft_id VARCHAR(32)    COMMENT '徽章id' ,
    benefit_prebuy_count INT    COMMENT '提前购优惠次数' ,
    is_all INT(1)   DEFAULT 1 COMMENT '是否全局生效' ,
    family_id VARCHAR(32)    COMMENT '指定系列生效的系列id' ,
    family_name VARCHAR(255)    COMMENT '指定系列生效的系列名字' ,
    PRIMARY KEY (id)
)  COMMENT = '徽章权益表';

DROP TABLE IF EXISTS badge_nft;
CREATE TABLE badge_nft(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    url VARCHAR(255)    COMMENT '图片url' ,
    nickname VARCHAR(255)    COMMENT '名字' ,
    introduce VARCHAR(255)    COMMENT '作品介绍' ,
    contract_address VARCHAR(255)    COMMENT '合约地址' ,
    contract_agreement VARCHAR(255)    COMMENT '合约协议' ,
    family_id VARCHAR(32)    COMMENT '系列' ,
    family_name VARCHAR(255)    COMMENT '系列名字' ,
    author_id VARCHAR(255)   DEFAULT 'root' COMMENT '作者id  默认平台root' ,
    stock_id VARCHAR(32)    COMMENT '库存id' ,
    PRIMARY KEY (id)
)  COMMENT = '徽章藏品';

DROP TABLE IF EXISTS benefit_user;
CREATE TABLE benefit_user(
    id VARCHAR(32) NOT NULL   COMMENT '主键' ,
    create_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间' ,
    update_time DATETIME   DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间' ,
    user_id VARCHAR(32)    COMMENT '用户id' ,
    benefit_prebuy_count INT    COMMENT '总优先购买次数' ,
    PRIMARY KEY (id)
)  COMMENT = '用户获得的权益表';

