package com.quickgo.platform.face;

import com.quickgo.platform.model.Share;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IShareService {

    List<Share> getSharesByProjectId(String projectId);

    int create(Share share);

    Share getShare(String id);

    int updateShare(Share share);



}
