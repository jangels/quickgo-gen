/**
 * 
 */
package com.quickgo.platform.dao;

import com.quickgo.platform.base.BaseDao;
import com.quickgo.platform.model.GenTable;
import com.quickgo.platform.model.GenTableColumn;

/**
 * 业务表字段DAO接口
 * 
 * @version 2013-10-15
 */

public interface GenTableColumnDao extends BaseDao<GenTableColumn> {
	
	public void deleteByGenTableId(GenTable genTable);
}
