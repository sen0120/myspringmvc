package com.test.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ClassUnderTest.class)
public class TestConstruction {
    //模拟构造函数
    @Test
    public void createDirectoryStructureWhenPathDoesntExist() throws Exception {
        final String directoryPath = "seemygod";
        //创建File的模拟对象
        File directoryMock = PowerMockito.mock(File.class);
        //在当前测试用例下,当出现new File("seemygod")时,就返回模拟对象
        PowerMockito.whenNew(File.class).withArguments(directoryPath).thenReturn(directoryMock);
        //当调用模拟对象的exists时,返回false
        PowerMockito.when(directoryMock.exists()).thenReturn(false);
        //当调用模拟对象的mkdirs时,返回tre
        PowerMockito.when(directoryMock.mkdirs()).thenReturn(true);
        assertTrue(new ClassUnderTest().createDirectoryStructure(directoryPath));
        //验证new File(directoryPath);  是否被调用过
        PowerMockito.verifyNew(File.class).withArguments("qdwd");
    }
}