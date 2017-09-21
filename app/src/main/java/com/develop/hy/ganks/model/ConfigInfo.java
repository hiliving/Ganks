package com.develop.hy.ganks.model;

/**
 * Created by HY on 2017/5/8.
 */

public class ConfigInfo {

    /**
     * msg :
     * data : {"description":"描述","fileType":2,"fileUrl":"http://10.10.6.63:8080/tv/aaaa.mp4"}
     * code : 0
     */

    private String msg;
    private DataBean data;
    private int code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class DataBean {
        /**
         * description : 描述
         * fileType : 2
         * fileUrl : http://10.10.6.63:8080/tv/aaaa.mp4
         */

        private String description;
        private int fileType;
        private String fileUrl;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getFileType() {
            return fileType;
        }

        public void setFileType(int fileType) {
            this.fileType = fileType;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }
    }
}
