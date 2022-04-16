/*
用于存储系统中所有的MySQL语句
*/

# 学员信息表
drop table if exists `student_info`;
create table `student_info`
(
    `id`           bigint      not null comment 'id',
    `phone`        varchar(11) not null comment '手机号',
    `name`         varchar(50) comment '姓名',
    `gender`       tinyint default 0 comment '1代表男，0代表女',
    `salt`         varchar(50) not null comment 'md5盐值',
    `password`     char(32)    not null comment '密码',
    `disable_flag` tinyint default 0 comment '1代表禁用',
    `head_pic`     varchar(100) comment '头像url',
    primary key (`id`),
    unique key `phone_unique` (`phone`)
) engine = innodb
  default charset = utf8mb4 comment ='学员信息表';

# 插入测试数据
insert into student_info(id, phone, name, gender, salt, password, disable_flag)
values (1, '13500000001', 'test1', 0, 'QWEZ12', '123', 0),
       (2, '13500000002', 'test2', 0, 'QWEZ13', '124', 0),
       (3, '13500000003', 'test3', 0, 'QWEZ14', '125', 0),
       (4, '13500000004', 'test4', 0, 'QWEZ15', '126', 0),
       (5, '13500000005', 'test5', 0, 'QWEZ16', '127', 0);

# 年级表
drop table if exists `grade`;
create table `grade`
(
    `id`    bigint      not null comment 'id',
    `name`  varchar(50) not null comment '年级名称',
    `order` int         not null comment '排序权重，可以用年级数字形式表示',
    primary key (`id`),
    unique key `name_unique` (`name`)
) engine = innodb
  default charset = utf8mb4 comment = '年级信息';

# 插入测试数据
insert into grade (`id`, `name`, `order`)
values (1, '一年级', 1),
       (2, '二年级', 2),
       (3, '三年级', 3),
       (4, '四年级', 4),
       (5, '五年级', 5),
       (6, '六年级', 6),
       (7, '初一', 7),
       (8, '初二', 8),
       (9, '初三', 9);

# 科目表
drop table if exists `subject`;
create table `subject`
(
    `id`       bigint      not null comment 'id',
    `name`     varchar(50) not null comment '科目名称',
    `grade_id` bigint      not null comment '所属年级id',
    primary key (`id`),
    foreign key (`grade_id`) references grade (`id`)
) engine = innodb
  default charset = utf8mb4 comment = '科目信息';

# 插入测试数据
insert into subject(`id`, `name`, `grade_id`)
values (1, '语文', 1),
       (2, '语文', 2),
       (3, '语文', 3),
       (4, '语文', 4),
       (5, '数学', 1),
       (6, '数学', 2),
       (7, '数学', 3),
       (8, '数学', 4),
       (9, '英语', 1);

