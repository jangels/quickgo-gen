package com.quickgo.platform.face;

import com.quickgo.platform.model.DataBase;

/**
 * Created by huangjie
 * on 2016/10/26.
 */
public interface IDatadbService {

    int save(DataBase dataBase);

    DataBase get(String id);

    int delete(DataBase dataBase);

    int update(DataBase dataBase);


}
