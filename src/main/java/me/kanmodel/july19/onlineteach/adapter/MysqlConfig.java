package me.kanmodel.july19.onlineteach.adapter;

import org.hibernate.dialect.MySQL5Dialect;
import org.springframework.stereotype.Component;

@Component
public class MysqlConfig extends MySQL5Dialect {

    @Override
    public String getTableTypeString() {
        return "DEFAULT CHARSET=utf8";
    }
}
