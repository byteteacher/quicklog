package com.byteteacher.library.quicklog;


import android.os.Process;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LogSaver {

    private static final String TAG = "logsave";
    private static LogRunner logRunner;
    private static final BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
    private static SimpleDateFormat sdf_yyyyMMddHHmmss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());


    /**
     * 初始化启动日志线程
     */
    public static void init() {
        if (logRunner != null && logRunner.isAlive()) {
            Log.e(TAG, "日志线程已在运行当中！");
            return;
        }
        logRunner = new LogRunner(blockingQueue);
        logRunner.start();
    }


    /**
     * 将需要打印的app运行日志，加入到打印队列中
     */
    private static void save(String content) {
        try {
            blockingQueue.put(content);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 运行日志
     *
     * @param content 内容
     * @param tag     tag
     */
    public static void saveAppLog(String content, String tag) {
        String stackTraceContent = formatHead(tag) + getStackTrace(content);
        save(stackTraceContent);
    }

    /**
     * 获取统一格式的日志头信息
     * 时间 线程 TAG
     */
    private static String formatHead(String tag) {
        int tid = Process.myTid();
        String hms_sss = sdf_yyyyMMddHHmmss.format(new Date());
        return String.format(Locale.getDefault(), "%s %-6.6s %-10.10s", hms_sss, tid, tag);
    }


    private static String getStackTrace(String content) {
        Throwable throwable = new Throwable();
        //LogUtil.LOG()的depth是0, LogUtil.e()函数的depth是1, 调用者的depth是2,调用者的调用者是3
        int methodDepth = 2;
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        if (methodDepth < stackTraceElements.length) {
            StackTraceElement element = stackTraceElements[methodDepth];
            if (element != null && element.getFileName() != null) {
//                String className = element.getFileName().substring(0, element.getFileName().lastIndexOf("."));
                content = String.format(Locale.getDefault(), " [(%s:%d).%s()] %s", element.getFileName(), element.getLineNumber(), element.getMethodName(), content);
            }
        }
        return content;
    }

}
