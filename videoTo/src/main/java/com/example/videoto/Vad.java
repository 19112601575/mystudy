package com.example.videoto;

import be.tarsos.dsp.SilenceDetector;
import be.tarsos.dsp.io.TarsosDSPAudioFloatConverter;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;

import java.io.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Vad {
        private ConcurrentLinkedQueue<byte[]> audioQueue = new ConcurrentLinkedQueue<byte[]>();
        private boolean isRunning = false; // 识别引擎是否在执行识别
        private boolean isFinishReadFile = false; // 是否读取完文件
        private String filePath;
        private int readLength = 1600; // 100ms音频的字节数
        private int noinputTimeout = 1000; //跳过开始多少ms
        private int silenceMaxTimes = 10; // 以100ms为单位 检测连续的多少次静音
        private float sampleRate = 8000; // 采样率
        private int sampleSizeInBits = 16; //位深度

        public Vad(String filePath, float sampleRate, int sampleSizeInBits, int noinputTimeout, int silenceTimeout) {
            this.filePath = filePath;
            this.sampleRate = sampleRate;
            this.sampleSizeInBits = sampleSizeInBits;
            //根据参数计算100ms音频的字节数
            this.readLength = (int)sampleRate*(sampleSizeInBits/8)/10;
            this.noinputTimeout = noinputTimeout;
            //计算检测几个 100毫秒单位长度
            this.silenceMaxTimes = (int)silenceTimeout/100;
        }


        public void start() {
            File audioFile = new File(this.filePath);
            FileInputStream fis;
            try {
                audioQueue.clear();
                setIsRuning(true);
                isFinishReadFile = false;
                Thread sttThread = new Thread(vadRunbale);
                sttThread.start();
                fis = new FileInputStream(audioFile);
                byte[] byteArr = new byte[this.readLength];

                int size;
                while ((size = fis.read(byteArr)) != -1) {
                    audioQueue.add(byteArr.clone());
                }

                while (!audioQueue.isEmpty()) {
                    // DebugLog.Log("队列还有内容,等待读取,延迟结束...");
                    Thread.sleep(2000);
                }
                isFinishReadFile = true;
                fis.close();
                while (sttThread.isAlive()) {
                    // DebugLog.Log("等待识别线程结束...");
                    Thread.sleep(2000);
                }
                System.out.println("正常结束");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


        private Runnable vadRunbale = new Runnable() {
            @Override
            public void run() {
                int currentPartTime = 0;
                int silenceTimes = 0;
                try {
                    //检测后的目标结果文件
                    FileOutputStream fileOutputStream = new FileOutputStream("D:\\pro\\videoToWav\\vad\\vad.wav");
                    while (!isFinishReadFile) {// 条件是主动结束,并且队列中已经没有数据
                        if (getIsRuning()) {
                            // 取出byte[]
                            byte[] data = audioQueue.poll();
                            if (data == null) {
                                Thread.sleep(50);
                                continue;
                            }
                            // 使用tarsos检测静音
                            TarsosDSPAudioFormat tdspFormat = new TarsosDSPAudioFormat(sampleRate, sampleSizeInBits, 1, true, false);
                            float[] voiceFloatArr = new float[readLength / tdspFormat.getFrameSize()];
                            TarsosDSPAudioFloatConverter audioFloatConverter = TarsosDSPAudioFloatConverter.getConverter(tdspFormat);
                            audioFloatConverter.toFloatArray(data.clone(),voiceFloatArr);
                            SilenceDetector silenceDetector = new SilenceDetector();
                            boolean isSlience = silenceDetector.isSilence(voiceFloatArr);

                            //以100ms为单位多次检测静音
                            if (currentPartTime >= noinputTimeout) {
                                if (isSlience) {
                                    System.out.println("检测到静音");
                                    // 检测连续静音到达最大值 结束
                                    if(++silenceTimes >=silenceMaxTimes){
                                        setIsRuning(false);
                                        isFinishReadFile = true;//检测到静音就不需要等待文件读取完成
                                    }
                                } else {
                                    System.out.println("活动状态");
                                    //重置静音次数
                                    silenceTimes = 0;
                                    Thread.sleep(1);
                                }
                            } else {
                                Thread.sleep(1);
                            }
                            currentPartTime = currentPartTime + 100;
                            fileOutputStream.write(data);

                        } else {
                            Thread.sleep(10);
                        }
                    }

                    fileOutputStream.close();
//                    IOUtils.closeQuietly(fileOutputStream);
                    System.out.println("退出");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        private synchronized void setIsRuning(boolean runing) {
            isRunning = runing;
        }

        private synchronized boolean getIsRuning() {
            return isRunning;
        }


    public static void main(String[] args) {
        String filePath = "D:\\pro\\videoToWav\\video.wav";
        new Vad(filePath,16000,16000,1000,1000).start();
    }
}
