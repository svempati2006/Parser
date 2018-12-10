package com.ef.Parser.util;

import java.io.File;
import java.io.FileNotFoundException;
public class ParserUtil {

	public static File loadAccessFileFromClassPath() throws FileNotFoundException {
	    String fileName = "access.log";
        ClassLoader classLoader = new ParserUtil().getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
	}
}
