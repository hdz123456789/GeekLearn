package com.learn.geeklearn.learn_week01;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。
 * 文件群里提供。
 */
public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
//            Path path = Paths.get("f:\\learn\\GeekUniversity\\week_01\\Hello.xlass\\Hello.xlass");
//            System.out.println("file.name:"+path.isAbsolute());
//            System.out.println("file.name2:"+path.getParent().isAbsolute());
//            System.out.println("file.name3:"+path.getFileName().isAbsolute());

            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(helloClass.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String myPath = "file:///f://learn//GeekUniversity//Hello.xlass//" + name.replace(".", "/") + ".xlass";
        Path path = null;
        byte[] helloArray = new byte[0];
        try {
            path = Paths.get(new URI(myPath));
            //     System.out.println("file.name:"+path.getFileName());
            helloArray = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < helloArray.length; i++) {
            helloArray[i] = (byte) (255 - helloArray[i]);
        }

        return defineClass(name, helloArray, 0, helloArray.length);
    }


}
