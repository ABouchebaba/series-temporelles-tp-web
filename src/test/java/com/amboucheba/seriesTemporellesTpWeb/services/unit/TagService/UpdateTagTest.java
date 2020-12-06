package com.amboucheba.seriesTemporellesTpWeb.services.unit.TagService;

import com.amboucheba.seriesTemporellesTpWeb.exceptions.NotFoundException;
import com.amboucheba.seriesTemporellesTpWeb.models.Event;
import com.amboucheba.seriesTemporellesTpWeb.models.Tag;
import com.amboucheba.seriesTemporellesTpWeb.repositories.EventRepository;
import com.amboucheba.seriesTemporellesTpWeb.repositories.TagRepository;
import com.amboucheba.seriesTemporellesTpWeb.services.EventService;
import com.amboucheba.seriesTemporellesTpWeb.services.SerieTemporelleService;
import com.amboucheba.seriesTemporellesTpWeb.services.TagService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
@WebMvcTest(TagService.class)
public class UpdateTagTest {

    @MockBean
    private TagRepository tagRepository;

    @MockBean
    private EventService eventService;

    @Autowired
    TagService tagService;

    @TestConfiguration
    static class Config{

        @Bean
        public TagService getService(){
            return new TagService();
        }
    }

    @Test
    public void eventExists__returnUpdatedEvent() {
        Tag toUpdate = new Tag(1L, "tag", null);
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.of(toUpdate));

        Tag newTag = new Tag(1L, "newtag", null);
        Mockito.when(tagRepository.save(newTag)).thenReturn(newTag);

        Tag updatedTag = tagService.updateTag(1L, newTag);

        assertEquals(newTag, updatedTag);
    }

    @Test
    public void tagDoesNotExist__ThrowNotFoundException(){

        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            tagService.updateTag(1L,new Tag());
        });
    }

}