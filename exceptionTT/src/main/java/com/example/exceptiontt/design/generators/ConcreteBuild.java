package com.example.exceptiontt.design.generators;

public class ConcreteBuild implements Builder {

    private String tires;
    private String glass;

    public ConcreteBuild(String tires, String glass) {
        this.glass = glass;
        this.tires = tires;
    }

    @Override
    public void makeTires() {
        System.out.println("baomatires");
    }

    @Override
    public void makeGlass() {

    }

    @Override
    public Cars getCars() {
        Cars cars = new Cars();
//        cars.setTires(tires);
//        cars.setGlass(glass);
        return cars;
    }
}
