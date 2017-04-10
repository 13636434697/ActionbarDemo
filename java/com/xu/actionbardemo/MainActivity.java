package com.xu.actionbardemo;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


/*
* 关于ActionBar最右这三个点按钮：
*
*正式的名称应该是 overflow menu，在android4.2.2以上的设备是默认出现的，但是android4.1.2的设备不显示，
* 似乎android也没有提供显示的API，所以网上搜索到这个强制显示的方案（亲测可用）。
*
* 关键代码如下：

private void getOverflowMenu() {
		ViewConfiguration viewConfig = ViewConfiguration.get(this);

		try {
			Field overflowMenuField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(null != overflowMenuField){
				overflowMenuField.setAccessible(true);
				overflowMenuField.setBoolean(viewConfig, false);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	在onCreate()的方法中直接调用就可以：

protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_page);

		//force to display overflow menu in action bar
		getOverflowMenu();
	}

原作者有补充到即时应用于android4.4以上的设备应该也不会有问题：因为即使得到的overflowMenuField为null，代码也做了判空处理，所以没什么影响。

我实际在调用时加入版本兼容条件，如下：

//force to display overflow menu in action bar when sdk version less than 21
		if((Build.VERSION.SDK_INT >= 10) & (Build.VERSION.SDK_INT < 21)) {
			getOverflowMenu();
		}

没有发现问题。
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionbar();
    }

    /*
    * 特别注意，这里需要按下menu菜单键才能显示出菜单,在4.2以下的版本
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // 加载actionbar菜单布局
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //有空指针的情况
    //在左上角做一些修改
    private void initActionbar() {
        //ActionBar在11版本才能用的
        ActionBar actionBar = getActionBar();

        actionBar.setTitle("哈哈哈哈");// 设置标题
        actionBar.setLogo(R.mipmap.ic_launcher);// 设置logo

        actionBar.setHomeButtonEnabled(true);// logo是否可以点击
        actionBar.setDisplayShowHomeEnabled(false);// 隐藏logo

        actionBar.setDisplayHomeAsUpEnabled(true);// 显示返回键
        //下面是另一种写法
        //显示左上角的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //隐藏左上角的图标
        getSupportActionBar().setDisplayShowHomeEnabled(false);

    }


    // actionbar点击事件处理
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 左上角home处点击之后的响应
                Toast.makeText(this, "返回啦", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_settings:
                Toast.makeText(this, "设置点击啦", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
