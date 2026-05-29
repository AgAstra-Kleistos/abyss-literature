package com.abyss.context;

public class BaseContext {

    //创建线程空间
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }

    public static void removeCurrendId(){
        threadLocal.remove();
    }
}
