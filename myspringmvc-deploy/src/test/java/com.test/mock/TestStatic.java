package com.test.mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.stubbing.OngoingStubbing;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
public class TestStatic {
    // 模拟 Static 方法
    @Test
    public void testCallInternalInstance() throws Exception {
        PowerMockito.mockStatic(IdGenerator.class);
        // 在这个测试用例中,当generateNewId()每次被调用时,都会返回15
        PowerMockito.when(IdGenerator.generateNewId()).thenReturn(15L);
        Assert.assertEquals(15L, new ClassUnderTest().methodToTest());
    }
}