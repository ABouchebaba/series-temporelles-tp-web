package com.amboucheba.seriesTemporellesTpWeb.controllers.integration.SerieTemporelleController;

import com.amboucheba.seriesTemporellesTpWeb.SeriesTemporellesTpWebApplication;
import com.amboucheba.seriesTemporellesTpWeb.exceptions.ApiException;
import com.amboucheba.seriesTemporellesTpWeb.models.ModelLists.SerieTemplorelleList;
import com.amboucheba.seriesTemporellesTpWeb.models.SerieTemporelle;
import com.amboucheba.seriesTemporellesTpWeb.models.User;
import com.amboucheba.seriesTemporellesTpWeb.repositories.SerieTemporelleRepository;
import com.amboucheba.seriesTemporellesTpWeb.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SeriesTemporellesTpWebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SqlGroup({
        @Sql(scripts = { "classpath:schema.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(scripts = { "classpath:reset.sql" }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
public class GetAllOfOwnerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private SerieTemporelleRepository serieTemporelleRepository;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepository;

    @Test
    void userExists__returnStsOfUser() throws Exception {
        User user = new User("user", "pass");
        user = userRepository.save(user);
        SerieTemporelle st1 = new SerieTemporelle("t1", "d1", user);
        SerieTemporelle st2 = new SerieTemporelle("t2", "d2", user);
        serieTemporelleRepository.save(st1);
        serieTemporelleRepository.save(st2);

        String uri = "http://localhost:" + port + "/users/" + user.getId() +"/seriesTemporelles";
        ResponseEntity<SerieTemplorelleList> responseEntity = testRestTemplate.getForEntity(uri, SerieTemplorelleList.class);
        SerieTemplorelleList stList = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, stList.getCount() );
    }

    @Test
    void userDoesNotExist__throwNotFoundException(){

        String uri = "http://localhost:" + port + "/users/1/seriesTemporelles";
        ResponseEntity<ApiException> responseEntity = testRestTemplate.getForEntity(uri, ApiException.class);
        ApiException e = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}
