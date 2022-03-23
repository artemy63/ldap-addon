alter table LDAP_LDAP_CONFIG add SYS_TENANT_ID varchar(255)^

create unique index IDX_LDAP_LDAP_CONFIG_UNIQ_ID_SYS_TENANT_ID on LDAP_LDAP_CONFIG (ID, SYS_TENANT_ID)
    where SYS_TENANT_ID is not null^
