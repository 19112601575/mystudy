CREATE table LICENSE(
                    "ID" bigint not null ,
                    "MACHINE_CODE" bigint not null,
                    "CUSTOMER_NAME" varchar(40),
                    "CONTACT_NAME" varchar(20),
                    "CONTACT_TEL" varchar(20),
                    "CONTACT_EMAIL" varchar(20),
                    "START_DATE" timestamp default current_timestamp() not null ,
                    "END_DATE" timestamp default  current_timestamp() not null ,
                    "USERS_COUNT" int not null ,
                    "CONNECTIONS" int not null ,
                    "LICENSE_KIND" varchar(2) not null ,
                    "FILE_LOCATION" varchar(30),
                    "BUILD_DATE" timestamp,
                    "FUNCTION_MAPPING" varchar(20)
);