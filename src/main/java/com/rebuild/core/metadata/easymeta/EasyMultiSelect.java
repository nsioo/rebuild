/*
Copyright (c) REBUILD <https://getrebuild.com/> and/or its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.core.metadata.easymeta;

import cn.devezhao.persist4j.Field;
import com.rebuild.core.configuration.general.MultiSelectManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

/**
 * @author devezhao
 * @since 2020/11/17
 */
@Slf4j
public class EasyMultiSelect extends EasyField {
    private static final long serialVersionUID = -1615061627351160386L;

    protected EasyMultiSelect(Field field, DisplayType displayType) {
        super(field, displayType);
    }

    @Override
    public Object convertCompatibleValue(Object value, EasyField targetField) {
        DisplayType targetType = targetField.getDisplayType();
        boolean is2Text = targetType == DisplayType.TEXT || targetType == DisplayType.NTEXT;
        if (is2Text) {
            return wrapValue(value);
        }

        if (value == null || (Long) value <= 0) {
            return null;
        }

        String[] valueLabels = MultiSelectManager.instance.getLabels((Long) value, getRawMeta());
        if (valueLabels == null || valueLabels.length == 0) {
            return null;
        }

        long maskValue = 0;
        for (String label : valueLabels) {
            long mv = MultiSelectManager.instance.findMultiItemByLabel(label, targetField.getRawMeta());
            if (mv > 0) {
                maskValue += mv;
            } else {
                log.warn("Cannot found mask-value of MultiSelect-Label : {}", label);
            }
        }
        return maskValue > 0 ? maskValue : null;
    }

    @Override
    public Object wrapValue(Object value) {
        if (value == null || (Long) value <= 0) {
            return StringUtils.EMPTY;
        }

        String[] multiLabel = MultiSelectManager.instance.getLabels((Long) value, getRawMeta());
        return StringUtils.join(multiLabel, ", ");
    }

    @Override
    public Object exprDefaultValue() {
        return MultiSelectManager.instance.getDefaultValue(getRawMeta());
    }
}
