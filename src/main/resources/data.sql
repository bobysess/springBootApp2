insert into users
    (username, password, enabled)
values
    ('user1', '{noop}user1',true),
    ('user2', 'noop}user2',true),
    ('user3', '{noop}user3',true);

insert into authorities
    (username, authority)
values
    ('user1', 'ADMIN'),
    ('user2', 'USER'),
    ('user3', 'ADMIN'),
    ('user3', 'USER');
