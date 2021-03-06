package com.amboucheba.seriesTemporellesTpWeb.services.unit.TagService;

import com.amboucheba.seriesTemporellesTpWeb.exceptions.NotFoundException;
import com.amboucheba.seriesTemporellesTpWeb.models.Event;
import com.amboucheba.seriesTemporellesTpWeb.models.SerieTemporelle;
import com.amboucheba.seriesTemporellesTpWeb.models.Tag;
import com.amboucheba.seriesTemporellesTpWeb.models.User;
import com.amboucheba.seriesTemporellesTpWeb.repositories.EventRepository;
import com.amboucheba.seriesTemporellesTpWeb.repositories.TagRepository;
import com.amboucheba.seriesTemporellesTpWeb.repositories.UserRepository;
import com.amboucheba.seriesTemporellesTpWeb.services.*;
import com.amboucheba.seriesTemporellesTpWeb.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TagService.class)
public class AddTagToEventTest {


    @MockBean
    private TagRepository tagRepository;

    @MockBean
    EventService eventService;

    @MockBean
    UserService userService;

    @MockBean
    PartageService partageService;

    @Autowired
    TagService tagService;

    @TestConfiguration
    static class Config{

        @MockBean
        public UserRepository userRepository;

        @Bean
        public JwtUtil getUtil(){
            return new JwtUtil();
        }

        @Bean
        public AuthService getAuth(){
            return new AuthService();
        }

        @Bean
        public TagService getService(){
            return new TagService();
        }
    }

    @Test
    public void eventExists__addTagToEvent(){

        User user = new User(1L, "user", "pass");
        SerieTemporelle st = new SerieTemporelle(1L,"event", "pass", user);
        Event event = new Event(1L, new Date(), 5.0f,"comment", st);

        Tag toSave = new Tag( "tag", event);
        Tag saved = new Tag(1L, "tag", event);

        // Suppose user is authenticated
        Mockito.when(userService.initiatorIsOwner(1L, 1L)).thenReturn(true);
        Mockito.when(eventService.find(1L)).thenReturn(event);
        Mockito.when(tagRepository.save(toSave)).thenReturn(saved);

        Tag returned = tagService.addTagToEvent(1L, toSave, 1L);

        assertEquals(saved, returned);
    }

    @Test
    public void eventDoesNotExist__ThrowNotFoundException(){

        Mockito.when(eventService.find(1L)).thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class, () -> {
            tagService.addTagToEvent(1L, new Tag(), 1L );
        });
    }

}
