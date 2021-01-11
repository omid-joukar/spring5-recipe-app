create database sfg_dev;
create database sfg_prod;

create user 'sfg_dev_user'@'localhost' identified by 'omid';
create user 'sfg_prod_user'@'localhost' identified by 'omid';

grant select on  sfg_dev.* to 'sfg_dev_user'@'localhost';
grant update on  sfg_dev.* to 'sfg_dev_user'@'localhost';
grant delete on  sfg_dev.* to 'sfg_dev_user'@'localhost';
grant insert on  sfg_dev.* to 'sfg_dev_user'@'localhost';
grant select on  sfg_prod.* to 'sfg_prod_user'@'localhost';
grant update on  sfg_prod.* to 'sfg_prod_user'@'localhost';
grant delete on  sfg_prod.* to 'sfg_prod_user'@'localhost';
grant insert on  sfg_prod.* to 'sfg_prod_user'@'localhost';