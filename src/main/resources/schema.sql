-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2025-08-10 18:34:16.138

-- Drop existing tables (if any) to reset schema and ID sequences
DROP TABLE IF EXISTS user_movie CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;
DROP TABLE IF EXISTS movie CASCADE;

-- Table: movie
CREATE TABLE movie (
                       id INT GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                       title VARCHAR(200) NOT NULL,
                       release_year SMALLINT NOT NULL,
                       runtime_minutes SMALLINT NOT NULL,
                       created_at TIMESTAMP NOT NULL,
                       CONSTRAINT movie_pk PRIMARY KEY (id)
);

-- Table: user_account
CREATE TABLE user_account (
                              id INT GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                              username VARCHAR(50) NOT NULL,
                              display_name VARCHAR(100) NOT NULL,
                              email VARCHAR(120) NOT NULL,
                              CONSTRAINT uq_user_username UNIQUE (username),
                              CONSTRAINT uq_user_email UNIQUE (email),
                              CONSTRAINT user_account_pk PRIMARY KEY (id)
);

-- Table: user_movie
CREATE TABLE user_movie (
                            id INT GENERATED ALWAYS AS IDENTITY (START WITH 1) NOT NULL,
                            user_id INT NOT NULL,
                            movie_id INT NOT NULL,
                            status VARCHAR(20) NOT NULL CHECK (status IN ('TO_WATCH','WATCHED')),
                            rating SMALLINT NULL CHECK (rating BETWEEN 0 AND 10),
                            comment VARCHAR(1000) NULL,
                            watched_at DATE NULL,
                            created_at TIMESTAMP NOT NULL,
                            CONSTRAINT user_movie_ak_1 UNIQUE (user_id, movie_id),
                            CONSTRAINT user_movie_pk PRIMARY KEY (id),
                            CONSTRAINT user_account_user_movie FOREIGN KEY (user_id) REFERENCES user_account (id) ON DELETE CASCADE,
                            CONSTRAINT user_movie_movie FOREIGN KEY (movie_id) REFERENCES movie (id) ON DELETE CASCADE
);
-- End of file.

