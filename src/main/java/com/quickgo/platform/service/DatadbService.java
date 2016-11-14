/**
 *
 */
package com.quickgo.platform.service;

import com.quickgo.platform.dao.DataBaseMapper;
import com.quickgo.platform.face.IDatadbService;
import com.quickgo.platform.model.DataBase;
import com.quickgo.platform.utils.StringUtils;
import com.quickgo.platform.utils.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 业务表Service
 *
 * on 2016-11-10
 */
@Service
public class DatadbService implements IDatadbService {

	@Autowired
	private DataBaseMapper dataBaseMapper;

	@Override
	public int save(DataBase dataBase) {
		if (StringUtils.isBlank(dataBase.getId())){
			dataBase.setId(Validate.uuid());
			dataBase.setCreateTime(new Date().getTime());
			return dataBaseMapper.insert(dataBase);
		}else{
			dataBase.setUpdateTime(new Date().getTime());
			return dataBaseMapper.update(dataBase);
		}

	}
	@Override
	public int delete(DataBase dataBase) {

		return dataBaseMapper.delete(dataBase);
	}

	@Override
	public int update(DataBase dataBase) {
		return dataBaseMapper.update(dataBase);
	}

	public DataBase get(String id) {
		DataBase dataBase = dataBaseMapper.get(id);
		return dataBase==null?null:dataBase;
	}
}