# 课程信息表
drop table if exists `course`;
create table `course`
(
    `id`          bigint       not null comment 'id',
    `name`        varchar(100) not null comment '课程名称',
    `cover`       varchar(100) comment '课程封面',
    `teacher`     varchar(50)  not null comment '课程讲师',
    `subject_id`  bigint       not null comment '课程所属科目',
    `description` text         not null comment '课程简介',
    `sort`        int          not null comment '排序权重',
    `status`      tinyint(1) default 1 comment '0为下架，1为上架',
    `certificate` varchar(100) comment '结课证书',
    `send_word`   varchar(100) comment '结课寄语',
    primary key (`id`),
    foreign key (`subject_id`) references subject (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='课程信息表';

# 插入课程信息测试数据
insert into course(id, name, cover, teacher, subject_id, description, sort, certificate, send_word)
values (1, '测试课程1', null, '测试教师1', 1, '这是第1个测试课程', 1, null, '恭喜你完成测试课程1'),
       (2, '测试课程2', null, '测试教师2', 2, '这是第2个测试课程', 2, null, '恭喜你完成测试课程2');


# 课程章节表
drop table if exists `course_section`;
create table `course_section`
(
    `id`        bigint       not null comment 'id',
    `section`   int          not null comment '章节数',
    `title`     varchar(100) not null comment '章节名称',
    `course_id` bigint       not null comment '章节所属课程',
    primary key (`id`),
    foreign key (`course_id`) references course (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='课程章节表';

# 插入测试数据
insert into course_section(id, section, title, course_id)
VALUES (1, 1, '我是测试的第一章', 1),
       (2, 2, '我是测试的第二章', 1);


# 课程课时表
drop table if exists `course_period`;
create table `course_period`
(
    `id`          bigint       not null comment 'id',
    `period`      int          not null comment '课时数',
    `title`       varchar(100) not null comment '课时名称',
    `course_id`   bigint       not null comment '课时所属课程',
    `section_id`  bigint       not null comment '课时所属章节',
    `video`       varchar(100) comment '视频内容',
    `price`       double       not null default 0.0 comment '课时价格，默认为0代表免费',
    `description` text         not null comment '课时简介',
    primary key (`id`),
    foreign key (`course_id`) references course (`id`),
    foreign key (`section_id`) references course_section (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='课程课时表';

insert into course_period(id, period, title, course_id, section_id, video, price, description)
values (1, 1, '我是测试的第一章节第一课时', 1, 1, '', 0, '本课时主要介绍的是xxx1'),
       (2, 2, '我是测试的第一章节第二课时', 1, 1, '', 10, '本课时主要介绍的是xxx2');

# 课程学员表 唯一索引student_id和course_id字段
drop table if exists `course_member`;
create table `course_member`
(
    `id`            bigint   not null comment 'id',
    `course_id`     bigint   not null comment '课程id',
    `student_id`    bigint   not null comment '学员id',
    `finish_course` int      not null default 0 comment '已学习课程',
    `join_date`     date     not null comment '加入日期',
    `join_time`     datetime not null comment '加入时间',
    primary key (`id`),
    foreign key (`course_id`) references course (`id`),
    foreign key (`student_id`) references student_info (`id`),
    unique key student_course_uk (student_id, course_id)
) engine = innodb
  default charset = utf8mb4 comment ='课程学员表';

# 插入测试数据
insert into course_member(id, course_id, student_id, finish_course, join_date, join_time)
values (1, 1, 1, 0, current_date(), now()),
       (2, 1, 2, 0, current_date(), now()),
       (3, 1, 3, 0, current_date(), now()),
       (4, 2, 1, 0, current_date(), now());

# 课程评论表
drop table if exists `course_comment`;
create table `course_comment`
(
    `id`          bigint   not null comment 'id',
    `course_id`   bigint   not null comment '课程id',
    `student_id`  bigint comment '学员id，如果为管理员发布则为null',
    `content`     text     not null comment '评论内容',
    `reply_id`    bigint comment '回复评论id，为null代表主评论',
    `top_flag`    boolean  not null default false comment '置顶标识',
    `elite_flag`  boolean  not null default false comment '精华标识',
    `create_date` date     not null comment '创建日期',
    `create_time` datetime not null comment '创建时间',
    primary key (`id`),
    foreign key (`course_id`) references course (`id`),
    foreign key (`student_id`) references student_info (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='课程评论表';

# 插入测试数据
insert into course_comment(id, course_id, student_id, content, reply_id, create_date, create_time)
values (1, 1, 1, '测试评论111', null, current_date(), now()),
       (2, 1, 2, '测试评论222', null, current_date(), now()),
       (3, 1, null, '管理员评论11', 1, current_date(), now()),
       (4, 2, 1, '测试评论333', null, current_date(), now());

# 题目表 题目类型为单选题、多选题、判断题、填空题、问答题
# 调整试题表type类型约定参数，新增4代表多选题
drop table if exists `question`;
create table `question`
(
    `id`            bigint     not null comment 'id',
    `subject_id`    bigint     not null comment '所属科目id',
    `course_id`     bigint     not null comment '所属课程id',
    `section_id`    bigint     not null comment '所属章节id',
    `type`          tinyint(2) not null comment '题目类型,0:单选题，1:判断题，2:填空题，3:问答题，4:多选题',
    `content`       text       not null comment '题干',
    `options`       json comment '选择/判断题选项',
    `answer_text`   text comment '填空/问答题答案',
    `answer_option` json comment '选择/判断题答案',
    `analysis`      text comment '题目解析',
    `create_time`   datetime   not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='题目表';

alter table question
    modify answer_option varchar(50) null comment '选择/判断题答案';



# 插入测试内容
insert into question(id, subject_id, course_id, section_id,
                     type, content, options, answer_text, answer_option,
                     analysis, create_time)
VALUES (1, 2, 1, 1, 0, '我是测试的一个选择题', '{
  "A": "选项1",
  "B": "选项2"
}', null, '{
  "A": "success"
}', null, now()),
       (2, 2, 1, 2, 2, '我是测试的一个填空题', null, '我是测试的答案内容', null, '我是测试的解析内容', now());


# 考试试卷信息表
DROP TABLE IF EXISTS `exam_paper`;
CREATE TABLE `exam_paper`
(
    `id`             bigint       not null comment 'id',
    `name`           varchar(100) not null comment '试卷名称',
    `total_score`    int          not null default 0 comment '试卷总分',
    `subject_id`     bigint       not null comment '所属科目id',
    `course_id`      bigint       not null comment '所属课程id',
    `sort`           int          not null default 0 comment '排序权重',
    `join_count`     int          not null default 0 comment '参与人数',
    `exam_time`      int          not null comment '考试时长[分钟]',
    `check_count`    int          not null default 0 comment '已批改试卷数量',
    `status`         tinyint(1)   not null default 0 comment '是否发布',
    `question_count` int          not null default 0 comment '试题数量',
    `auto_check`     tinyint(1)   not null default 0 comment '是否机器判题',
    `remark`         varchar(100) comment '备注',
    `start_date`     date         not null comment '考试开始日期',
    `end_date`       date         not null comment '考试结束日期',
    `create_time`    datetime     not null comment '创建时间',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='考试试卷信息表';

# 插入测试数据
insert into exam_paper(`id`, `name`, `total_score`, `subject_id`, `course_id`,
                       `sort`, `join_count`, `exam_time`, `check_count`, `status`,
                       `question_count`, `auto_check`, `remark`, `start_date`,
                       `end_date`, `create_time`)
values (1, '测试试卷1', 80, 1, 1, 1, 0, 60, 0, 0, 0, 0, null, current_date() + 2, current_date() + 5, now()),
       (2, '测试试卷2', 100, 1, 1, 2, 0, 30, 0, 1, 0, 0, '备注', current_date(), current_date() + 2, now());


# 试卷-试题关联表
drop table if exists `paper_question`;
create table `paper_question`
(
    `id`          bigint   not null comment 'id',
    `question_id` bigint   not null comment '试题id',
    `paper_id`    bigint   not null comment '试卷id',
    `sort`        int      not null default 0 comment '题号',
    `create_time` datetime not null comment '创建时间',
    PRIMARY KEY (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='试卷-试题关联表';

# 插入测试数据
insert into paper_question (id, question_id, paper_id, sort, create_time)
values (1, 1, 1, 1, now()),
       (2, 2, 1, 2, now());

# 考试信息表
drop table if exists `exam_info`;
create table `exam_info`
(
    `id`             bigint     not null comment 'id',
    `student_id`     bigint     not null comment '考试学员id',
    `score`          int        not null default 0 comment '总得分',
    `paper_id`       bigint     not null comment '试卷id',
    `student_answer` json comment '学员答案(题目id:答案的形式)',
    `check_list`     json comment '得分细则(题目id:得分的形式)',
    `check_flag`     tinyint(1) not null default 0 comment '批改状态(1为已批改，0为未批改)',
    `check_id`       bigint     not null default 0 comment '批改人ID(0代表机器)',
    `right_count`    int        not null default 0 comment '答对题数',
    `error_count`    int        not null default 0 comment '错题数',
    `status`         tinyint(1) not null default 0 comment '考试状态(0为未完成 1为已完成)',
    `exam_time`      int        not null default 0 comment '考试耗时(s)',
    `create_time`    datetime   not null comment '创建时间',
    primary key (`id`),
    index index_student (`student_id`),
    index paper_index (`paper_id`),
    unique key uq_student_paper (`student_id`, `paper_id`)
) engine = innodb
  default charset = utf8mb4 comment ='考试信息表';

# 插入测试数据
insert into exam_info (id, student_id, score, paper_id, student_answer,
                       check_list, check_flag, check_id, right_count,
                       error_count, status, exam_time, create_time)
values (1, 1, 0, 1, null, null, 0, 0, 0, 0, 0, 0, now()),
       (2, 2, 50, 1, '{
         "28565739711631360": "{\\"A\\": null, \\"B\\": true, \\"C\\": true, \\"D\\": null}"
       }',
        '{
          "28565739711631360": 50
        }', 1, 0, 1, 0, 1, 20, now());

# 轮播图配置表
drop table if exists `swipe_config`;
create table `swipe_config`
(
    `id`      bigint       not null comment 'id',
    `pic_url` varchar(100) not null comment '图片地址',
    `sort`    int          not null comment '顺序',
    PRIMARY KEY (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='轮播图设置';

# 插入测试数据
insert into swipe_config(id, pic_url, sort)
VALUES (1, 'https://ucloudteach.obs.cn-north-4.myhuaweicloud.com/banner1.jpg', 1),
       (2, 'https://ucloudteach.obs.cn-north-4.myhuaweicloud.com/banner2.jpg', 2);

# 功能菜单配置表
drop table if exists `grid_config`;
create table `grid_config`
(
    `id`      bigint       not null comment 'id',
    `name`    varchar(20)  not null comment '名称',
    `pic_url` varchar(100) not null comment '图标地址',
    `sort`    int          not null comment '顺序',
    PRIMARY KEY (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='菜单项设置';

# 插入测试数据
insert into grid_config(id,name,pic_url,sort)
values (1, '精品','https://ucloudteach.obs.cn-north-4.myhuaweicloud.com/fantastic.png',1),
       (2, '课程中心','https://ucloudteach.obs.cn-north-4.myhuaweicloud.com/course.png',2);


# 业务需要 课程表中增加字段
alter table `course`
    add total_member int default 0 not null comment '总学员数',
    add total_section int default 0 not null comment '总章节数',
    add create_time datetime not null comment '创建时间',
    add total_period int default 0 not null comment '总课时数';

# 管理员信息表
drop table if exists `admin`;
create table `admin`
(
    `id`       bigint      not null comment 'id',
    `username` varchar(50) comment '用户名',
    `salt`     varchar(50) not null comment 'md5盐值',
    `password` char(32)    not null comment '密码',
    `super`    tinyint default 0 comment '1是超级管理员 0是普通管理员',
    primary key (`id`),
    unique key uq_username (`username`)
) engine = innodb
  default charset = utf8mb4 comment ='管理员表';

# 插入超级管理员
insert into admin(id, username, salt, password, super_flag)
    VALUE (10001,'admin','QWE123!','f84bdbb9b4ca011e654e250ba3d18e2c',1);

# 学员信息表部分字段调整
alter table student_info modify gender tinyint(1) default 0 null comment '1代表男，0代表女';
alter table student_info modify disable_flag tinyint(1) default 0 null comment '1代表禁用';


# 业务需要 课程表中增加价格字段
alter table course
    add `price` int default 0 not null comment '价格，为0免费';

# 课程表价格字段类型调整
alter table course
    modify `price` decimal(8, 2) default 0 not null comment '价格，为0免费';

# 题目-试卷表中添加分值字段 管理员添加题目时需要填入题目分值
alter table paper_question
    add score int default 0 not null comment '试题分值';

# 试卷表增加及格分数字段
alter table exam_paper
    add pass_score int default 0 not null comment '及格分数';

create table `snapshot`
(
    `id`             bigint not null comment 'id',
    `create_date`    date   not null comment '快照生成日期',
    `course_count`   int    not null default 0 comment '课程数',
    `student_count`  int    not null default 0 comment '学员数',
    `paper_count`    int    not null default 0 comment '试卷数',
    `question_count` int    not null default 0 comment '试题数',
    primary key (`id`)
) engine = innodb
  default charset = 'utf8mb4' comment ='数据快照表';