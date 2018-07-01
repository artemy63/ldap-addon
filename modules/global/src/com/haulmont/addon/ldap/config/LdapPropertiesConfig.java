package com.haulmont.addon.ldap.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.DefaultBoolean;
import com.haulmont.cuba.core.config.defaults.DefaultInt;
import com.haulmont.cuba.core.config.defaults.DefaultInteger;
import com.haulmont.cuba.core.config.defaults.DefaultString;
import com.haulmont.cuba.core.config.type.CommaSeparatedStringListTypeFactory;
import com.haulmont.cuba.core.config.type.Factory;

import java.util.List;

public interface LdapPropertiesConfig extends Config {

    @Source(type = SourceType.APP)
    @Property("ldap.contextSourceUrl")
    String getContextSourceUrl();

    @Source(type = SourceType.APP)
    @Property("ldap.contextSourceBase")
    String getContextSourceBase();

    @Source(type = SourceType.APP)
    @Property("ldap.contextSourceUserName")
    String getContextSourceUserName();

    @Source(type = SourceType.APP)
    @Property("ldap.contextSourcePassword")
    String getContextSourcePassword();

    @Source(type = SourceType.APP)
    @Property("ldap.sessionExpiringPeriodSec")
    @DefaultInt(60)
    int getSessionExpiringPeriodSec();

    @Source(type = SourceType.APP)
    @Property("cuba.web.standardAuthenticationUsers")
    @Factory(factory = CommaSeparatedStringListTypeFactory.class)
    @DefaultString("admin,anonymous")
    List<String> getStandardAuthenticationUsers();

    @Source(type = SourceType.APP)
    @Property("ldap.addonEnabled")
    Boolean getLdapAddonEnabled();

    @Source(type = SourceType.APP)
    @Property("ldap.expiringSessionsEnable")
    Boolean getExpiringSessionsEnable();

    @Source(type = SourceType.APP)
    @Property("ldap.userSynchronizationBatchSize")
    @DefaultInteger(100)
    Integer getUserSynchronizationBatchSize();

    @Source(type = SourceType.APP)
    @Property("ldap.userSynchronizationOnlyActiveProperty")
    @DefaultBoolean(true)
    Boolean getUserSynchronizationOnlyActiveProperty();

    void setContextSourceUrl(String contextSourceUrl);

    void setContextSourceBase(String contextSourceBase);

    void setContextSourceUserName(String contextSourceUserName);

    void setContextSourcePassword(String contextSourcePassword);

    void setSessionExpiringPeriodSec(int sessionExpiringPeriod);

    void setStandardAuthenticationUsers(List<String> standardAuthenticationUsers);

    void setLdapAddonEnabled(Boolean ldapAddonEnabled);

    void setExpiringSessionsEnable(Boolean expiringSessionsEnable);

    void setUserSynchronizationBatchSize(Integer userSynchronizationBatchSize);

    void setUserSynchronizationOnlyActiveProperty(Boolean userSynchronizationOnlyActiveProperty);

}
