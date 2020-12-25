package cn.itcast.domain;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class DownThread extends Thread {
    private URL url;
    private File file;
    private int data;
    private int thread;

    public DownThread(URL url, File file, int data, int thread) {
        this.url = url;
        this.file = file;
        this.data = data;
        this.thread = thread;
    }

    @Override
    public void run() {
         // 计算该线程下载文件的开始位置
        int begin = thread*data;
        int end = (thread + 1)*data - 1;
        HttpURLConnection httpURLConnection;
        // 获取文件下载的信息
        try {
            RandomAccessFile rwd = new RandomAccessFile(file, "rwd"); // 创建一个本地的文件
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("get");
            httpURLConnection.setRequestProperty("Range","bytes="+begin+"-"+end);
            httpURLConnection.setConnectTimeout(5*1000);
            InputStream is = httpURLConnection.getInputStream();
            // 开始写入数据
            byte[]bytes = new byte[1024];
            int len = 0;
            while((len = is.read(bytes))!=-1){
                rwd.write(bytes,0,len);
            }
            is.close();
            rwd.close();
            System.out.println("线程:" + thread + "下载完成");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
