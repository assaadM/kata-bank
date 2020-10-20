package com.kata.bdd;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.Application;
import com.kata.model.Account;
import com.kata.model.OperationType;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = Application.class)
public class AccountBDDCucumberTest extends SpringBootBaseIntegrationTest {

    @Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	private String idAccount = "";
	private double amount = 0;
	private double initBalance = 200;
	private Account account;

	@Given("^As a bank client \"([^\"]*)\"$")
	public void initiateAccount(String idAccount) throws JsonProcessingException, Exception {
        this.idAccount = idAccount;
        createAccount();
        deposit(initBalance);
	}

	@When("^I want to make a deposit of \"([^\"]*)\" in my account$")
	public void deposit(double amount) throws Exception {
		this.amount = amount;
		this.mockMvc.perform(put("/v1/accounts/"+idAccount+"/deposit/"+amount)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@When("^I want to make a withdrawal of \"([^\"]*)\" from my account$")
	public void withdrawal(double amount) throws Exception {
		this.amount = amount;
		this.mockMvc.perform(put("/v1/accounts/"+idAccount+"/withdrawal/"+amount)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk()).andReturn();
	}

	@When("^I want to see the history (operation, date, amount, balance) of my operations$")
	public void history() throws Exception {
		this.account = getAccount(this.idAccount);
	}

	@Then("^the deposit is done$")
	public void validateDeposit() throws Exception {
		Account account = getAccount(idAccount);
		assertEquals(this.initBalance +this.amount, account.getBalance(),0);
		assertEquals(2, account.getOperations().size());
		assertEquals(OperationType.DEPOSIT, account.getOperations().get(1).getType());
		assertEquals(amount, account.getOperations().get(1).getAmount(),0);

	}

	@Then("^I can see history$")
	public void checkHistory() throws Exception {
		Account account = getAccount(idAccount);
		assertEquals(this.initBalance, account.getBalance(),0);
		assertEquals(1, account.getOperations().size());
		assertEquals(OperationType.DEPOSIT, account.getOperations().get(0).getType());
		assertEquals(initBalance, account.getOperations().get(0).getAmount(),0);

	}

    private Account getAccount(String idAccount) throws Exception {
        MvcResult result = this.mockMvc.perform(get("/v1/accounts/"+idAccount).contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, Account.class);
    }

    @Then("^the withdrawal is done$")
	public void validateWithdrawal() throws Exception {
		Account account = getAccount(idAccount);
		assertEquals(this.initBalance - this.amount, account.getBalance(),0);
		assertEquals(2, account.getOperations().size());
		assertEquals(OperationType.WITH_DRAWL, account.getOperations().get(1).getType());
		assertEquals(this.initBalance - this.amount, account.getOperations().get(1).getBalance(),0);
		assertEquals(this.amount, account.getOperations().get(1).getAmount(),0);

	}
    private Account createAccount() throws JsonProcessingException, Exception {
        MvcResult result = this.mockMvc
                .perform(post("/v1/accounts/"+idAccount).contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        return objectMapper.readValue(content, Account.class);
    }

}