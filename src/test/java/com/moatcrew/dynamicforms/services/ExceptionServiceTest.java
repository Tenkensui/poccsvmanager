package com.moatcrew.dynamicforms.services;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by e-mcce on 03/05/2016.
 */
public class ExceptionServiceTest extends AbstractTest {

    @Test
    public void getExceptionsTest() throws Exception {
        List<String> exceptions = exceptionService.getExceptions();
        Assert.assertTrue(exceptions.size() > 0);
    }

    @Test
    public void isExceptionTest() throws Exception {
        Assert.assertTrue(exceptionService.isException("test3"));
        Assert.assertTrue(exceptionService.isException("TeSt3"));
        Assert.assertFalse(exceptionService.isException("test2"));
    }
}
