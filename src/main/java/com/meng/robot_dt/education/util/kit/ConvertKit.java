package com.meng.robot_dt.education.util.kit;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author taorun
 * @date 2022/12/13 18:58
 */
public class ConvertKit {

    public static <T> T convert(Object o, Class<T> classType) {
        try {
            if (Objects.isNull(o)) {
                return null;
            }
            T t = classType.newInstance();
            BeanUtils.copyProperties(o, t);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(List list, Class<T> classType) {
        return (List<T>) list.stream().map(o -> {
            try {
                T t = classType.newInstance();
                BeanUtils.copyProperties(o, t);
                return t;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

}
