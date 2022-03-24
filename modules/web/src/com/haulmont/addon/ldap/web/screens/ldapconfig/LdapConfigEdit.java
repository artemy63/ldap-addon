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
import com.haulmont.cuba.core.global.EntityStates;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.TextField;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.screen.*;

import javax.inject.Inject;

@UiController("ldap$LdapPropertiesConfig.edit")
@UiDescriptor("ldap-config-edit.xml")
@EditedEntityContainer("ldapConfigDs")
//@LoadDataBeforeShow
public class LdapConfigEdit extends StandardEditor<LdapConfig> {

    @Inject
    private LdapService ldapService;
    @Inject
    private MessageBundle messageBundle;
    @Inject
    private Notifications notifications;
    @Inject
    private EntityStates entityStates;

    @Inject
    private InstanceContainer<LdapConfig> ldapConfigDs;
    @Inject
    private TextField<String> contextSourceUrlField;
    @Inject
    private TextField<String> contextSourceUserNameField;
    @Inject
    private TextField<String> contextSourceBaseField;
    @Inject
    private PasswordField contextSourcePasswordField;
    @Inject
    private TextField<String> tenantField;
    @Inject
    private TextField<String> loginAttributeField;
    @Inject
    private TextField<String> cnAttributeField;
    @Inject
    private TextField<String> givenNameAttributeField;
    @Inject
    private TextField<String> middleNameAttributeField;
    @Inject
    private TextField<String> snAttributeField;
    @Inject
    private TextField<String> emailAttributeField;
    @Inject
    private TextField<String> memberOfAttributeField;
    @Inject
    private TextField<String> accessGroupAttributeField;
    @Inject
    private TextField<String> positionAttributeField;
    @Inject
    private TextField<String> languageAttributeField;
    @Inject
    private TextField<String> ouAttributeField;
    @Inject
    private TextField<String> inactiveUserAttributeField;
    @Inject
    private TextField<String> userBaseField;
    @Inject
    private TextField<String> defaultAccessGroupNameField;
    @Inject
    private TextField<String> schemaBaseField;
    @Inject
    private TextField<String> ldapUserObjectClassesField;
    @Inject
    private TextField<String> objectClassPropertyNameField;
    @Inject
    private TextField<String> attributePropertyNamesField;

    @Subscribe
    public void onInitEntity(InitEntityEvent<LdapConfig> event) {
//        copyDefaults();
    }

    @Subscribe
    public void onBeforeShow(BeforeShowEvent event) {
        getScreenData().loadAll();

        boolean isDefaultConfig = isDefaultConfig();
        contextSourceUrlField.setEditable(!isDefaultConfig);
        contextSourceUserNameField.setEditable(!isDefaultConfig);
        contextSourceBaseField.setEditable(!isDefaultConfig);
        contextSourcePasswordField.setVisible(!isDefaultConfig);
        tenantField.setVisible(!isDefaultConfig);

        copyDefaults();
    }

    private void copyDefaults() {
        LdapConfig defaultConfig = ldapService.getDefaultConfig();

        schemaBaseField.setValue(defaultConfig.getSchemaBase());
        defaultAccessGroupNameField.setValue(defaultConfig.getDefaultAccessGroupName());
        ldapUserObjectClassesField.setValue(defaultConfig.getLdapUserObjectClasses());
        objectClassPropertyNameField.setValue(defaultConfig.getObjectClassPropertyName());
        attributePropertyNamesField.setValue(defaultConfig.getAttributePropertyNames());
        loginAttributeField.setValue(defaultConfig.getLoginAttribute());
        emailAttributeField.setValue(defaultConfig.getEmailAttribute());
        cnAttributeField.setValue(defaultConfig.getCnAttribute());
        snAttributeField.setValue(defaultConfig.getSnAttribute());
        givenNameAttributeField.setValue(defaultConfig.getGivenNameAttribute());
        middleNameAttributeField.setValue(defaultConfig.getMiddleNameAttribute());
        memberOfAttributeField.setValue(defaultConfig.getMemberOfAttribute());
        accessGroupAttributeField.setValue(defaultConfig.getAccessGroupAttribute());
        positionAttributeField.setValue(defaultConfig.getPositionAttribute());
        ouAttributeField.setValue(defaultConfig.getOuAttribute());
        languageAttributeField.setValue(defaultConfig.getLanguageAttribute());
        inactiveUserAttributeField.setValue(defaultConfig.getInactiveUserAttribute());
        userBaseField.setValue(defaultConfig.getUserBase());

        setModifiedAfterOpen(false);
    }

    public void onTestConnectionClick() {
        String result = ldapService.testConnection(getEditedEntity().getSysTenantId());

        if ("SUCCESS".equals(result)) {
            notifications.create(Notifications.NotificationType.HUMANIZED)
                    .withCaption(messageBundle.getMessage("settingsScreenConnectionSuccessCaption"))
                    .withDescription(messageBundle.getMessage("settingsScreenConnectionSuccessMsg"))
                    .show();
        } else {
            notifications.create(Notifications.NotificationType.WARNING)
                    .withCaption(messageBundle.getMessage("settingsScreenConnectionErrorCaption"))
                    .withDescription(result)
                    .show();
        }
    }

    public void onUpdateLdapSchemaUserAttributesButtonClick() {
    }


//    public void onUpdateLdapSchemaUserAttributesButtonClick() {
//        // TODO: 24.03.2022 regarding current tenant
//        showOptionDialog(
//                getMessage("refreshAttributesFromLdapTitle"),
//                getMessage("refreshAttributesFromLdap"),
//                MessageType.CONFIRMATION,
//                new Action[]{
//                        new DialogAction(DialogAction.Type.YES) {
//                            public void actionPerform(Component component) {
//                                LdapConfig lc = getItem();
//                                ldapService.fillLdapUserAttributes(lc.getSchemaBase(), lc.getLdapUserObjectClasses(),
//                                        lc.getObjectClassPropertyName(), lc.getAttributePropertyNames());
//                                ldapUserAttributesDs.refresh();
//                            }
//                        },
//                        new DialogAction(DialogAction.Type.NO)
//                }
//        );
//    }

    private boolean isDefaultConfig() {
        LdapConfig editedEntity = getEditedEntity();
        if (entityStates.isNew(editedEntity)) {
            return false;
        }

        if (!Strings.isNullOrEmpty(getEditedEntity().getSysTenantId())) {
            return false;
        }

        // TODO: 24.03.2022 do I need this check or previous two are enough
        LdapConfig defaultConfig = ldapService.getDefaultConfig();
        return defaultConfig.getContextSourceUrl().equals(getEditedEntity().getContextSourceUrl())
                && defaultConfig.getContextSourceBase().equals(getEditedEntity().getContextSourceBase())
                && defaultConfig.getContextSourceUserName().equals(getEditedEntity().getContextSourceUserName());

    }

}