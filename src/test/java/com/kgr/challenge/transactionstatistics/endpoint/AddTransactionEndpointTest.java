package com.kgr.challenge.transactionstatistics.endpoint;

import com.kgr.challenge.transactionstatistics.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith(MockitoExtension.class)
class AddTransactionEndpointTest {

    MockMvc mockMvc;

    @Mock
    TransactionService transactionService;

    @BeforeEach
    void setup() {
        this.mockMvc = standaloneSetup(new AddTransactionEndpoint(transactionService)).build();
    }

    @Test
    void add_transaction_responses_with_202() throws Exception {

        mockMvc.perform(post("/sales")
                .param("sales_amount", "12.1") //
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isAccepted()) //
                .andExpect(content().string(""));
    }
}