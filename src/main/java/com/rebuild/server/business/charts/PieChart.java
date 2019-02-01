/*
rebuild - Building your business-systems freely.
Copyright (C) 2018 devezhao <zhaofang123@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package com.rebuild.server.business.charts;

import java.text.MessageFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rebuild.server.Application;
import com.rebuild.server.metadata.entityhub.EasyMeta;
import com.rebuild.utils.JSONUtils;

import cn.devezhao.persist4j.engine.ID;

/**
 * 饼图
 * 
 * @author devezhao
 * @since 12/15/2018
 */
public class PieChart extends ChartData {

	protected PieChart(JSONObject config, ID user) {
		super(config, user);
	}

	@Override
	public JSON build() {
		Dimension[] dims = getDimensions();
		Numerical[] nums = getNumericals();
		
		Dimension dim1 = dims[0];
		Numerical num1 = nums[0];
		Object[][] dataRaw = Application.createQuery(buildSql(dim1, num1), user).array();
		
		JSONArray dataJson = new JSONArray();
		for (Object[] o : dataRaw) {
			o[0] = warpAxisValue(dim1, o[0]);
			o[1] = warpAxisValue(num1, o[1]);
			JSON d = JSONUtils.toJSONObject(new String[] { "name", "value" }, o);
			dataJson.add(d);
		}
		
		// TODO 排序
		
		JSONObject ret = JSONUtils.toJSONObject(
				new String[] { "data", "name" },
				new Object[] { dataJson, EasyMeta.getLabel(num1.getField()) + num1.getFormatCalc().getLabel() });
		return ret;
	}
	
	protected String buildSql(Dimension dim, Numerical num) {
		String sql = "select {0},{1} from {2} where {3} group by {0}";
		String where = getFilterSql();
		
		sql = MessageFormat.format(sql, 
				dim.getSqlName(),
				num.getSqlName(),
				getSourceEntity().getName(), where);
		return sql;
	}
}
