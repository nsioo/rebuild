/*
Copyright 2018 DEVEZHAO(zhaofang123@gmail.com)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package cn.devezhao.rebuild.server.service;

import cn.devezhao.persist4j.PersistManagerFactory;
import cn.devezhao.persist4j.Query;
import cn.devezhao.persist4j.Record;
import cn.devezhao.persist4j.query.NativeQuery;

/**
 * 查询服务
 * 
 * @author zhaofang123@gmail.com
 * @since 05/21/2017
 */
public class QueryFactory {
	
	public static final int QUERY_TIMEOUT = 5 * 1000;

	private PersistManagerFactory persistManagerFactory;

	protected QueryFactory(PersistManagerFactory persistManagerFactory) {
		super();
		this.persistManagerFactory = persistManagerFactory;
	}

	/**
	 * @param ajql
	 * @return
	 */
	public Query createQuery(String ajql) {
		return persistManagerFactory.createQuery(ajql)
				.setTimeout(QUERY_TIMEOUT).setSlowLoggerTime(1000);
	}

	/**
	 * @param sql
	 * @return
	 */
	public NativeQuery createNativeQuery(String sql) {
		return persistManagerFactory.createNativeQuery(sql)
				.setTimeout(QUERY_TIMEOUT).setSlowLoggerTime(1000);
	}

	/**
	 * @param ajql
	 * @return
	 */
	public Object[][] array(String ajql) {
		return createQuery(ajql).array();
	}

	/**
	 * @param ajql
	 * @return
	 */
	public Object[] unique(String ajql) {
		return createQuery(ajql).unique();
	}

	/**
	 * @param ajql
	 * @return
	 */
	public Record record(String ajql) {
		return createQuery(ajql).record();
	}
}
