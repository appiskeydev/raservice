package com.appiskey.raservice.controller;

import com.appiskey.raservice.model.Skill;
import com.appiskey.raservice.service.SkillService;
import com.appiskey.raservice.util.Datagen;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(SkillController.class)
public class SkillControllerIntegrationTest {

    @Value("${app.url}")
    private String appUrl;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private SkillService service;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void whenPostItem_thenCreateItem() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        given(service.insert(Mockito.any())).willReturn(item1);
        mockMvc.perform(post(appUrl + "/skill").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(item1))).andExpect(status().isCreated());
        verify(service, VerificationModeFactory.times(1)).insert(Mockito.any());
        reset(service);
    }

    @Test
    public void whenPutItem_thenUpdateItem() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        given(service.update(Mockito.any())).willReturn(item1);
        mockMvc.perform(put(appUrl + "/skill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(item1)))
                .andExpect(status().isOk());
        verify(service, VerificationModeFactory.times(1)).update(Mockito.any());
        reset(service);
    }

    @Test
    public void whenDeleteItem_thenDeleteItem() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        given(service.delete(Mockito.any())).willReturn(item1);
        mockMvc.perform(put(appUrl + "/skill")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(item1)))
                .andExpect(status().isOk());
        verify(service, VerificationModeFactory.times(1)).update(Mockito.any());
        reset(service);
    }

    @Test
    public void givenItems_whenGotItems_thenReturnJsonArray() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        Skill item2 = Datagen.generateSkill("item2");
        Skill item3 = Datagen.generateSkill("item3");

        List<Skill> allItems = Arrays.asList(item1, item2, item3);
        given(service.getAll()).willReturn(allItems);

        mockMvc.perform(get(appUrl + "/skill").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(item1.getName())))
                .andExpect(jsonPath("$[1].name", is(item2.getName())))
                .andExpect(jsonPath("$[2].name", is(item3.getName())));

        verify(service, VerificationModeFactory.times(1)).getAll();
        reset(service);
    }

    @Test
    public void givenItems_whenSearchItems_thenReturnJsonArray() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        Skill item2 = Datagen.generateSkill("item2");
        Skill item3 = Datagen.generateSkill("item32");

        Map<String, String> keywordStrings = new HashMap<>();
        keywordStrings.put("keyword", "2");
        List<Skill> allItems = Arrays.asList(item2, item3);
        given(service.searchBySkillName(Mockito.anyString())).willReturn(allItems);

        mockMvc.perform(post(appUrl + "/skill/search/")
                .content(JsonUtil.asJsonString(keywordStrings))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(item2.getName())))
                .andExpect(jsonPath("$[1].name", is(item3.getName())));

        verify(service, VerificationModeFactory.times(1)).searchBySkillName(Mockito.anyString());
        reset(service);
    }

    @Test
    public void givenItem_whenGotItemById_thenReturnJsonObject() throws Exception {
        Skill item1 = Datagen.generateSkill("item1");
        UUID uuid = Datagen.getUuId();
        item1.setId(uuid);
        given(service.findById(Mockito.any())).willReturn(item1);

        mockMvc.perform(get(appUrl + "/skill/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is(item1.getName())));
        verify(service, VerificationModeFactory.times(1)).findById(Mockito.any());
        reset(service);
    }
}
