drop database if exists pcontent;
create database pcontent;
grant all privileges on pcontent.* to pcontent@localhost identified by "menu25serv56frtfz";
grant all privileges on pcontent.* to pcontent@sanya.linux identified by "menu25serv56frtfz";
grant all privileges on pcontent.* to pcontent@sanya.notik identified by "menu25serv56frtfz";

drop database if exists pcontent_test;
create database pcontent_test;
grant all privileges on pcontent_test.* to pcontent@localhost identified by "menu25serv56frtfz";
grant all privileges on pcontent_test.* to pcontent@sanya.linux identified by "menu25serv56frtfz";
grant all privileges on pcontent_test.* to pcontent@sanya.notik identified by "menu25serv56frtfz";

drop database if exists pcontent_dev;
create database pcontent_dev;
grant all privileges on pcontent_dev.* to pcontent@localhost identified by "menu25serv56frtfz";
grant all privileges on pcontent_dev.* to pcontent@sanya.linux identified by "menu25serv56frtfz";
grant all privileges on pcontent_dev.* to pcontent@sanya.notik identified by "menu25serv56frtfz";

use pcontent;