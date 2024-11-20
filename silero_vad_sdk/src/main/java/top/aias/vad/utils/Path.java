package top.aias.vad.utils;

public class Path {
    int count;
    public String path(){
        String outpath = "D:\\pro\\videoToWav\\wavcut\\my\\" + count + ".wav";
        return outpath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
