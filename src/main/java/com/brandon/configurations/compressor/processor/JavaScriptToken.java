package com.brandon.configurations.compressor.processor;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-06-24.
 */
public class JavaScriptToken {
    private int type;
    private String value;

    JavaScriptToken(int type, String value) {
        this.type = type;
        this.value = value;
    }

    int getType() {
        return type;
    }

    String getValue() {
        return value;
    }
}
