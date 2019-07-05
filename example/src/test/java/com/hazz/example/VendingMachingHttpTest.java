package com.hazz.example;

import com.hazz.baselibs.net.download.DownLoadManager;
import com.hazz.baselibs.net.download.ProgressCallBack;
import com.hazz.example.data.entity.BaseEntity;
import com.hazz.example.data.repository.RetrofitUtils;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import io.reactivex.functions.Consumer;

/**
 * @Auther:gq
 * @Desc:
 * @Date:2019/5/29
 */
public class VendingMachingHttpTest {
    @Test
    public void reportError() {
        String cmd = "fault";
        String vmc_no = "4008000151";
        String fault_id = "1";
        String fault_desc = "水桶1中水不足";
        boolean is_set = true;
        boolean is_ok = false;
        String fault_type = "10";
        RetrofitUtils.getHttpService().reportError(cmd,vmc_no,getData(),fault_id,fault_desc,is_set,is_ok,fault_type).subscribe(new Consumer<BaseEntity>() {
            @Override
            public void accept(BaseEntity baseEntity) throws Exception {
                if (baseEntity != null) {
                    System.out.println(baseEntity.toString());
                }
            }
        });
    }

    public String getData(){
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        return df.format(new Date());
    }

    @Test
    public void testBuffer(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("123");
        System.out.println(buffer.toString().substring(0,buffer.length()-1));
    }

    @Test
    public void testDownLoad(){


    }

}
