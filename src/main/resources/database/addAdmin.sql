INSERT INTO users (id, username, password, active)
SELECT * FROM (SELECT 1 AS id, 'admin' AS username, '$2y$08$A/Uz2NgPUbqxDMY8buz.Iu186POcmaanWe8JYuEPXbC9qOqZuFJyC' as password, true as active) AS temp
WHERE NOT EXISTS (
    SELECT username FROM users WHERE username = 'admin'
) LIMIT 1;

INSERT INTO user_role (user_id, roles)
SELECT * FROM (SELECT 1 AS id, 'USER' AS roles) AS temp
WHERE NOT EXISTS (
    SELECT user_id FROM user_role WHERE user_id = 1
) LIMIT 1;

INSERT INTO user_role (user_id, roles)
SELECT * FROM (SELECT 1 AS id, 'ADMIN' AS roles) AS temp
WHERE NOT EXISTS (
    SELECT roles FROM user_role WHERE roles = 'ADMIN'
) LIMIT 1;