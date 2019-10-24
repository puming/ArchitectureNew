package com.common.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * @author pm
 * @date 2018/11/26
 * @email puming@zdsoft.cn
 */
public class ApiResponse<RequestType> {
    private static final String TAG = "ApiResponse";

    public static <RequestType> ApiErrorResponse<RequestType> create(Throwable error) {
        return new ApiErrorResponse<RequestType>(error.getMessage());
    }

    public static <RequestType> ApiResponse<RequestType> create(Response<RequestType> response) {
        if (response.isSuccessful()) {
            RequestType body = response.body();
            Log.d(TAG, "create: code="+response.code());
            if (body == null || response.code() == 204) {
                return new ApiEmptyResponse<>();
            } else {
                return new ApiSuccessResponse<>(body);
            }
        } else {
            Log.d(TAG, "create: error code="+response.code());
            StringBuilder errorMsg = new StringBuilder();
            try {
                ResponseBody responseBody = response.errorBody();
                if (responseBody == null) {
                    errorMsg.append("unknown error");
                    return new ApiErrorResponse<>(errorMsg.toString(),response.code());
                }
                String msg = responseBody.string();
                if (msg.isEmpty()) {
                    errorMsg.append(response.message());
                } else {
                    errorMsg.append(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ApiErrorResponse<>(errorMsg.toString(),response.code());
        }
    }

    public static class ApiErrorResponse<RequestType> extends ApiResponse<RequestType> {
        private String error;
        private int errorCode;

        public ApiErrorResponse(String error) {
           this(error,-1);
        }
        public ApiErrorResponse(String error, int code) {
            this.error = error;
            this.errorCode = code;
        }

        public String getError() {
            return error;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

    public static class ApiSuccessResponse<RequestType> extends ApiResponse<RequestType> {
        private RequestType body;

        public ApiSuccessResponse(RequestType body) {
            this.body = body;
        }

        public RequestType getBody() {
            return body;
        }
    }

    public static class ApiEmptyResponse<RequestType> extends ApiResponse<RequestType> {
    }
}
