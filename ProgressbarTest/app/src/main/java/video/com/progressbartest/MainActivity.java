package video.com.progressbartest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends Activity {

    private ProgressBar pro;

    private ProgressBar bar;
    private GifImageView prince;
    private ImageView princess;
    private TextView tv;
    private Handler handler;
    private float cha = 0;
    private float distance;
    private ImageView imageView;

    String[] hint = new String[] {
            "登录十分钟绝世佳人以身相许",
            "上朝批奏折处理政务，你是否真的能治理好国家？",
            "开局只有四名大臣，如何才能一步登天？",
            "招募心仪大臣，封官加爵，\n体会名臣辅佐，大权在握的快感！",
            "生孩子培养后还能结婚，孙子满地跑。",
            "后宫翻牌带上耳机，真实效果更佳~",
            "记得偷偷的躲在被窝里玩哦！",
            "游客账号记得绑定账号以免丢失",
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pro = (ProgressBar)findViewById(R.id.my_progress);

        imageView = (ImageView)findViewById(R.id.ivGif);
        Glide.with(MainActivity.this).load(R.drawable.gi).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);


        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = pro.getProgress();
                while ((progress + 1) <= 100) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pro.setProgress(progress + 1);
                    progress = progress + 1;
                }
            }
        }).start();


        WindowManager wm = getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        System.out.println("GameLoading width: " + width);
        distance = width * 79/ 100 / 100;

        // 进度条
        bar = (ProgressBar)findViewById(R.id.loading_progress);
        // 行走的小人
        prince = (GifImageView)findViewById(R.id.prince);
        // 提示文字
        tv = (TextView)findViewById(R.id.hint);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what % 15 == 0){
                    Random rand = new Random();
                    int i = rand.nextInt(8);
                    tv.setText(hint[i]);
                }
                cha = cha + distance;
                prince.setX(cha);
                imageView.setX(cha);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                int progress = bar.getProgress();
                while ((progress + 1) <= 100) {
                    try {
                        Thread.sleep(130);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.what = progress;
                    handler.sendMessage(msg);
                    bar.setProgress(progress + 1);
//                    bar.setSecondaryProgress(progress + 3);
                    progress = progress + 1;
                    System.out.println("GameLoading progress: " + progress);
                }
            }
        }).start();

    }


}
