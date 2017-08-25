package com.test.mock;

import java.io.File;

public class ClassUnderTest {
    public long methodToTest() {
        final long id = IdGenerator.generateNewId();
        return id;
    }

    public boolean createDirectoryStructure(String directoryPath) {
        File directory = new File(directoryPath);
        if (directory.exists()) {
            String msg = "\"" + directoryPath + "\" 已经存在.";
            throw new IllegalArgumentException(msg);
        }
        return directory.mkdirs();
    }

}