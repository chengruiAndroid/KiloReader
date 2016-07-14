package com.wantide.cr_chen.kiloreader.utils.imagecache;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by CR_Chen on 2016/6/29.
 */
public class FileCache {
    private final static String IMAGECACHE = "qianyue/baidejie/ImageCache";
    private final static String lASTPATHNAME = ".cache"; // 文件名

    private final static int MB = 1024 * 1024;
    private final static int CACHESIZE = 10;
    private final static int SDCARD_FREE_SPANCE_CACHE = 10;

    public FileCache() {
        removeCache(getDirectory());
    }

    /**
     * 将图片存入缓存
     *
     * @param url
     *            ： 地址
     * @param bitmap
     *            ： 图片
     */
    public void saveBitmap(String url, Bitmap bitmap) {
        if (bitmap == null) {
            return;
        }
        if (SDCARD_FREE_SPANCE_CACHE > caluateSDCardFreeSpance()) {
            return; // 空间不足
        }
        String fileName = convertUrlToFileName(url);
        String dirPath = getDirectory();
        File dirFile = new File(dirPath);
        if (dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dirPath + "/" + fileName);
        try {
            file.createNewFile();
            OutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("文件未找到或者io异常");
        }
    }

    /**
     * 获取文件缓存图片
     *
     * @param url
     *            ： 地址
     * @return ： bitmap
     */
    public Bitmap getBitmap(final String url) {
        Bitmap bitmap = null;
        final String path = getDirectory() + convertUrlToFileName(url);
        File file = new File(path);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(path);
            if (bitmap == null) {
                file.delete();
            } else {
                updateFileTime(path);
            }
        }
        return bitmap;
    }

    /**
     * 获取sdCard路径
     *
     * @return ：路径地址
     */
    private String getSDCardPath() {
        String path = "";
        File file = null;
        boolean isSDCardExist = Environment.getExternalStorageState()
                .toString().equals(android.os.Environment.MEDIA_MOUNTED); // 判断是否有sdCard
        if (isSDCardExist) {
            file = Environment.getExternalStorageDirectory();
        }
        if (file != null) {
            path = file.toString();
        }
        return path;
    }

    /**
     * 计算存储目录下的文件大小，
     * 当文件总大小大于规定的CACHE_SIZE或者sdcard剩余空间小于FREE_SD_SPACE_NEEDED_TO_CACHE的规定
     * 那么删除40%最近没有被使用的文件
     */
    private boolean removeCache(String dirPath) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return true;
        }

        if (!android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            return false;
        }

        int dirSize = 0;
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().contains(lASTPATHNAME)) {
                dirSize += files[i].length();
            }
        }

        if (dirSize > CACHESIZE * MB
                || SDCARD_FREE_SPANCE_CACHE > caluateSDCardFreeSpance()) {
            int removeFactor = (int) ((0.4 * files.length) + 1);
            Arrays.sort(files, new FileLastModifSort());
            for (int i = 0; i < removeFactor; i++) {
                if (files[i].getName().contains(lASTPATHNAME)) {
                    files[i].delete();
                }
            }
        }

        if (caluateSDCardFreeSpance() <= CACHESIZE) {
            return false;
        }

        return true;
    }

    /**
     * 获取缓存目录
     *
     * @return : 目录
     */
    private String getDirectory() {
        return getSDCardPath() + "/" + IMAGECACHE;
    }

    /**
     * 将url转换成文件名
     *
     * @param url
     *            ： 地址
     * @return ： 文件名
     */
    private String convertUrlToFileName(final String url) {
        String[] strs = url.split("/");
        return strs[strs.length - 1] + lASTPATHNAME;
    }

    /**
     * 计算sdCard上的空闲空间
     *
     * @return ： 大小
     */
    @SuppressLint("NewApi")
    private int caluateSDCardFreeSpance() {
        int freespance = 0;
        StatFs start = new StatFs(Environment.getExternalStorageDirectory()
                .getPath());
        long blocksize = start.getBlockSizeLong();
        long availableBlocks = start.getAvailableBlocksLong();
        freespance = Integer.parseInt(blocksize * availableBlocks + "");
        return freespance;
    }

    /** 修改文件的最后修改时间 **/
    public void updateFileTime(String path) {
        File file = new File(path);
        long lastTime = System.currentTimeMillis();
        file.setLastModified(lastTime);
    }

    /** 根据文件的最后修改时间进行排序 **/
    private class FileLastModifSort implements Comparator<File> {
        public int compare(File arg0, File arg1) {
            if (arg0.lastModified() > arg1.lastModified()) {
                return 1;
            } else if (arg0.lastModified() == arg1.lastModified()) {
                return 0;
            } else {
                return -1;
            }
        }
    }
}
