package com.nextzakir.springbootrestapi.service;

import com.nextzakir.springbootrestapi.helper.Response;
import org.springframework.data.domain.Page;
import org.springframework.lang.Nullable;

import java.util.List;

public interface HelperService {

    Response getResponse(Page<?> contents, @Nullable List<?> resources, @Nullable Long totalElements);

}