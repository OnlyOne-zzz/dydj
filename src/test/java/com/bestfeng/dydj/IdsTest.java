package com.bestfeng.dydj;

import com.bestfeng.dydj.constants.Constants;
import com.bestfeng.dydj.utils.IDUtils;
import org.junit.jupiter.api.Test;

public class IdsTest {

    @Test
    public void getOrderNo(){
        System.out.println(IDUtils.getId(Constants.ORDER_NO_PRE));
    }
}
