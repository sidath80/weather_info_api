package com.sample.weather.model;

public class ApiError {

    private int errorCode;
    private String errorMessage;

    private ApiError() {
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static class Builder {
        private int errorCode;
        private String errorMessage;

        public Builder errorCode(int errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder errorMessage(String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ApiError build() {
            ApiError apiError = new ApiError();
            apiError.errorCode = this.errorCode;
            apiError.errorMessage = this.errorMessage;
            return apiError;
        }
    }
}

