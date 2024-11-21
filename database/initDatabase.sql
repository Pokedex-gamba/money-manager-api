drop database if exists money_manager;

create DATABASE money_manager;

use money_manager;

CREATE TABLE user_wallet (
                             user_id VARCHAR(128) NOT NULL UNIQUE PRIMARY KEY,
                             balance DECIMAL(10) DEFAULT 0
);