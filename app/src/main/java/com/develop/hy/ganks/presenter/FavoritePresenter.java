package com.develop.hy.ganks.presenter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Movie;
import android.util.Log;

import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.utils.ToastUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ProgressCallback;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Helloworld on 2017/9/20.
 */

public class FavoritePresenter extends BasePresenter<IFavoriteView> {
    private String TAG = "FavoritePresenter";
    private final User user;

    public FavoritePresenter(Context context, IFavoriteView iView) {
        super(context, iView);
        user = BmobUser.getCurrentUser(User.class);
    }

    @Override
    public void release() {
        unSubcription();
    }
    public void  getUserBgAndHeadImg(){

        BmobQuery<UserFile> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",user);
        query.order("-updateAt");
        query.include("userId");//希望查询头像的同时也把用户背景图也查询出来
        query.findObjects(new FindListener<UserFile>() {
            @Override
            public void done(List<UserFile> list, BmobException e) {
                if (list!=null){
                   iView.initUserInfo(list);
                }else {

                }
            }
        });
    }


    public void getFavorite() {
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",user);
        query.order("-updateAt");
        query.include("userId");//希望查询收藏的同时也把用户信息也查询出来
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> list, BmobException e) {
                if (list!=null){
                    iView.initData(list);
                }else {
                    iView.showErrorData();
                }
            }
        });
    }

    public void deleteFavorite(int position, final List<Favorite> favorites) {
        Favorite favorite = new Favorite();
        favorite.setObjectId(favorites.get(position).getObjectId());
        favorite.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                ToastUtils.showShortToast("刪除收藏成功");
            }
        });
    }

    public void pushImage(List<String> path) {

        final ProgressDialog  dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("上传中...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final User user = BmobUser.getCurrentUser(User.class);


        File file = new File(path.get(0));
        final BmobFile bmobFile = new BmobFile(file);

        bmobFile.uploadObservable(new ProgressCallback() {//上传文件操作
            @Override
            public void onProgress(Integer value, long total) {
                log("uploadMovoieFile-->onProgress:"+value);
                dialog.setProgress(value);
            }
        }).doOnNext(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
               String url = bmobFile.getUrl();
                log("上传成功："+url+","+bmobFile.getFilename());
                dialog.dismiss();
            }
        }).concatMap(new Func1<Void, Observable<String>>() {//将bmobFile保存到movie表中
            @Override
            public Observable<String> call(Void aVoid) {
                UserFile userFile = new UserFile();
                userFile.setUserId(user);
                userFile.setUsername(user.getUsername());
                userFile.setBgimg(bmobFile.getUrl());
                return saveObservable(userFile);
            }
        });
        /*
        这个是下载文件用，这里用不到，我们用Glide会自动做缓存
        .concatMap(new Func1<String, Observable<String>>() {//下载文件
            @Override
            public Observable<String> call(String s) {
                return bmobFile.downloadObservable(new ProgressCallback() {
                    @Override
                    public void onProgress(Integer value, long total) {
                        log("download-->onProgress:"+value+","+total);
                    }
                });
            }
        })
            .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("--onCompleted--");
            }

            @Override
            public void onError(Throwable e) {
                log("--onError--:"+e.getMessage());
                dialog.dismiss();
            }

            @Override
            public void onNext(String s) {
                dialog.dismiss();
                log("download的文件地址：");
            }
        });*/

    }

    private void log(String s) {
        Log.d(TAG,s);
    }

    /**
     * save的Observable
     * @param obj
     * @return
     */
    private Observable<String> saveObservable(BmobObject obj){
        return obj.saveObservable();
    }

    public void pushUserBgImg(List<String> path) {
       /* final ProgressDialog  dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setTitle("上传中...");
        dialog.setIndeterminate(false);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final User user = BmobUser.getCurrentUser(User.class);


        File file = new File(path.get(0));
        final BmobFile bmobFile = new BmobFile(file);

        bmobFile.uploadObservable(new ProgressCallback() {//上传文件操作
            @Override
            public void onProgress(Integer value, long total) {
                log("uploadMovoieFile-->onProgress:"+value);
                dialog.setProgress(value);
            }
        }).doOnNext(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                String url = bmobFile.getUrl();
                log("上传成功："+url+","+bmobFile.getFilename());
                dialog.dismiss();
            }
        }).concatMap(new Func1<Void, Observable<String>>() {//将bmobFile保存到movie表中
            @Override
            public Observable<String> call(Void aVoid) {
                UserFile userFile = new UserFile();
                userFile.setUserId(user);
                userFile.setBgimg(bmobFile.getUrl());
                return saveObservable(userFile);
            }
        });*/
    }
}
