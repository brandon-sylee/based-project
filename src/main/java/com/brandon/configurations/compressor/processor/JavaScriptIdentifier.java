package com.brandon.configurations.compressor.processor;

import org.mozilla.javascript.Token;

/**
 * Created by brandon Lee(lz90011@linecorp.com) on 2016-06-24.
 */
class JavaScriptIdentifier extends JavaScriptToken {
    private int refcount = 0;
    private String mungedValue;
    private ScriptOrFnScope declaredScope;
    private boolean markedForMunging = true;

    JavaScriptIdentifier(String value, ScriptOrFnScope declaredScope) {
        super(Token.NAME, value);
        this.declaredScope = declaredScope;
    }

    ScriptOrFnScope getDeclaredScope() {
        return declaredScope;
    }

    String getMungedValue() {
        return mungedValue;
    }

    void setMungedValue(String value) {
        mungedValue = value;
    }

    void preventMunging() {
        markedForMunging = false;
    }

    boolean isMarkedForMunging() {
        return markedForMunging;
    }

    void incrementRefcount() {
        refcount++;
    }

    int getRefcount() {
        return refcount;
    }
}
