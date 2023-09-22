package com.byteteacher.library.quicklog;


import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class LogRunner extends Thread {

    private static final String TAG = "logsave";
    private final BlockingQueue<String> blockingQueue;
    public static String LOG_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/com.pda.logs";
    public static String LOG_FILE_NAME = "cjlog.txt";

    public LogRunner(BlockingQueue<String> blockingQueue) {
        super("hz-log-thread");
        this.blockingQueue = blockingQueue;
    }


    @Override
    public void run() {
        Log.e(TAG, "日志线程开始运行！！！");
        while (true) {
            try {
//                Log.e(TAG, "run: 等待写" );
                String take = blockingQueue.take();
//                Log.e(TAG, "run: 写完成" );

                writeLogToFile(take);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG, "日志线程停止运行，" + e.getMessage());
                break;
            }
        }
    }


    /**
     * 写日志进文件
     *
     * @param content  日志内容
     */
    private void writeLogToFile(String content) {

        BufferedWriter bufferedWriter = null;
        try {
            File logPath = new File(LOG_FILE_PATH);
            if (!logPath.exists()) {
                boolean mkdirs = logPath.mkdirs();
                Log.e(TAG, "创建日志文件夹: " + mkdirs);
            }

            File file = new File(logPath, LOG_FILE_NAME);
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            bufferedWriter.write(content);
            bufferedWriter.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
