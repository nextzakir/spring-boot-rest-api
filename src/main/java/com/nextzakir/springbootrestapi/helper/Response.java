package com.nextzakir.springbootrestapi.helper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;

public class Response {

    private List<?> contents;

    private HttpHeaders httpHeaders;

    private HttpStatus httpStatusCode;

    public Response() {
    }

    public Response(List<?> contents, HttpHeaders httpHeaders, HttpStatus httpStatusCode) {
        this.contents = contents;
        this.httpHeaders = httpHeaders;
        this.httpStatusCode = httpStatusCode;
    }

    public List<?> getContents() {
        return contents;
    }

    public void setContents(List<?> contents) {
        this.contents = contents;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public HttpStatus getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatus httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response that = (Response) o;
        return Objects.equals(contents, that.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    @Override
    public String toString() {
        return "Response{" +
                "contents=" + contents +
                ", httpHeaders=" + httpHeaders +
                ", httpStatus=" + httpStatusCode +
                '}';
    }

}