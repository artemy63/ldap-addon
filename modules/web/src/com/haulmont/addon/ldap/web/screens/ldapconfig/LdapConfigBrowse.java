/*
 * Copyright (c) 2008-2022 Haulmont.
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

package com.haulmont.addon.ldap.web.screens.ldapconfig;

import com.google.common.base.Strings;
import com.haulmont.addon.ldap.entity.LdapConfig;
import com.haulmont.addon.ldap.service.LdapService;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("ldap$LdapPropertiesConfig.browse")
@UiDescriptor("ldap-config-browse.xml")
@LookupComponent("ldapConfigsTable")
public class LdapConfigBrowse extends StandardLookup<LdapConfig> {

    @Inject
    private CollectionLoader<LdapConfig> ldapConfigsDl;
    @Inject
    private LdapService ldapService;

    @Inject
    private CollectionContainer<LdapConfig> ldapConfigsDc;

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        ldapConfigsDl.load();

        //fill missing attributes for default ldapConfig
        ldapConfigsDc.getItems().stream()
                .filter(ldapConfig -> Strings.isNullOrEmpty(ldapConfig.getSysTenantId()))
                .findFirst()
                .ifPresent(ldapConfig -> {
                    LdapConfig defaultConfig = ldapService.getDefaultConfig();
                    ldapConfig.setContextSourceUrl(defaultConfig.getContextSourceUrl());
                    ldapConfig.setContextSourceBase(defaultConfig.getContextSourceBase());
                    ldapConfig.setContextSourceUserName(defaultConfig.getContextSourceUserName());
                });
    }



}