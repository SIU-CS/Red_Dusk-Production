CREATE TABLE Account_Info(
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL
	CONSTRAINT CHK_UserLength CHECK (CHAR_LENGTH(username) >= 4),
	CONSTRAINT CHK_UserPassword CHECK (CHAR_LENGTH(password) >= 6)
);


CREATE TABLE R_File(
	username VARCHAR(255),
	filename VARCHAR(255),
	data_file MEDIUMBLOB NOT NULL,
	PRIMARY KEY(username, filename)
	FOREIGN KEY(username) REFERENCES Account_Info(username)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);



