-- begin LDAP_MATCHING_RULE
alter table LDAP_MATCHING_RULE add constraint FK_LDAP_MATCHING_RULE_ON_ACCESS_GROUP foreign key (ACCESS_GROUP_ID) references SEC_GROUP(ID)^
alter table LDAP_MATCHING_RULE add constraint FK_LDAP_MATCHING_RULE_ON_MATCHING_RULE_STATUS foreign key (MATCHING_RULE_STATUS_ID) references LDAP_MATCHING_RULE_STATUS(ID)^
alter table LDAP_MATCHING_RULE add constraint FK_LDAP_MATCHING_RULE_ON_MATCHING_RULE_ORDER foreign key (MATCHING_RULE_ORDER_ID) references LDAP_MATCHING_RULE_ORDER(ID)^
alter table LDAP_MATCHING_RULE add constraint FK_LDAP_MATCHING_RULE_LDAP_LDAP_CONFIG foreign key (LDAP_CONFIG_ID) references LDAP_LDAP_CONFIG (ID) ON DELETE CASCADE ^
alter table LDAP_USER_ATTRIBUTE add constraint FK_LDAP_USER_ATTRIBUTE_LDAP_LDAP_CONFIG foreign key (LDAP_CONFIG_ID) references LDAP_LDAP_CONFIG (ID) ON DELETE CASCADE^
create index IDX_LDAP_MATCHING_RULE_ON_ACCESS_GROUP on LDAP_MATCHING_RULE (ACCESS_GROUP_ID)^
create index IDX_LDAP_MATCHING_RULE_ON_MATCHING_RULE_STATUS on LDAP_MATCHING_RULE (MATCHING_RULE_STATUS_ID)^
create index IDX_LDAP_MATCHING_RULE_ON_MATCHING_RULE_ORDER on LDAP_MATCHING_RULE (MATCHING_RULE_ORDER_ID)^
-- end LDAP_MATCHING_RULE
-- begin LDAP_SIMPLE_RULE_CONDITION
alter table LDAP_SIMPLE_RULE_CONDITION add constraint FK_LDAP_SIMPLE_RULE_CONDITION_ON_SIMPLE_MATCHING_RULE foreign key (SIMPLE_MATCHING_RULE_ID) references LDAP_MATCHING_RULE(ID)^
create index IDX_LDAP_SIMPLE_RULE_CONDITION_ON_SIMPLE_MATCHING_RULE on LDAP_SIMPLE_RULE_CONDITION (SIMPLE_MATCHING_RULE_ID)^
-- end LDAP_SIMPLE_RULE_CONDITION
-- begin LDAP_MATCHING_RULE_ORDER
create unique index IDX_LDAP_MATCHING_RULE_ORDER_UNIQ_CUSTOM_MATCHING_RULE_ID on LDAP_MATCHING_RULE_ORDER (CUSTOM_MATCHING_RULE_ID) ^
-- end LDAP_MATCHING_RULE_ORDER
-- begin LDAP_MATCHING_RULE_STATUS
create unique index IDX_LDAP_MATCHING_RULE_STATUS_UNIQ_CUSTOM_MATCHING_RULE_ID on LDAP_MATCHING_RULE_STATUS (CUSTOM_MATCHING_RULE_ID) ^
-- end LDAP_MATCHING_RULE_STATUS

create unique index IDX_LDAP_LDAP_CONFIG_UNIQ_SYS_TENANT_ID on LDAP_LDAP_CONFIG (SYS_TENANT_ID)^
