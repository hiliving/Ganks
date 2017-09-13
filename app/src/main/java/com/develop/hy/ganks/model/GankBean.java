package com.develop.hy.ganks.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HY on 2017/9/12.
 */

public class GankBean implements Serializable{

    /**
     * error : false
     * results : [{"_id":"59b667cf421aa9118887ac12","createdAt":"2017-09-11T18:39:11.631Z","desc":"用少量Rxjava代码，为retrofit添加退避重试功能","publishedAt":"2017-09-12T08:15:08.26Z","source":"web","type":"Android","url":"http://www.jianshu.com/p/fca90d0da2b5","used":true,"who":"小鄧子"},{"_id":"59b673ef421aa9118887ac13","createdAt":"2017-09-11T19:30:55.50Z","desc":"Android 端基于 OpenCV 的边框识别功能","publishedAt":"2017-09-12T08:15:08.26Z","source":"web","type":"Android","url":"https://pqpo.me/2017/09/11/opencv-border-recognition/","used":true,"who":"Linmin Qiu"},{"_id":"59b69738421aa9118c8262ad","createdAt":"2017-09-11T22:01:28.352Z","desc":"用ContentProvider初始化你的库","images":["http://img.gank.io/3b0b193d-6abf-4714-9d5a-5508404666f4"],"publishedAt":"2017-09-12T08:15:08.26Z","source":"web","type":"Android","url":"http://zjutkz.net/2017/09/11/%E4%B8%80%E4%B8%AA%E5%B0%8F%E6%8A%80%E5%B7%A7%E2%80%94%E2%80%94%E4%BD%BF%E7%94%A8ContentProvider%E5%88%9D%E5%A7%8B%E5%8C%96%E4%BD%A0%E7%9A%84Library/","used":true,"who":null}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 59b667cf421aa9118887ac12
         * createdAt : 2017-09-11T18:39:11.631Z
         * desc : 用少量Rxjava代码，为retrofit添加退避重试功能
         * publishedAt : 2017-09-12T08:15:08.26Z
         * source : web
         * type : Android
         * url : http://www.jianshu.com/p/fca90d0da2b5
         * used : true
         * who : 小鄧子
         * images : ["http://img.gank.io/3b0b193d-6abf-4714-9d5a-5508404666f4"]
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;
        private List<String> images;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
