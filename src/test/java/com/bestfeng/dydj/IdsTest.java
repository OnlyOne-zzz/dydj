package com.bestfeng.dydj;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.utils.IDUtils;
import com.bestfeng.dydj.utils.httpclient.HttpClientUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class IdsTest {

    @Test
    public void getOrderNo()throws Exception{
        String accessToken="37_quE4Ykld_TK8D7De_aw5iRyIYGQpeAWwJQ1-9W9U1xA2u-h-NjflyA9j6N8zFjNaTjSZlv-jhQlyPyq4IitaIZd2UK_JuLvBLuQ4Q-zpOP2UMFZP9eo0u7DmuxRkz5CX_VzjUKQsqJQfLFWoPEBdAEAZOL";
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=";
        Map<String,String> parm = new HashMap<>();
        parm.put("touser","o6S4e5JS7WnTBe2NfoFPoIt9fAyk");
        parm.put("template_id","O7qm04PozaNS375KaRZxPTe3GB8wNM_H8VH0_mQnZHc");
        String res =HttpClientUtil.doPost(url.concat(accessToken),parm);
        System.out.println(res);
    }
}
