package com.hazz.example;

import com.hazz.baselibs.rx.RxSchedulers;
import com.hazz.example.data.MachineRetryWithDelay;
import com.hazz.example.data.entity.Respond;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Auther:gq
 * @Desc:
 * @Date:2019/5/31
 */
public class RetryWhenTest {
    @Test
    public void testFun1(){
        Respond respond = new Respond();
        respond.setResult("0");
        respond.setReason("机器通信异常");
        Throwable throwable = new Throwable("自定义异常");

        Observable.just(respond)
                .retryWhen(new MachineRetryWithDelay())
                .subscribe(new Consumer<Respond>() {
            @Override
            public void accept(Respond respond) throws Exception {
                System.out.println("accept1:" + respond.toString());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("accept2:" + throwable.getMessage());
            }
        });

    }


}
