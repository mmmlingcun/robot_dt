package com.meng.robot_dt.education.util.kit;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author taorun
 * @date 2022/12/15 15:21
 */
public class CollectionKit extends CollectionUtils {

    public static <T> List<T> mergeToList(Collection<T>... collections) {
        List<T> list = new ArrayList<>();
        for (Collection<T> collection : collections) {
            if (Objects.isNull(collection)) {
                continue;
            }
            list.addAll(collection);
        }
        return list;
    }

}
