CREATE TABLE IF NOT EXISTS Account_Info (
	username VARCHAR(255) PRIMARY KEY,
	password VARCHAR(255) NOT NULL,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL
);


CREATE TABLE IF NOT EXISTS R_File (
	file_owner VARCHAR(255),
	file_name VARCHAR(255),
	last_modified DATETIME,
	data_file MEDIUMTEXT NOT NULL,
	PRIMARY KEY(file_owner, file_name),
	FOREIGN KEY(file_owner) REFERENCES Account_Info(username)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);



