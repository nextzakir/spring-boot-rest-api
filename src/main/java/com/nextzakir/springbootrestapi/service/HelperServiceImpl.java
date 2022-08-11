package com.nextzakir.springbootrestapi.service;

import com.nextzakir.springbootrestapi.helper.Helper;
import com.nextzakir.springbootrestapi.helper.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class HelperServiceImpl implements HelperService {

    @Override
    public Response getResponse(Page<?> contents, @Nullable List<?> resources, @Nullable Long totalElements) {

        if (contents.getContent().isEmpty()) {
            return new Response(null, null, HttpStatus.NO_CONTENT);
        } else {
            long totalContents = totalElements != null ? totalElements : contents.getTotalElements();
            int numberOfPageContents = contents.getNumberOfElements();

            HttpHeaders headers = new HttpHeaders();
            headers.add("X-Total-Count", String.valueOf(totalContents));

            if (numberOfPageContents < totalContents) {
                headers.add("first", Helper.buildPageUri(PageRequest.of(0, contents.getSize())));
                headers.add("last", Helper.buildPageUri(PageRequest.of(contents.getTotalPages() - 1, contents.getSize())));

                if (contents.hasNext()) {
                    headers.add("next", Helper.buildPageUri(contents.nextPageable()));
                }

                if (contents.hasPrevious()) {
                    headers.add("prev", Helper.buildPageUri(contents.previousPageable()));
                }

                return new Response(resources != null ? resources : contents.getContent(), headers, HttpStatus.PARTIAL_CONTENT);
            } else {
                return new Response(resources != null ? resources : contents.getContent(), headers, HttpStatus.OK);
            }
        }

    }

}