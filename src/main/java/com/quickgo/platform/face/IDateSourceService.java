package com.quickgo.platform.face;

import com.quickgo.platform.model.DateSource;
import com.quickgo.platform.model.Interface;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IDateSourceService {



    void save(DateSource model);



    DateSource get(String id);

    void delete(DateSource dateSourceModel);


}
