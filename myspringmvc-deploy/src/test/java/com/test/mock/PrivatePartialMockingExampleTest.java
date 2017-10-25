package com.test.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PrivatePartialMockingExample.class)
public class PrivatePartialMockingExampleTest {
    @Test
    public void demoPrivateMethodMocking() throws Exception {
        final String expected = "TEST VALUE";
        final String nameOfMethodToMock = "methodToMock";
        final String input = "input";
        PrivatePartialMockingExample underTest = spy(new PrivatePartialMockingExample());
        when(underTest, nameOfMethodToMock, input).thenReturn(expected);
        assertEquals(expected, underTest.methodToTest());
        verifyPrivate(underTest).invoke(nameOfMethodToMock, input);
    }

}