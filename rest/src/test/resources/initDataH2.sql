DELETE FROM RENT_HISTORY;
DELETE FROM USERS_INFO;
DELETE FROM USERS;
DELETE FROM USER_ROLE;
DELETE FROM BOOKS_CATALOGS;
DELETE FROM BOOKS_AUTHORS;
DELETE FROM CATALOGS;
DELETE FROM AUTHORS;
DELETE FROM BOOKS;

INSERT INTO USER_ROLE (id, name) VALUES	(1,'admin'),
										(2,'reader');

INSERT INTO USERS (id, login, password,user_role_id) VALUES (1,'anshimko','1234',1),
															(2,'dandy','1234',2);

INSERT INTO USERS_INFO (id, email, name, surname, users_id) VALUES	(1,'anshimko@gmail.com','Andru','Shymko', 1),
																	(2,'himko@gmail.com','Nik','Goro', 2);

								
INSERT INTO CATALOGS (id, name, parent_id) VALUES	(1,'classic',null),
													(2,'new', 1),
													(3, 'detectiv', null);

INSERT INTO BOOKS (id, title) VALUES 	(1,'War and piec'),
										(2,'Idiot'),
										(3,'Monblan');					

INSERT INTO AUTHORS (id, name) VALUES	(1,'Tolstoj'),
										(2, 'Dostoevski');
										
INSERT INTO BOOKS_AUTHORS (authors_id, books_id) VALUES	(1,1),
														(2,2),
														(1,3);
										
INSERT INTO BOOKS_CATALOGS (catalogs_id, books_id) VALUES	(1,1),
															(2,1),
															(3,3);

INSERT INTO RENT_HISTORY (id, books_id, users_id, borrow_date, return_date, is_returned) 
						VALUES	(1, 1, 1, '2020-03-20', '2020-04-11', true),
								(2, 2, 1, '2020-04-20', '2020-06-14', false),
								(3, 1, 2, '2020-04-24', '2020-05-10', false);