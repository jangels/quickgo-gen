/**
 *
 */
package com.quickgo.platform.service;

import com.quickgo.platform.annotation.Service;
import com.quickgo.platform.dao.DateSourceMapper;
import com.quickgo.platform.face.IDateSourceService;
import com.quickgo.platform.model.DateSource;
import com.quickgo.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;

/**
 * 业务表Service
 *
 * @version 2013-10-15
 */
@Service
@Transactional
public class DateSourceServiceImp implements IDateSourceService {

	@Autowired
	private DateSourceMapper dateSourceMapper;

	@Transactional(readOnly = false)
	public void save(DateSource dateSourceModel) {
		if (StringUtils.isBlank(dateSourceModel.getId())){
			dateSourceModel.preInsert();
			dateSourceMapper.insert(dateSourceModel);
		}else{
			dateSourceModel.preUpdate();
			dateSourceMapper.update(dateSourceModel);
		}

	}



	@Transactional(readOnly = false)
	public void delete(DateSource dateSourceModel) {

		dateSourceMapper.delete(dateSourceModel);
	}

	@Transactional(readOnly = false)
	public DateSource get( String id) {
		DateSource dateSource=dateSourceMapper.get(id);
		return dateSource;
	}
}
