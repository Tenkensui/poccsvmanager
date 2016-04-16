create table test (
    testpk1 TIMESTAMP not null,
    testpk2 varchar(255) not null,
    testvarchar varchar(255),
    testnumber decimal(19,2),
    testdate TIMESTAMP not null,
    PRIMARY KEY testpk1, testpk2
);

create table test_2 (
    testpk1 TIMESTAMP not null,
    testpk2 varchar(255) not null,
    testvarchar varchar(255),
    testnumber decimal(19,2),
    testdate TIMESTAMP not null,
    fk1 TIMESTAMP,
    fk2 VARCHAR(255),
    PRIMARY KEY testpk1, testpk2
);

alter table test_2
  add CONSTRAINT fkutoeuuoe
  FOREIGN KEY (fk1, fk2)
  REFERENCES test;
