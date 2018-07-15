package com.lechuang.lingquanbao.bean;

import java.util.List;

/**
 * @author yrj
 * @date   2017/10/2
 * @E-mail 1422947831@qq.com
 * @desc   首页最下栏目实体类
 */
public class HomeLastProgramBean{

    //商品集合
    public List<ListBean> productList;

    public static class ListBean {
        public String id;
        //图片
        public String imgs;
        //商品标题
        public String name;
        //价格
        public String price;
        //券后价
        public String preferentialPrice;
        //商品id,传给H5页面的参数
        public String alipayItemId;
       /* //转链接之后的链接
        public String tbPrivilegeUrl;*/
        public String alipayCouponId;
        // 1 淘宝 2天猫
        public String shopType;
        public String shareIntegral;
        // 赚金额
        public String zhuanMoney;
        // 销量
        public int nowNumber;
        // 券金额
        public String couponMoney;
        //分享图片列表
        public List<String> smallImages;
        //分享文案
        public String shareText;
    }

    public programaBean programa;

    //广告图
    public static class programaBean {
        public int dataId;
        public String id;
        public List<ListBean> indexBannerList;

        public static class ListBean {
            public String id;
            //图片
            public String img;
            //链接
            public String link;
            public int type;
            public int programaId;
        }
    }
    //}


}
