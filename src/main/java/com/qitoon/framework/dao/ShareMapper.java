package com.qitoon.framework.dao;

import com.qitoon.framework.model.Share;

import java.util.List;


public interface ShareMapper extends BaseDao<Share> {

     List<Share> getSharesByProjectId(String projectId);


}
