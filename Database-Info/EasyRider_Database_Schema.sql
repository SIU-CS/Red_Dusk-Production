CREATE TABLE IF NOT EXISTS Account_Info (
	username VARCHAR(255) PRIMARY KEY,
	hashedword VARCHAR(255) NOT NULL,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS R_File (
	file_owner VARCHAR(255),
	filename VARCHAR(255),
	data_file MEDIUMBLOB NOT NULL,
	PRIMARY KEY(file_owner, filename),
	FOREIGN KEY(file_owner) REFERENCES Account_Info(username)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);



