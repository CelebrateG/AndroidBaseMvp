package com.hazz.example;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

import com.hazz.example.data.entity.BaseResult;
import com.hazz.example.data.entity.ZkyEntity;
import com.hazz.example.data.repository.RetrofitUtils;
import org.junit.Test;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testZKY() {
        String path = "C:\\Users\\admin\\Pictures\\capture\\";
        File file1 = new File(path + "201905222025.png");
        File file2 = new File(path + "201905290934.png");
        File file3 = new File(path + "aaa.png");
//        File file4 = new File(path + "2019052909.png");
        List<ZkyEntity> list = new ArrayList<>();
        //使用zip合并
        Observable.zip(
                RetrofitUtils.getHttpService().detection(getPartMap(), getMultipartBody(file1)),
                RetrofitUtils.getHttpService().detection(getPartMap(), getMultipartBody(file2)),
                RetrofitUtils.getHttpService().detection(getPartMap(), getMultipartBody(file3)),
                (Function3<ZkyEntity, ZkyEntity, ZkyEntity, List<ZkyEntity>>) (zkyEntity, zkyEntity2, zkyEntity3) -> {
                    System.out.println(Thread.currentThread());
                    list.add(zkyEntity);
                    list.add(zkyEntity2);
                    list.add(zkyEntity3);
                    return list;
                }
        )
                //测试环境不能使用android主线程
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<ZkyEntity>, ObservableSource<BaseResult>>() {
                    @Override
                    public ObservableSource<BaseResult> apply(List<ZkyEntity> zkyEntities) throws Exception {
                        System.out.println(Thread.currentThread());
                        if (zkyEntities == null) {
                            System.out.println("zkyEntities is null");
                        } else {
                            System.out.println("zkyEntities Size:" + zkyEntities.size());
                            for (ZkyEntity zkyEntity : zkyEntities) {
                                System.out.println(zkyEntity.toString());
                            }
                        }
                        return Observable.error(new Throwable("this is error"));
                    }
                })
                .subscribe(new Consumer<BaseResult>() {
                    @Override
                    public void accept(BaseResult baseResult) throws Exception {
                        System.out.println(baseResult.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable.getMessage());
                        System.out.println(throwable.toString());
                    }
                });

    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("multipart/form-data"), descriptionString);
    }

    public Map<String, RequestBody> getPartMap() {
        Map<String, RequestBody> partMap = new HashMap<>();
        partMap.put("api_key", createPartFromString("1767AF0F941D85D99858D63CEEDF9413"));
        partMap.put("secret_key", createPartFromString("53E8D5140169FFB07B15CB8CF0206A52"));
        partMap.put("roi", createPartFromString(""));
        return partMap;
    }

    public MultipartBody.Part getMultipartBody(File file) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
        return MultipartBody.Part.createFormData("image_file", file.getName(), requestFile);
    }

    @Test
    public void testCase() {
//        int a = 2;
//        switch (a){
//            case 1:
//            case 2:
//            case 3:
//                System.out.println(a);
//                break;
//        }
        long progress = 20734903;
        long total = 20734989;
        double progressPercent = Double.valueOf(progress) / total * 100;

        System.out.println((int) progressPercent);
    }

    @Test
    public void testCount() {
        int[] array = new int[]{1, 3, 1, 3,2,4};
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int a : array) {
            map.put(a, map.get(a) == null ? 1 : map.get(a) + 1);
        }
        StringBuffer buffer = new StringBuffer();
        for (int key : map.keySet()) {
            buffer.append(key + ",");
            System.out.println(key + ":" + map.get(key));
        }
        System.out.println(buffer.substring(0,buffer.length() - 1));


    }

}