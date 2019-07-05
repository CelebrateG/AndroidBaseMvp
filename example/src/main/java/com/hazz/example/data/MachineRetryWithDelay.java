package com.hazz.example.data;


import android.util.Log;

import com.hazz.example.data.entity.Respond;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Auther:gq
 * @Desc:机器通信重试类
 * @Date:2019/5/22
 */
public class MachineRetryWithDelay implements Function<Observable<? extends Throwable>, Observable<?>> {
    private int maxRetries = 5;
    private long retryDelayMillis = 2000;
    private int currentRetries = 1;

    public MachineRetryWithDelay() {
    }

    public MachineRetryWithDelay(int maxRetries, long retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }


    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        Log.d("MachineRetryWhen", "apply");
        return observable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                                      @Override
                                      public ObservableSource<?> apply(Throwable throwable) throws Exception {
                                          Log.d("MachineRetryWhen", throwable.toString() + "times:" + currentRetries);
                                          if (++currentRetries <= maxRetries) {
                                              Log.d("MachineRetryWhen", "times:" + currentRetries);
                                              return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                                          }
                                          return Observable.error(new Throwable(throwable.getMessage()));
                                      }
                                  }
        );
    }

/*    @Override
    public Observable<?> apply(Observable<Respond> respondObservable) throws Exception {
        Log.d("MachineRetryWhen", "apply");
        return respondObservable.flatMap(new Function<Respond, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Respond respond) throws Exception {
                Log.d("MachineRetryWhen", respond.toString() + "times:" + currentRetries);
                if (!respond.isSuccess() && ++currentRetries <= maxRetries) {
                    Log.d("MachineRetryWhen", "times:" + currentRetries);
                    return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                }
                return Observable.error(new Throwable(respond.getReason()));
            }
        });
    }*/
}
