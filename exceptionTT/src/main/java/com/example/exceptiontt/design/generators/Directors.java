package com.example.exceptiontt.design.generators;

public class Directors {
    public Cars getBaomao(){
        ConcreteBuild factoryBuild = new ConcreteBuild("bao","bao");
        return factoryBuild.getCars();
    }
    public Cars getBenchi(){
        ConcreteBuild factoryBuild = new ConcreteBuild("ben", "ben");
        return factoryBuild.getCars();
    }
}
