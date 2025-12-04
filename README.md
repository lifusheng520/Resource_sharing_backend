#  ** 后端仓库 README（resourceSharingBackend）**

```markdown
# 🔧 Backend — Campus Resource Sharing Platform  
*Spring Boot + MyBatis + MySQL + Spring Security

This is the **backend** service for the Campus Resource Sharing System.

---

##  Features
- Spring Boot REST API
- MyBatis ORM  
- MySQL relational storage  
- SESSION-based authentication  
- User roles: User / Admin  
- Exception handling  
- CORS configuration  
- Global response model  

---

##  Tech Stack
- Java  
- Spring Boot  
- MyBatis  
- MySQL  
- Maven  
- SESSION auth  
- DFA algorithm for illegal words filter
- email sender

---

## How to Run locally

### 1. Configure database
```sql
CREATE DATABASE campusResourceSharing DEFAULT CHARACTER SET utf8mb4;

use campusResourceSharing;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id integer(255) NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  password varchar(255) NOT NULL,
  enabled tinyint(1) NOT NULL DEFAULT 1,
  create_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE role (
  id integer(255) NOT NULL AUTO_INCREMENT,
  role varchar(100) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

#DROP TABLE IF EXISTS resource;
CREATE TABLE resource (
  id           integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id      integer(255) UNSIGNED NOT NULL,
  origin_name  VARCHAR(255) NOT NULL,
  type         VARCHAR(100) DEFAULT NULL,
  size         BIGINT UNSIGNED NOT NULL DEFAULT 0,
  disk_name    VARCHAR(255) NOT NULL,
  discipline   VARCHAR(64)  NOT NULL,
  upload_time  DATETIME     NOT NULL,
  downloads    integer(255) UNSIGNED NOT NULL DEFAULT 0,
  enabled      TINYINT(1)   NOT NULL DEFAULT 1,
  isDeleted    TINYINT(1)   NOT NULL DEFAULT 0,
  md5          CHAR(32)     NOT NULL,
  description  TEXT         DEFAULT NULL,
  state        VARCHAR(32)  NOT NULL,
  constraint resource_pk PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE resource_deleted (
  id          integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id     integer(255) UNSIGNED NOT NULL,
  resource_id integer(255) UNSIGNED NOT NULL,
  time        DATETIME        NOT NULL,
  constraint resource_deleted_pk PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE resource_support (
  id          integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id     integer(255) UNSIGNED NOT NULL,
  resource_id integer(255) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE favourite (
  id           integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id      integer(255) UNSIGNED NOT NULL,
  resource_id  integer(255) UNSIGNED NOT NULL,
  create_time  DATETIME     NOT NULL,
  constraint favourite_pk PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE user_info (
  user_id     integer(255) UNSIGNED NOT NULL,
  name        VARCHAR(64)  NOT NULL,
  sex         TINYINT(1)   DEFAULT NULL,
  address     VARCHAR(255) DEFAULT NULL,
  headIcon    VARCHAR(255) DEFAULT NULL,
  verifyCode  VARCHAR(16)  DEFAULT NULL,
  verifyTime  DATETIME     DEFAULT NULL,
  email       VARCHAR(128) DEFAULT NULL,
  constraint user_info_pk PRIMARY KEY (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE focus (
  id          integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id     integer(255) UNSIGNED NOT NULL,  -- 发起关注的用户
  focus_uid   integer(255) UNSIGNED NOT NULL,  -- 被关注的用户
  time        DATETIME     NOT NULL,           -- 关注时间
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE favourite_folder (
  id          integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id     integer(255) UNSIGNED NOT NULL,
  folder_name VARCHAR(128) NOT NULL,
  time        DATETIME     NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE comment (
  id             integer(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  resource_id    integer(255) UNSIGNED NOT NULL,  -- 评论对应的资源ID
  user_id        integer(255) UNSIGNED NOT NULL,  -- 评论发起用户ID
  to_uid         integer(255) UNSIGNED DEFAULT NULL, -- 被@或回复的目标用户ID，可为空
  content        TEXT         NOT NULL,           -- 评论内容
  support_number integer(255) UNSIGNED NOT NULL DEFAULT 0, -- 点赞/支持数
  time           DATETIME     NOT NULL,           -- 评论时间
  isIllegal      TINYINT(1)   NOT NULL DEFAULT 0, -- 是否违规：0正常、1违规
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE past_record (
  id integer(255) NOT NULL AUTO_INCREMENT,  -- 主键，deleteRecord需要
  user_id integer(255) NOT NULL,
  resource_id integer(255) NOT NULL,
  time datetime NOT NULL,
  isDeleted tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



### 2. Update application.properties

### 3. Build & run
mvn clean package
java -jar target/backend.jar

