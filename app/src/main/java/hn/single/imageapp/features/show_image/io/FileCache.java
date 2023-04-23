package hn.single.imageapp.features.show_image.io;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import hn.single.imageapp.common.utils.Logger;

public class FileCache {

    private final File cacheDir;

    public FileCache(Context context) {
        //Find the dir at SDCARD to save cached images
        /* if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = new File(Environment.getExternalStorageDirectory(), "LazyList");*/
        String externalStorageState = Environment.getExternalStorageState();
        Logger.INSTANCE.d("LoadImage -- externalStorageState == " + externalStorageState);
        if (externalStorageState.equals(Environment.MEDIA_MOUNTED)) {
            //if SDCARD is mounted (SDCARD is present on device and mounted)
            cacheDir = new File(Environment.getExternalStorageDirectory(), "LazyList");
        } else {
            Logger.INSTANCE.d("LoadImage -- Else --- Environment.getExternalStorageState() " + externalStorageState);
            // if checking on simulator the create cache dir in your application context
            cacheDir = context.getCacheDir();
        }
        if (!cacheDir.exists()) {
            Logger.INSTANCE.d("LoadImage -- cache dir not exists");
            boolean mkdirs = cacheDir.mkdirs();
        }
    }

    public File getFile(String url) {
        //Identify images by hashcode or encode by URLEncoder.encode.
        String filename = String.valueOf(url.hashCode());
        return new File(cacheDir, filename);
    }

    public void clear() {
        // list all files inside cache directory
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        //delete all cache directory files
        for (File f : files) {
            boolean delete = f.delete();
            if (delete) {
                Logger.INSTANCE.d("LoadImage -- delete Success");
            }
        }
    }

}