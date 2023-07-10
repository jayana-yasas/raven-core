package com.example.demo.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'"
    )
    Timestamp responseTimestamp;
    private String message;
    private ResponseStatus status;
    private Object data;
    private String lendoTraceId;

    public Response() {
    }

    public Response(final String message, final ResponseStatus status, final Timestamp responseTimestamp, final Object data, final String lendoTraceId) {
        this.message = message;
        this.status = status;
        this.responseTimestamp = responseTimestamp;
        this.data = data;
        this.lendoTraceId = lendoTraceId;
    }

    public static ResponseBuilder builder() {
        return new ResponseBuilder();
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public ResponseStatus getStatus() {
        return this.status;
    }

    public void setStatus(final ResponseStatus status) {
        this.status = status;
    }

    public Timestamp getResponseTimestamp() {
        return this.responseTimestamp;
    }

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'"
    )
    public void setResponseTimestamp(final Timestamp responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(final Object data) {
        this.data = data;
    }

    public String getLendoTraceId() {
        return this.lendoTraceId;
    }

    public void setLendoTraceId(final String lendoTraceId) {
        this.lendoTraceId = lendoTraceId;
    }

    public static class ResponseBuilder {
        private String message;
        private ResponseStatus status;
        private Timestamp responseTimestamp;
        private Object data;
        private String lendoTraceId;

        ResponseBuilder() {
        }

        public ResponseBuilder message(final String message) {
            this.message = message;
            return this;
        }

        public ResponseBuilder status(final ResponseStatus status) {
            this.status = status;
            return this;
        }

        @JsonFormat(
                shape = JsonFormat.Shape.STRING,
                pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS'Z'"
        )
        public ResponseBuilder responseTimestamp(final Timestamp responseTimestamp) {
            this.responseTimestamp = responseTimestamp;
            return this;
        }

        public ResponseBuilder data(final Object data) {
            this.data = data;
            return this;
        }

        public ResponseBuilder lendoTraceId(final String lendoTraceId) {
            this.lendoTraceId = lendoTraceId;
            return this;
        }

        public Response build() {
            return new Response(this.message, this.status, this.responseTimestamp, this.data, this.lendoTraceId);
        }

        public String toString() {
            return "Response.ResponseBuilder(message=" + this.message + ", status=" + this.status + ", responseTimestamp=" + this.responseTimestamp + ", data=" + this.data + ", lendoTraceId=" + this.lendoTraceId + ")";
        }
    }
}

