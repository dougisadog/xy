package com.doug.paylib.wepay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.doug.paylib.util.WepayManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Objects;

public abstract class WXPayEntryBaseActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryBaseActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutId);

        api = WXAPIFactory.createWXAPI(this, WepayManager.Companion.getAPPID());
        api.handleIntent(getIntent(), this);
    }

    int layoutId;

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //非成功不处理
//			Toast.makeText(WXPayEntryActivity.this, "微信支付返回" +code, Toast.LENGTH_SHORT).show();
            if (resp.errCode != 0) {
                Objects.requireNonNull(WepayManager.getInstance().getPayCallback()).onFailed(resp.errStr);
                WepayManager.getInstance().setPayCallback(null);
                finish();
                return;
            }
            Objects.requireNonNull(WepayManager.getInstance().getPayCallback()).onSuccess();
            WepayManager.getInstance().setPayCallback(null);
            dealWithSuccessResp();
        }
    }

    public abstract void dealWithSuccessResp();
}