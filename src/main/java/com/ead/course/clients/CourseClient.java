package com.ead.course.clients;

import com.ead.course.dtos.CourseDTO;
import com.ead.course.dtos.ResponsePageDTO;
import com.ead.course.dtos.UserDTO;
import com.ead.course.models.Course;
import com.ead.course.services.UtilsService;
import com.ead.course.services.exceptions.ResourceNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Value("${ead.api.url.authuser}")
    String REQUEST_URL_AUTHUSER;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UtilsService service;

    public Page<UserDTO> findAllUsersByCourse(UUID courseId, Pageable pageable) {

        String url = service.createUrl(courseId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ParameterizedTypeReference<ResponsePageDTO<UserDTO>> responseType = new ParameterizedTypeReference<ResponsePageDTO<UserDTO>>() {
            };
            ResponseEntity<ResponsePageDTO<UserDTO>> result = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            if (result.getBody() != null) {
                log.debug("Response Number of Elements: {} ", result.getBody().getContent().size());
                return result.getBody();
            }
        } catch (HttpStatusCodeException e) {
            log.error("Error request /users {} ", e);
        }
        log.info("Ending request /users courseId {} ", courseId);

        return Page.empty();
    }

    public UserDTO findById(UUID userId) {

        String url = service.createUrl(userId);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            ResponseEntity<UserDTO> result = restTemplate.exchange(url, HttpMethod.GET, null, UserDTO.class);

            if (result.getBody() != null) {
                log.debug("Response: {} ", result.getBody());
                return result.getBody();
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("User Id or Instructor Id not found: " + userId);
        }

        return null;
    }


    public void postSubscriptionUserInCourse(Course entity, UUID userId){

        String url = REQUEST_URL_AUTHUSER + "/users/" + userId + "/courses/subscription";
        CourseDTO dto = new CourseDTO();
        dto = new CourseDTO(entity);
        restTemplate.postForObject(url, dto, String.class);
    }
}
