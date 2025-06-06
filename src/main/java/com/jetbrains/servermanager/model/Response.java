package com.jetbrains.servermanager.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public class Response {
    private LocalDateTime timeStamp;
    private int statusCode;
    private HttpStatus status;
    private String reason;
    private String message;
    private String developerMessage;
    private Map<?, ?> data;

    // ✅ Private constructor for builder use
    private Response(Builder builder) {
        this.timeStamp = builder.timeStamp;
        this.statusCode = builder.statusCode;
        this.status = builder.status;
        this.reason = builder.reason;
        this.message = builder.message;
        this.developerMessage = builder.developerMessage;
        this.data = builder.data;
    }

    // ✅ Getters
    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getReason() {
        return reason;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public Map<?, ?> getData() {
        return data;
    }

    // ✅ Static Builder class
    public static class Builder {
        private LocalDateTime timeStamp;
        private int statusCode;
        private HttpStatus status;
        private String reason;
        private String message;
        private String developerMessage;
        private Map<?, ?> data;

        public Builder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder statusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder developerMessage(String developerMessage) {
            this.developerMessage = developerMessage;
            return this;
        }

        public Builder data(Map<?, ?> data) {
            this.data = data;
            return this;
        }

        public Response build() {
            return new Response(this);
        }
    }

    // ✅ Static factory method
    public static Builder builder() {
        return new Builder();
    }
}
