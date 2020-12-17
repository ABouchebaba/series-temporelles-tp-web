package com.amboucheba.seriesTemporellesTpWeb.services.unit.EventService;

import com.amboucheba.seriesTemporellesTpWeb.exceptions.NotFoundException;
import com.amboucheba.seriesTemporellesTpWeb.models.Event;
import com.amboucheba.seriesTemporellesTpWeb.repositories.EventRepository;
import com.amboucheba.seriesTemporellesTpWeb.services.EventService;
import com.amboucheba.seriesTemporellesTpWeb.services.SerieTemporelleService;
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
@WebMvcTest(EventService.class)
public class UpdateEventTest {

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    SerieTemporelleService serieTemporelleService;

    @Autowired
    private EventService eventService;

    @TestConfiguration
    static class Config{

        @Bean
        public EventService getService(){
            return new EventService();
        }
    }


//    @Test
//    public void eventExists__returnUpdatedEvent() {
//        Event toUpdate = new Event(1L, new Date(), 5.0f,"comment", null);
//        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.of(toUpdate));
//
//        Event newEvent = new Event(1L, new Date(), 6.0f,"new comment", null);
//        Mockito.when(eventRepository.save(newEvent)).thenReturn(newEvent);
//
//        Event updatedST = eventService.updateEvent(1L, newEvent);
//
//        assertEquals(newEvent, updatedST);
//    }
//
//    @Test
//    public void stIdDoesNotExist__ThrowNotFoundException(){
//
//        Mockito.when(eventRepository.findById(1L)).thenReturn(Optional.empty());
//
//        assertThrows(NotFoundException.class, () -> {
//            eventService.updateEvent(1L,new Event());
//        });
//    }

}
