/**
 * 
 */
package com.quickgo.platform.dao;


import com.quickgo.platform.base.BaseDao;
import com.quickgo.platform.model.GenScheme;
import org.apache.ibatis.annotations.Param;

/**
 * 生成方案DAO接口
 * 
 * @version 2013-10-15
 */

public interface GenSchemeDao extends BaseDao<GenScheme> {

    GenScheme getSchemeById(@Param("tableId") String tableId, @Param("projectId")String projectId);
}
