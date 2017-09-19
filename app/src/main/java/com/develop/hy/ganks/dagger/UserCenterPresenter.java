package com.develop.hy.ganks.dagger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.ui.CommonListActivity;
import com.develop.hy.ganks.ui.UserCenterActivity;
import com.develop.hy.ganks.utils.ToastUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by HY on 2017/9/19.
 */

public class UserCenterPresenter {
    private Context userCenterActivity;

    public UserCenterPresenter(Context userCenterActivity) {
        this.userCenterActivity = userCenterActivity;
    }


    public void getFavorite() {
        User user = BmobUser.getCurrentUser(User.class);
        BmobQuery<Favorite> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",user);
        query.order("-updateAt");
        query.include("userId");//希望查询收藏的同时也把用户信息也查询出来
        query.findObjects(new FindListener<Favorite>() {
            @Override
            public void done(List<Favorite> list, BmobException e) {
                if (list!=null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Favorite", (Serializable) list);
                    userCenterActivity.startActivity(new Intent(userCenterActivity, CommonListActivity.class)
                    .putExtra("Data",bundle));
                }else {
                }
            }
        });
    }

    public void deleteFavorite(int position, List<Favorite> favorites) {
        Favorite favorite = new Favorite();
        favorite.setObjectId(favorites.get(position).getObjectId());
        favorite.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                ToastUtils.showShortToast("刪除成功");
            }
        });
    }
}
