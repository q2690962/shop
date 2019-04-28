package com.db.com.latte_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.db.com.latte_core.app.Latte;
import com.db.com.latte_core.net.callback.IRequest;
import com.db.com.latte_core.net.callback.ISuccess;
import com.db.com.latte_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class SaveFileTask  extends AsyncTask<Object,Void,File> {
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir= (String) params[0];
        String extension= (String) params[1];
        final ResponseBody body= (ResponseBody) params[2];
        final String name= (String) params[3];
        final InputStream is=body.byteStream();
        if(downloadDir==null||downloadDir.equals("")){
            downloadDir="down_loads";
        }
        if(extension==null||extension.equals("")){
            extension="";
        }
        if (name==null){
            return FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);
        }

    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if(SUCCESS!=null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST!=null){
            REQUEST.onRequestEnd();
        }
        auotoInstallApk(file);
    }

    private void auotoInstallApk(File file){
        if(FileUtil.getExtension(file.getPath()).equals("apk")){
            final Intent install=new Intent();
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Latte.getApplication().startActivity(install);

        }
    }
}
