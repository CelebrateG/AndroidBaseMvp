package com.hazz.example.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hazz.baselibs.base.BaseMvpActivity;
import com.hazz.baselibs.net.download.DownLoadManager;
import com.hazz.baselibs.net.download.ProgressCallBack;
import com.hazz.baselibs.utils.LogUtils;
import com.hazz.example.R;
import com.hazz.example.data.entity.TabEntity;
import com.hazz.example.data.entity.TestNews;
import com.hazz.example.ui.main.home.HomeFragment;
import com.hazz.example.ui.main.mine.MineFragment;
import com.hazz.example.ui.main.video.VideoFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseMvpActivity<MainPresenter> implements MainContract.View {


    private HomeFragment mHomeFragment;
    private VideoFragment mVideoFragment;
    private MineFragment mMineFragment;

    // 顶部滑动的标签栏
    private String[] mTitles = {"首页", "系列", "我的"};
    // 未被选中的图标
    private int[] mIconUnSelectIds = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    // 被选中的图标
    private int[] mIconSelectIds = {R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    // 默认为0;
    private int mCurrIndex = 0;


    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("onCreate...........");
        if (savedInstanceState != null) {
            LogUtils.d("onRestore enter...." + mCurrIndex);
            mCurrIndex = savedInstanceState.getInt("currTabIndex");
        }
        tabLayout.setCurrentTab(mCurrIndex);
        switchFragment(mCurrIndex);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initView() {
        initTab();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        testDownload();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void showData(List<TestNews> testNews) {

    }


    /**
     * 初始化底部菜单
     */
    private void initTab() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnSelectIds[i]));
        }
        //为Tab赋值数据
        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                //切换Fragment
                switchFragment(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        tabLayout.showDot(2);

        tabLayout.showDot(0);

        tabLayout.showMsg(1, 100);
    }

    /**
     * 切换Fragment
     *
     * @param position 下标
     */
    private void switchFragment(int position) {
        // Fragment事务管理器
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        LogUtils.d("current position tab" + position);
        switch (position) {
            case 0: //首页
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance(mTitles[0]);
                    transaction.add(R.id.fl_container, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case 1: //视频
                if (mVideoFragment == null) {
                    mVideoFragment = VideoFragment.getInstance(mTitles[1]);
                    transaction.add(R.id.fl_container, mVideoFragment, "video");
                } else {
                    transaction.show(mVideoFragment);
                }
                break;

            case 2: //更多
                if (mMineFragment == null) {
                    mMineFragment = MineFragment.getInstance(mTitles[2]);
                    transaction.add(R.id.fl_container, mMineFragment, "mine");
                } else {
                    transaction.show(mMineFragment);
                }
                break;
            default:
                if (mHomeFragment == null) {
                    mHomeFragment = HomeFragment.getInstance(mTitles[0]);
                    transaction.add(R.id.fl_container, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
        }
        mCurrIndex = position;
        tabLayout.setCurrentTab(mCurrIndex);
        transaction.commitAllowingStateLoss();

    }

    /**
     * 隐藏所有的Fragment
     *
     * @param transaction transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (null != mHomeFragment) {
            transaction.hide(mHomeFragment);
        }
        if (null != mVideoFragment) {
            transaction.hide(mVideoFragment);
        }
        if (null != mMineFragment) {
            transaction.hide(mMineFragment);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //记录fragment的位置,防止崩溃 activity被系统回收时，fragment错乱
        LogUtils.e("onSaveInstanceState crash..." + mCurrIndex);
        if (tabLayout != null) {
            outState.putInt("currTabIndex", mCurrIndex);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void testDownload(){
        String dir = Environment.getExternalStorageDirectory() + File.separator + "Apk" + File.separator;
        String url = "https://zongsapp-update.oss-cn-hangzhou.aliyuncs.com/20190621/%E5%AE%97%E7%9B%9B%E5%95%A4%E9%85%92%E6%9C%BA_V1.0.4_06_30_release.apk";
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        ProgressDialog progressDialog = new ProgressDialog(this);
        DownLoadManager.getInstance().load(url, new ProgressCallBack(dir,fileName) {
            @Override
            public void onSuccess(Object o) {
                System.out.println("下载成功！");
                System.out.println(dir + fileName);
                progressDialog.setMessage("下载成功！");
                progressDialog.dismiss();
//                install(dir + fileName);
            }

            @Override
            public void progress(long progress, long total) {
                int progressPercent = (int) (progress / total * 100);
                System.out.println(progress + "/" + total + "=" + progressPercent);
                progressDialog.setMessage("下载进度：" + progressPercent + "%");
                progressDialog.show();
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("下载失败！");
                progressDialog.dismiss();
            }
        });
    }

    /**
     * 静默安装
     *
     * @param apkPath
     * @return
     */
    public boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;
        try {
            // 申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());
            // 执行pm install命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();
            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            Log.d("TAG", "install msg is " + msg);
            // 如果执行结果中包含Failure字样就认为是安装失败，否则就认为安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (Exception e) {
            Log.e("TAG", e.getMessage(), e);
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (errorStream != null) {
                    errorStream.close();
                }
            } catch (IOException e) {
                Log.e("TAG", e.getMessage(), e);
            }
        }
        return result;
    }

}
