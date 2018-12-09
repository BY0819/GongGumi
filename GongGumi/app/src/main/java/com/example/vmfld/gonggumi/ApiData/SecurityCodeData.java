package com.example.vmfld.gonggumi.ApiData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SecurityCodeData {

        @SerializedName("result")
        @Expose
        public List<SecurityCodeResultData> result = null;

        /**
         * No args constructor for use in serialization
         *
         */
        public SecurityCodeData() {
        }

        /**
         *
         * @param result
         */
        public SecurityCodeData(List<SecurityCodeResultData> result) {
            super();
            this.result = result;
        }

        public List<SecurityCodeResultData> getResult() {
            return result;
        }

        public void setResult(List<SecurityCodeResultData> result) {
            this.result = result;
        }

    }
