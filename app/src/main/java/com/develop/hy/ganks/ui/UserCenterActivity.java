package com.develop.hy.ganks.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.develop.hy.ganks.BaseActivity;
import com.develop.hy.ganks.R;
import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.model.UserFile;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.presenter.FavoritePresenter;
import com.develop.hy.ganks.ui.view.GlideImageLoader;
import com.develop.hy.ganks.utils.ToastUtils;
import com.yancy.gallerypick.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;

import static com.develop.hy.ganks.utils.Utils.GetRoundedCornerBitmap;

/**
 * Created by HY on 2017/9/14.
 */

public class UserCenterActivity extends BaseActivity implements View.OnClickListener,IFavoriteView {

    private static final String TAG = "UserCenterActivity";
    private static boolean IS_USER_BG = true;
    @BindView(R.id.bt_logout)
    TextView logout;
    @BindView(R.id.bt_collect)
    TextView collect;
    @BindView(R.id.bt_clear_cache)
    TextView clear;
    @BindView(R.id.tv_username)
    TextView username;
    @BindView(R.id.user_header)
    ImageView userHeader;
    @BindView(R.id.image)
    ImageView user_bg;
    private FavoritePresenter presenter;
    private ArrayList<Favorite> list= new ArrayList<>();;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 8;
    private List<String> path = new ArrayList<>();
    private IHandlerCallBack iHandlerCallBack;
    private PhotoAdapter photoAdapter;
    private GalleryConfig galleryConfig;//头像裁剪配置
    private IHandlerCallBack iHandlerCallBack2;

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        initEvent();
        setTitle("");
        User bmobUser = BmobUser.getCurrentUser(User.class);
        username.setText(bmobUser.getUsername());
        presenter = new FavoritePresenter(this,this);
        presenter.init();
        initGallery();

        galleryConfig = new GalleryConfig.Builder()
                .imageLoader(new GlideImageLoader())    // ImageLoader 加载框架（必填）
                .iHandlerCallBack(iHandlerCallBack)     // 监听接口（必填）
                .provider("com.yancy.gallerypickdemo.fileprovider")   // provider(必填)
                .pathList(path)                         // 记录已选的图片
                .multiSelect(false)                      // 是否多选   默认：false
                .multiSelect(false, 1)                   // 配置是否多选的同时 配置多选数量   默认：false ， 9
                .maxSize(1)                             // 配置多选时 的多选数量。    默认：9
                .crop(true)                             // 快捷开启裁剪功能，仅当单选 或直接开启相机时有效
                .crop(true, 1, 1, 200, 200)             // 配置裁剪功能的参数，   默认裁剪比例 1:1
                .isShowCamera(true)                     // 是否现实相机按钮  默认：false
                .filePath("/Gallery/Pictures")          // 图片存放路径
                .build();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
    }

    @Override
    protected void onPreCreate() {
        super.onPreCreate();
    }

    private void initEvent() {
        logout.setOnClickListener(this);
        collect.setOnClickListener(this);
        clear.setOnClickListener(this);
        user_bg.setOnClickListener(this);
        userHeader.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.usercenter_layout;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_logout:
                BmobUser.logOut();
                finish();
                break;
            case R.id.bt_collect:
                getFavorite();
                break;
            case R.id.bt_clear_cache:
                ToastUtils.showShortToast("功能正在开发中");
                break;
            case R.id.image:
                IS_USER_BG = true;
                pushImage();
                break;
            case R.id.user_header:
                IS_USER_BG = false;
                pushImage();
                break;

        }
    }


    private void pushImage() {
        checkPermissions();
    }
    private void initGallery() {
        iHandlerCallBack = new IHandlerCallBack() {
            @Override
            public void onStart() {
                Log.i(TAG, "onStart: 开启");
            }

            @Override
            public void onSuccess(List<String> photoList) {
                Log.i(TAG, "onSuccess: 返回数据");
                path.clear();
                for (String s : photoList) {
                    Log.i(TAG, s);
                    path.add(s);
                }
                if (IS_USER_BG){
                    presenter.pushImage(path,true);//上传背景图
                }else {
                    presenter.pushImage(path,false);//上传头像
                }
            }

            @Override
            public void onCancel() {
                Log.i(TAG, "onCancel: 取消");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 结束");
            }

            @Override
            public void onError() {
                Log.i(TAG, "onError: 出错");
            }
        };

    }

    private void checkPermissions() {
        int checkCode = ContextCompat.checkSelfPermission(UserCenterActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int checkRead = ContextCompat.checkSelfPermission(UserCenterActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);
        //如果拒绝
        if (checkCode== PackageManager.PERMISSION_DENIED||checkRead==PackageManager.PERMISSION_DENIED){
            //申请权限
            if (checkCode==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }
            if (checkRead==PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
            }
        }else if (checkCode==PackageManager.PERMISSION_GRANTED){
                Log.d(TAG,IS_USER_BG+"");
                GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserCenterActivity.this);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[],int[] grantResults) {
            if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "同意授权");
                    // 进行正常操作
                        GalleryPick.getInstance().setGalleryConfig(galleryConfig).open(UserCenterActivity.this);
                } else {
                    Log.i(TAG, "拒绝授权");
                }
            }
    }
    private void getFavorite() {
        if (list.size()==0){
            ToastUtils.showShortToast("当前还没有收藏任何内容");
            return;
        }else {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Favorite",list);
            startActivity(new Intent(UserCenterActivity.this, CommonListActivity.class)
                    .putExtra("Data",bundle));
        }
    }


    @Override
    public void initViews() {
        presenter.getUserBgAndHeadImg();
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showErrorData() {

    }

    @Override
    public void showNoMoreData() {
        ToastUtils.showShortToast("当其无任何收藏内容");
    }

    @Override
    public void initData(List<Favorite> favorites) {
        list.clear();
        list.addAll(favorites);
    }

    @Override
    public void initUserInfo(List<UserFile> imgInfo) {

        Glide.with(this).load(imgInfo.get(0).getHeadImg()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                userHeader.setImageBitmap(GetRoundedCornerBitmap(resource,120));
            }
        });
        Log.d(TAG,imgInfo.get(0).getHeadImg());
        Glide.with(this).load(imgInfo.get(0).getBgimg()).placeholder(R.mipmap.e).into(user_bg);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getFavorite();
    }

}
