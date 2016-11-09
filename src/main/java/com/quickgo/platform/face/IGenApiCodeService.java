package com.quickgo.platform.face;

import java.util.List;

/**
 * Created by huangjie
 * on 2016/11/4.
 */
public interface IGenApiCodeService {

    int genApiCode(List<String> fids,String packageName);
}
