package com.develop.hy.ganks.model;

/**
 * 版本信息
 */

// "version": "1.2.6",//版本
// "updateType": 1,//升级类型 0无需升级 1非强制升级 2强制升级
// "url":"http://domain/xxx.xxx",//下载链接
// "description":"1.更牛逼了\n2.更漂亮了",

public class VersionBean {

    /**
     * code : 0
     * data : {"description":"1.修改了网络异常提醒;\n2.修复了个人中心页的BUG;\n3.UI细节优化。","updateType":1,"url":"http://4k-images.oss-cn-beijing.aliyuncs.com/apps/test/net.cibntv.ott.sk_release_v2.1.3.apk ","version":"2.1.3"}
     * msg :
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * description : 1.修改了网络异常提醒;
         2.修复了个人中心页的BUG;
         3.UI细节优化。
         * updateType : 1
         * url : http://4k-images.oss-cn-beijing.aliyuncs.com/apps/test/net.cibntv.ott.sk_release_v2.1.3.apk
         * version : 2.1.3
         */

        private String description;
        private int updateType;
        private String url;
        private String version;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getUpdateType() {
            return updateType;
        }

        public void setUpdateType(int updateType) {
            this.updateType = updateType;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
