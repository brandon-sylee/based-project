package com.brandon;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by brandon Lee on 2016-08-26.
 */
public class OptionalTest {
    final Logger logger = getLogger(getClass());
    @Test
    public void optionalTest1() {
        Integer x = null;
        Assert.assertFalse(Optional.ofNullable(x).map(integer -> integer <= 1).orElse(false));

        Integer x1 = 1;
        Assert.assertTrue(Optional.ofNullable(x1).map(integer -> integer <= 1).orElse(false));

        Integer x2 = 1;
        Assert.assertEquals(Integer.valueOf(1), Optional.ofNullable(x2).orElse(-1));

        Integer x3 = null;
        Assert.assertEquals(Integer.valueOf(-1), Optional.ofNullable(x3).orElse(-1));
    }
}
