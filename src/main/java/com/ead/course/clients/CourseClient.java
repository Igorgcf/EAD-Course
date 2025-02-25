package com.ead.course.clients;

import com.ead.course.dtos.ResponsePageDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.services.UtilsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService service;

    public Page<UserDTO> findAllUsersByCourse(UUID courseId, Pageable pageable){

        String url = service.createUrl(courseId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try{
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {};
            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            if(result.getBody() != null){
                log.debug("Response Number of Elements: {} ", result.getBody().getContent().size());
                return result.getBody();
            }
        }catch(HttpStatusCodeException e){
            log.error("Error request /users {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId );

        return Page.empty();
    }
}
