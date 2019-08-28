/*
 * Copyright (c) 2008-2019 Haulmont.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.haulmont.addon.ldap.core;

import com.haulmont.bali.util.Dom4j;
import com.haulmont.cuba.testsupport.TestContainer;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class LdapTestContainer extends TestContainer {

    public LdapTestContainer() {
        super();
        appComponents = new ArrayList<>(Arrays.asList(
                "com.haulmont.cuba"
                // add CUBA premium add-ons here
                // "com.haulmont.bpm",
                // "com.haulmont.charts",
                // "com.haulmont.fts",
                // "com.haulmont.reports",
                // and custom app components if any
        ));
        appPropertiesFiles = Arrays.asList(
                // List the files defined in your web.xml
                // in appPropertiesConfig context parameter of the core module
                "com/haulmont/addon/ldap/core/app.properties",
                // Add this file which is located in CUBA and defines some properties
                // specifically for test environment. You can replace it with your own
                // or add another one in the end.
//                "test-app.properties",
                "com/haulmont/addon/ldap/core/resources/ldap-test-app.properties");
        initDbProperties();
    }

    @Override
    protected void initAppProperties() {
        super.initAppProperties();
        String dbmsType = System.getProperty("test.db.dbmsType");
        if (dbmsType != null) {
            getAppProperties().put("cuba.dbmsType", dbmsType);
        }
    }

    private void initDbProperties() {
        File contextXmlFile = new File("modules/core/web/META-INF/context.xml");
        if (!contextXmlFile.exists()) {
            contextXmlFile = new File("web/META-INF/context.xml");
        }
        if (!contextXmlFile.exists()) {
            throw new RuntimeException("Cannot synchronizeUserAfterLdapLogin 'context.xml' file to read database connection properties. " +
                    "You can set them explicitly in this method.");
        }
        Document contextXmlDoc = Dom4j.readDocument(contextXmlFile);
        Element resourceElem = contextXmlDoc.getRootElement().element("Resource");

        dbDriver = getDbConfigurationValue(resourceElem, "driverClassName");
        dbUrl = getDbConfigurationValue(resourceElem, "url");
        dbUser = getDbConfigurationValue(resourceElem, "username");
        dbPassword = getDbConfigurationValue(resourceElem, "password");
    }

    protected String getDbConfigurationValue(Element resourceElem, String attributeName) {
        String externalValue = System.getProperty("test.db." + attributeName);
        return StringUtils.isNotBlank(externalValue) ? externalValue : resourceElem.attributeValue(attributeName);
    }

    public static final class Common extends LdapTestContainer {

        public static final LdapTestContainer.Common INSTANCE = new LdapTestContainer.Common();

        private static volatile boolean initialized;

        private Common() {
        }

        @Override
        public void before() throws Throwable {
            if (!initialized) {
                super.before();
                initialized = true;
            }
            setupContext();
        }

        @Override
        public void after() {
            cleanupContext();
            // never stops - do not call super
        }
    }
}