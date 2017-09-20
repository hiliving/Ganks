package com.develop.hy.ganks.presenter;

import android.content.Context;
import android.util.Log;

import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.User;
import com.develop.hy.ganks.presenter.CommenInterface.IFavoriteView;
import com.develop.hy.ganks.utils.ToastUtils;

import java.util.List;
import java.util.logging.Logger;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Helloworld on 2017/9/20.
 */

public class FavoritePresenter extends BasePresenter<IFavoriteView> {

    public FavoritePresenter(Context context, IFavoriteView iView) {
        super(context, iView);
    }

    @Override
    public void release() {
        unSubcription();
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
                    iView.initData(list);
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
                ToastUtils.showShortToast("刪除收藏成功");
                Log.d("AAAAAAAAAA",e.toString());

            }
        });
    }
}
