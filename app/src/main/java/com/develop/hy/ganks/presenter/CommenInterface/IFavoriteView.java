package com.develop.hy.ganks.presenter.CommenInterface;

import com.develop.hy.ganks.model.Favorite;
import com.develop.hy.ganks.model.UserFile;

import java.util.List;

/**
 * Created by Helloworld on 2017/9/20.
 */

public interface IFavoriteView extends IBaseView {
    void initData(List<Favorite> favorites);
    void initUserInfo(List<UserFile> favorites);
}
