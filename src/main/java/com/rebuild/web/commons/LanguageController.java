/*
Copyright (c) REBUILD <https://getrebuild.com/> and/or its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.web.commons;

import cn.devezhao.commons.web.ServletUtils;
import com.rebuild.core.support.i18n.LanguageBundle;
import com.rebuild.utils.AppUtils;
import com.rebuild.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 多语言控制
 *
 * @author devezhao
 * @since 2019/11/29
 */
@Controller
@RequestMapping("/language/")
public class LanguageController extends BaseController {

    private static final String HEADER_ETAG = "ETag";
    private static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    private static final String HEADER_CACHE_CONTROL = "Cache-Control";
    private static final String DIRECTIVE_NO_STORE = "no-store";

    // Support Etag
    // see org.springframework.web.filter.ShallowEtagHeaderFilter
    @GetMapping("bundle")
    public void getLanguageBundle(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(ServletUtils.CT_JS);

        final LanguageBundle bundle = AppUtils.getReuqestBundle(request);

        // whether the generated ETag should be weak
        // SPEC: length of W/ + " + 0 + 32bits md5 hash + "
        String responseETag = String.format("W/\"0%s\"", bundle.getBundleHash());
        response.setHeader(HEADER_ETAG, responseETag);

        // 无缓存
        String cacheControl = response.getHeader(HEADER_CACHE_CONTROL);
        if (cacheControl != null && cacheControl.contains(DIRECTIVE_NO_STORE)) {
            ServletUtils.write(response, "window._LANGBUNDLE = " + bundle.toJSON().toJSONString());
            return;
        }

        String requestETag = request.getHeader(HEADER_IF_NONE_MATCH);
        if (requestETag != null && ("*".equals(requestETag) || responseETag.equals(requestETag) ||
                responseETag.replaceFirst("^W/", "").equals(requestETag.replaceFirst("^W/", "")))) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
        } else {
            ServletUtils.write(response, "window._LANGBUNDLE = " + bundle.toJSON().toJSONString());
        }
    }
}
