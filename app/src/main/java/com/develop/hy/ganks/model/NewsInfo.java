package com.develop.hy.ganks.model;

import java.util.List;

/**
 * Created by Helloworld on 2017/9/24.
 */

public class NewsInfo {

    /**
     * code : 200
     * msg : ok
     * newslist : [{"ctime":"2015-07-17","title":"那个抱走王明涵的，你上微信吗？看完这个你会心软吗？","description":"中国传统文化","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-667708.jpg/640","url":"http://mp.weixin.qq.com"},{"ctime":"2015-06-12","title":"深悦地产风云榜丨房地产微信公众号一周榜单","description":"深悦会","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-530408.jpg/640","url":"http://mp.weixin.qq.com/s"},{"ctime":"2015-06-14","title":"一条微信向全世界宣告，这就是惠州！","description":"西子湖畔","picUrl":"http://zxpic.gtimg.com/infonew/0/wechat_pics_-536516.jpg/640","url":"http://mp.weixin.qq.com/"}]
     */

    private int code;
    private String msg;
    private List<NewslistBean> newslist;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<NewslistBean> getNewslist() {
        return newslist;
    }

    public void setNewslist(List<NewslistBean> newslist) {
        this.newslist = newslist;
    }

    public static class NewslistBean {
        /**
         * ctime : 2015-07-17
         * title : 那个抱走王明涵的，你上微信吗？看完这个你会心软吗？
         * description : 中国传统文化
         * picUrl : http://zxpic.gtimg.com/infonew/0/wechat_pics_-667708.jpg/640
         * url : http://mp.weixin.qq.com
         */

        private String ctime;
        private String title;
        private String description;
        private String picUrl;
        private String url;

        public String getCtime() {
            return ctime;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
