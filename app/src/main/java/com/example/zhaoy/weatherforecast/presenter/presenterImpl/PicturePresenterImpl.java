package com.example.zhaoy.weatherforecast.presenter.presenterImpl;

import android.graphics.Bitmap;

import com.example.zhaoy.weatherforecast.callback.PicCallback;
import com.example.zhaoy.weatherforecast.model.IBingPicModel;
import com.example.zhaoy.weatherforecast.model.modelImp.BingPicModel;
import com.example.zhaoy.weatherforecast.presenter.IPicturePresenter;
import com.example.zhaoy.weatherforecast.view.IShowPicture;

/**
 * Created by Administrator on 2016/12/27.
 */

public class PicturePresenterImpl implements IPicturePresenter {
    private IBingPicModel bingPicModel;
    private IShowPicture showPicture;

    public PicturePresenterImpl(IShowPicture showPicture) {
        bingPicModel = new BingPicModel();
        this.showPicture = showPicture;
    }

    @Override
    public void loadPicture() {
        bingPicModel.loadBingPic(new PicCallback() {
            @Override
            public void onSuccess(Object success) {
                String bingPic = (String) success;
                showPicture.showPic(bingPic);
            }
            @Override
            public void onError() {

            }
        });
    }
}
