DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100;

INSERT INTO users (name, password, email, registration) VALUES
  ('user01',  '$2a$10$73jFQukIzmyowQUJVttIreA.tR22/jUtQouWql3X0tTAoAiH3poDm','user01@yandex.ru','2015-05-30 10:00'),
  ('user02',  '$2a$10$ZTUcvo.n8DWHuDTkw93tWelKANB2.UPh/QAmWPiBEQb.kIyBWJ6ry','user02@yandex.ru','2015-05-31 10:00'),
  ('user03',  '$2a$10$wl2.YAXvcmVlyi2bB1CuLuVdt0Z0p91OMZrJiwWE0S.yby5Wv.s/i','user03@yandex.ru','2015-06-02 10:00'),
  ('user04',  '$2a$10$DpebmiUCnu9GzRRzlpiSZOsqF7fuGCjF/Fs6lw2Ni1EaF2cGQuQym','user04@yandex.ru','2015-06-03 10:00'),
  ('admin',  '$2a$10$bOm4JRR0kpbuuWAnP6X/uO3QS3f19kcl6GB37oXf/3PeHP54BsjUm','admin@gmail.com','2016-05-30 10:00');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100),
  ('ROLE_USER', 101),
  ('ROLE_USER', 102),
  ('ROLE_USER', 103),
  ('ROLE_ADMIN', 104),
  ('ROLE_USER', 104);