package com.quickgo.platform.dao;

import com.quickgo.platform.model.Share;

import java.util.List;


public interface ShareMapper extends BaseDao<Share> {

     List<Share> getSharesByProjectId(String projectId);


}
