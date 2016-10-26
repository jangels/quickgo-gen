package com.qitoon.framework.dao;


import java.io.Serializable;
import java.util.List;

/**
 * 公共Dao接口
 * @author hugy
 * @date 2016-05-05
 * Copyright: Copyright (c) 2016 
 * Company:ToB中心
 */
public interface BaseDao<T extends Serializable> {

	/**
	 * 插入一条记录
	 * @param entity 实体对象
	 * @return 修改条数，自增ID的话并更改entity的主键字段
	 */
	  int insert(T entity);

	/**
	 * 字段可选择性的插入一条记录
	 * @param entity 实体对象
	 * @return 插入条数
	 */
	  int insertSelective(T entity);

	/**
	 * 根据主键删除一个记录
	 * @param id 主键ID
	 * @return 删除条数
	 */
	  int deleteById(Long id);
    
    /**
     * 根据主键查询一条记录
     * @param id 主键ID
     * @return 实体对象
     */
	   T selectById(Long id);

	/**
     * 根据主键查询一条记录
     * @param entity 主键ID
     * @return 修改行数
     */
	  int updateByIdSelective(T entity);

	/**
	 * 修改一条记录
	 * @param entity 实体对象
	 * @return 修改行数
	 */
	  int updateById(T entity);

	
	/**
	 * 查询所有数据，用于下拉列表显示数据，数据量大的情况下禁止使用
	 * @return 数据列表
	 */
	   List<T> queryAll();
	
	/**
	 * 获取数据总个数
	 * @return
	 */
	   int getCount();
    
	/**
	 * 根据主键判断数据是否已存在
	 * @param id 主键ID
	 * @return true/false
	 */
	  int queryCheck(Long id);
	
}
