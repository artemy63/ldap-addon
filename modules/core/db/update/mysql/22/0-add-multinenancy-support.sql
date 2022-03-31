ALTER TABLE LDAP_MATCHING_RULE ADD COLUMN LDAP_CONFIG_ID varchar(32)^

ALTER TABLE LDAP_USER_ATTRIBUTE ADD COLUMN LDAP_CONFIG_ID varchar(32)^

ALTER TABLE LDAP_LDAP_CONFIG ADD COLUMN SYS_TENANT_ID varchar(255)^
ALTER TABLE LDAP_LDAP_CONFIG ADD COLUMN CONTEXT_SOURCE_BASE varchar(255)^
ALTER TABLE LDAP_LDAP_CONFIG ADD COLUMN CONTEXT_SOURCE_URL varchar(255)^
ALTER TABLE LDAP_LDAP_CONFIG ADD COLUMN CONTEXT_SOURCE_USER_NAME varchar(255)^
ALTER TABLE LDAP_LDAP_CONFIG ADD COLUMN CONTEXT_SOURCE_PASSWORD varchar(255)^

UPDATE LDAP_MATCHING_RULE
SET LDAP_CONFIG_ID = 'ff2ebe743836465b918560141a6a0548'^
ALTER TABLE LDAP_MATCHING_RULE ADD CONSTRAINT FK_LDAP_MATCHING_RULE_LDAP_LDAP_CONFIG FOREIGN KEY (LDAP_CONFIG_ID) REFERENCES LDAP_LDAP_CONFIG(ID) ON DELETE CASCADE^

UPDATE LDAP_USER_ATTRIBUTE
SET LDAP_CONFIG_ID = 'ff2ebe743836465b918560141a6a0548'^
ALTER TABLE LDAP_USER_ATTRIBUTE ADD CONSTRAINT FK_LDAP_USER_ATTRIBUTE_LDAP_LDAP_CONFIG FOREIGN KEY (LDAP_CONFIG_ID) REFERENCES LDAP_LDAP_CONFIG (ID) ON DELETE CASCADE^

CREATE UNIQUE INDEX IDX_LDAP_LDAP_CONFIG_UNIQ_SYS_TENANT_ID on LDAP_LDAP_CONFIG (SYS_TENANT_ID)^