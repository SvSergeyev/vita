INSERT INTO role VALUES
(1, 'ROLE_ADMIN') ,
(2, 'ROLE_USER') ,
(3, 'ROLE_OPERATOR')
ON CONFLICT DO NOTHING;

INSERT INTO person VALUES
(1, 'Slava', 'slava@ya.ru', '$2a$12$ZWcvpwe6Y.k1kSDOug5bP.mQ4hPNoK1s2njLIMQrcTrQ2AjKJOmnm'),
(2, 'Ivan', 'ivan@ya.ru', '$2a$12$3dV6WlCwfau1V.YQamlpcuAXlxL9KNVycdgiEAM0.iOVY6EB3pjiC'),
(3, 'Oleg', 'oleg@ya.ru', '$2a$12$Q8IQ9V/DKesDzVqRKGktp.EkL7JpC8UBsoF3kNJ4saVuvlpEEcOne'),
(4, 'Dima', 'dima@ya.ru', '$2a$12$E4BtEOOfVwTUQEg8KGvYbest9dJ/X.IBHUSIBVEg6keXWz7wLHk/m'),
(5, 'Sasha', 'sasha@ya.ru', '$2a$12$izKI8i3vWclAchlBd9zuj.4vqPs5wJXbxkkOX1NOdJCQNsOKqbyuu'),
(6, 'Vasya', 'vasya@ya.ru', '$2a$12$e.kb3OyYNdJ1e9iCifumauIYhXktJVPV2D02il/VmksC/SUeK/qma')
ON CONFLICT DO NOTHING;

INSERT INTO people_roles VALUES
(1, 1),
(4, 1),
(2, 2),
(5, 2),
(3, 3),
(6, 3)
ON CONFLICT DO NOTHING;