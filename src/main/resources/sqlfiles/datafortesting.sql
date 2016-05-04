create table test (
    id varchar(255) not null,
    id_type varchar(255) not null,
    version decimal(19,0),
    testvarchar varchar(255),
    testnumber decimal(19,2),
    testdate TIMESTAMP not null,
    PRIMARY KEY id, id_type, version
);

create table test2 (
    id varchar(255) not null,
    id_type varchar(255) not null,
    version decimal(19,0),
    testvarchar varchar(255),
    testnumber decimal(19,2),
    testdate TIMESTAMP not null,
    test_id varchar(255),
    test_id_type varchar(255),
    test_version decimal(19,0)
    PRIMARY KEY id, id_type, version
);

create table test3 (
    id varchar(255) not null,
    id_type varchar(255) not null,
    version decimal(19,0),
    testvarchar varchar(255),
    testnumber decimal(19,2),
    testdate TIMESTAMP not null,
    PRIMARY KEY id, id_type, version
);

alter table test2
  add CONSTRAINT fktest1test2
  FOREIGN KEY (test_id, test_id_type, test_version)
  REFERENCES test;
