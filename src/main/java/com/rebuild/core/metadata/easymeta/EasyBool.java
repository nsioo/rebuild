/*
Copyright (c) REBUILD <https://getrebuild.com/> and/or its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.core.metadata.easymeta;

import cn.devezhao.persist4j.Field;
import com.rebuild.core.support.i18n.Language;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author devezhao
 * @since 2020/11/17
 */
public class EasyBool extends EasyField {
    private static final long serialVersionUID = -7818334967908628446L;

    protected EasyBool(Field field, DisplayType displayType) {
        super(field, displayType);
    }

    @Override
    public Object wrapValue(Object value) {
        return (Boolean) value ? Language.L("True") : Language.L("False");
    }

    @Override
    public Object exprDefaultValue() {
        String valueExpr = (String) getRawMeta().getDefaultValue();
        return StringUtils.isBlank(valueExpr) ? Boolean.FALSE : BooleanUtils.toBoolean(valueExpr);
    }
}
