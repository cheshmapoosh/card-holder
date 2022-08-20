package ir.co.isc.assignment.cardholder.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.co.isc.assignment.cardholder.model.constant.SortDirection;
import ir.co.isc.assignment.cardholder.model.constant.SortField;
import ir.co.isc.assignment.cardholder.model.dto.CardDto;
import ir.co.isc.assignment.cardholder.model.dto.CardSearchDto;
import ir.co.isc.assignment.cardholder.model.dto.PageDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
        //need this in Spring Boot test
class CardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "ADMIN")
    void findAllByCriteria_byAdminRole_expectedSuccess() throws Exception {

        String body = objectMapper.writeValueAsString(CardSearchDto.builder()
                .page(0)
                .size(5)
                .build());
        MvcResult result = mvc.perform(
                get("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        final TypeReference<PageDto<CardDto>> token = new TypeReference<>() {
        };
        assertThatNoException().isThrownBy(() -> objectMapper.readValue(result.getResponse().getContentAsString(), token));

        PageDto<CardDto> page = objectMapper.readValue(result.getResponse().getContentAsString(), token);

        assertThatObject(page).isNotNull();

        assertThatObject(page.getContent()).isNotNull();

        assertThat(page.getContent().size()).isBetween(0, 5);
    }

    @Test
    @WithMockUser(roles = {})
    void findAllByCriteria_expectedForbidden() throws Exception {
        String body = objectMapper.writeValueAsString(CardSearchDto.builder()
                .page(0)
                .size(5)
                .build());
        mvc.perform(
                get("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    void findAllByCriteria_expectedNotValidNationalCode() throws Exception {
        String body = objectMapper.writeValueAsString(CardSearchDto.builder()
                .holderNationalCode("123456")
                .page(0)
                .size(5)
                .build());
        mvc.perform(
                get("/api/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }


}