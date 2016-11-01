package com.qitoon.framework.service;

import com.qitoon.framework.dao.ShareMapper;
import com.qitoon.framework.face.IShareService;
import com.qitoon.framework.model.Share;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ShareService implements IShareService {

    @Autowired
    private ShareMapper shareMapper;


    @Override
    public List<Share> getSharesByProjectId(String projectId) {
        return shareMapper.getSharesByProjectId(projectId);

    }

    @Override
    public int create(Share share) {
        return shareMapper.insert(share);
    }

    @Override
    public Share getShare(String id) {
        return shareMapper.selectById(id);
    }

    @Override
    public int updateShare(Share share) {
        return shareMapper.updateById(share);
    }
}
