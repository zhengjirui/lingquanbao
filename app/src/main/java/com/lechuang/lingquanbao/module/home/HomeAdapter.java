package com.lechuang.lingquanbao.module.home;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.common.app.base.ComponentViewHolder;
import com.common.app.constants.Constant;
import com.lechuang.lingquanbao.R;
import com.lechuang.lingquanbao.bean.GetBean;
import com.lechuang.lingquanbao.bean.HomeBannerBean;
import com.lechuang.lingquanbao.bean.HomeGunDongTextBean;
import com.lechuang.lingquanbao.bean.HomeKindBean;
import com.lechuang.lingquanbao.bean.HomeProgramBean;

import java.util.List;


/**
 * @author: zhengjr
 * @since: 2018/7/11
 * @describe:
 */

public class HomeAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, ComponentViewHolder> {

    protected HomeAdapterLisenter mHomeAdapterLisenter;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeAdapter(List data, HomeAdapterLisenter homeAdapterLisenter) {
        super(data);
        this.mHomeAdapterLisenter = homeAdapterLisenter;
        addItemType(Constant.LY_0, R.layout.item_home_banner);
        addItemType(Constant.LY_1, R.layout.item_home_kinddata);
        addItemType(Constant.LY_2, R.layout.item_home_scroll_text);
        addItemType(Constant.LY_3, R.layout.item_home_program);
        addItemType(Constant.LY_4, R.layout.item_home_tablayout);
    }

    @Override
    protected void convert(ComponentViewHolder helper, MultiItemEntity item) {

        switch (helper.getItemViewType()) {
            case Constant.LY_0:
                HomeBannerBean homeBannerBean = (HomeBannerBean) item;
                if (mHomeAdapterLisenter != null)
                    mHomeAdapterLisenter.setBannerData(helper, homeBannerBean);
                break;
            case Constant.LY_1:
                HomeKindBean homeKindBean = (HomeKindBean) item;
                if (mHomeAdapterLisenter != null)
                    mHomeAdapterLisenter.setHomeKindData(helper, homeKindBean);
                break;
            case Constant.LY_2:
                HomeGunDongTextBean homeGunDongTextBean = (HomeGunDongTextBean) item;
                if (mHomeAdapterLisenter != null)
                    mHomeAdapterLisenter.setHomeScrollText(helper, homeGunDongTextBean);
                break;
            case Constant.LY_3:
                HomeProgramBean homeProgramBean = (HomeProgramBean) item;
                if (mHomeAdapterLisenter != null)
                    mHomeAdapterLisenter.setHomeProgram(helper, homeProgramBean);
                break;
            case Constant.LY_4:
                GetBean getBean = (GetBean) item;
                if (mHomeAdapterLisenter != null)
                    mHomeAdapterLisenter.setHomeTabData(helper, getBean);
                break;
        }
    }

    public interface HomeAdapterLisenter {

        /**
         * 设置首页轮播图数据
         *
         * @param helper
         * @param homeBannerBean
         */
        void setBannerData(ComponentViewHolder helper, HomeBannerBean homeBannerBean);

        /**
         * 设置首页轮播图数据
         *
         * @param helper
         * @param homeKindBean
         */
        void setHomeKindData(ComponentViewHolder helper, HomeKindBean homeKindBean);

        /**
         * 设置首页滚动文字数据
         *
         * @param helper
         * @param homeGunDongTextBean
         */
        void setHomeScrollText(ComponentViewHolder helper, HomeGunDongTextBean homeGunDongTextBean);

        /**
         * 设置首页4个栏目数据
         * @param helper
         * @param homeProgramBean
         */
        void setHomeProgram(ComponentViewHolder helper, HomeProgramBean homeProgramBean);

        /**
         * 设置首页的tablayout数据
         * @param helper
         * @param getBean
         */
        void setHomeTabData(ComponentViewHolder helper, GetBean getBean);

//        void setProductData(ComponentViewHolder helper,);
    }
}
