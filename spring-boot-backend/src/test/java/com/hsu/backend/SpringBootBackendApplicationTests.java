package com.hsu.backend;

import com.hsu.backend.model.Item;
import com.hsu.backend.repository.ReferenceDataRepository;
import com.hsu.backend.service.ReferenceDataService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(properties = "application-test.properties")
@WebAppConfiguration
class SpringBootBackendApplicationTests {

	@Autowired
	private ReferenceDataRepository referenceDataRepository;

	@Autowired
	private WebApplicationContext webContext;

	private MockMvc mockMvc;

	@Before
	public void setupMockMvc() {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.build();
	}

	@Test
	public void swaggerPage() throws  Exception {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.build();
		mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui.html"))
				.andExpect(status().isOk());
	}

	@Test
	public void getItemsFirstPageOfSizeTwo() throws  Exception {
		Set<Item> nine1One2 = new HashSet<>();
		nine1One2 = new HashSet<>();
		nine1One2.add(new Item(1L, "1111111112"));
		nine1One2.add(new Item(2L, "111111111a"));
		nine1One2.add(new Item(3L, "111111111b"));
		nine1One2.add(new Item(4L, "111111111c"));
		this.referenceDataRepository.saveAll(nine1One2);
		this.referenceDataRepository.flush();
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.build();
		mockMvc.perform(get("/reference/v1/getItems/size/2/page/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content[*].text", containsInAnyOrder(
						"111111111b",
						"111111111c"
				)));
	}

	@Test
	public void generateItems() throws  Exception {
		this.mockMvc = MockMvcBuilders
				.webAppContextSetup(webContext)
				.build();
		mockMvc.perform(post("/reference/v1/generate")
				.content("1111111112"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(4)))
				.andExpect(jsonPath("$[*].text", containsInAnyOrder(
						"1111111112",
						"111111111a",
						"111111111b",
						"111111111c"
				)));

	}

	@Test
	public void onlyRepositoryModuleIsLoaded() {
		assertThat(referenceDataRepository).isNotNull();
	}

	@Test
	public void whenSpringContextIsBootstrapped_thenNoExceptions() {
	}

}
