package com.example.exceptiontt.board;

import oshi.SystemInfo;
import oshi.hardware.*;

import java.util.List;


public class Getboard2 {

    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        //硬件信息
        HardwareAbstractionLayer hardware = systemInfo.getHardware();

        //cpu信息
        CentralProcessor processor = hardware.getProcessor();

        //处理器型号名
        String name = processor.getProcessorIdentifier().getName();
        //物理处理器数
        int physicalProcessorCount = processor.getPhysicalProcessorCount();
        //处理器核心数
        List<CentralProcessor.PhysicalProcessor> physicalProcessors = processor.getPhysicalProcessors();
        //逻辑处理器数
        int logicalProcessorCount = processor.getLogicalProcessorCount();
        //cpu频率
        long[] currentFreq = processor.getCurrentFreq();
        long maxFreq = processor.getMaxFreq();

        System.out.println("处理器型号：" + name);
        System.out.println("物理处理器数：" + physicalProcessorCount);
        System.out.println("每个cpu的核心数：");
        physicalProcessors.forEach(System.out::println);
        System.out.println("逻辑处理器数：" + logicalProcessorCount);
        System.out.println("cpu频率：" + currentFreq);
        System.out.println("cpu最大频率：" + maxFreq);


        //mac地址
        List<NetworkIF> networkIFs = hardware.getNetworkIFs();
        networkIFs.forEach(System.out::println);
        //内存
        GlobalMemory memory = hardware.getMemory();
        System.out.println("内存"+memory);

        //硬盘
        List<HWDiskStore> diskStores = hardware.getDiskStores();
        diskStores.forEach(System.out::println);

        //显卡
        List<GraphicsCard> graphicsCards = hardware.getGraphicsCards();
        graphicsCards.forEach( System.out::println);
        //主板信息
        ComputerSystem computerSystem = hardware.getComputerSystem();
        Baseboard baseboard = computerSystem.getBaseboard();

        System.out.println("制造商: " + baseboard.getManufacturer());
        System.out.println("主板号: " + baseboard.getSerialNumber());


        //
    }

}
