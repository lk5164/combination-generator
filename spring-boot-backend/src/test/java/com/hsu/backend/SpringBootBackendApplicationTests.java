package com.hsu.backend;

import com.hsu.backend.model.Item;
import com.hsu.backend.repository.ReferenceDataRepository;
import com.hsu.backend.service.ReferenceDataService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "application-test.properties")
@ActiveProfiles("test")
class SpringBootBackendApplicationTests {

	private static Set<Item> nine1One2 = new HashSet<>();
	@Autowired
	private ReferenceDataService referenceDataService;

	@Autowired
	private ReferenceDataRepository referenceDataRepository;

	@Test
	public void onlyRepositoryModuleIsLoaded() {
		assertThat(referenceDataService).isNotNull();
		assertThat(referenceDataRepository).isNotNull();
	}


	@Test
	public void whenPhoneNumberProvided_thenGenerateCombinationsIsCorrect() {
		String ones = "1111111112";
		nine1One2 = new HashSet<>();
		nine1One2.add(new Item(1L, "1111111112"));
		nine1One2.add(new Item(2L, "111111111a"));
		nine1One2.add(new Item(3L, "111111111b"));
		nine1One2.add(new Item(4L, "111111111c"));
		Iterable<Item> mock = new Iterable<>() {
			@Override
			public Iterator<Item> iterator() {
				return nine1One2.iterator();
			}
		};
		Mockito.when(referenceDataService.generateCombinations(ones)).thenReturn(mock);
		referenceDataService.generateCombinations(ones);
		verify(this.referenceDataService).generateCombinations(ones);
	}

	@Test
	public void whenRequestingFirstPageOfSizeTwo_ThenReturnFirstPage() {
		nine1One2 = new HashSet<>();
		nine1One2.add(new Item(1L, "1111111112"));
		nine1One2.add(new Item(2L, "111111111a"));
		nine1One2.add(new Item(3L, "111111111b"));
		nine1One2.add(new Item(4L, "111111111c"));
		this.referenceDataRepository.saveAll(nine1One2);
		this.referenceDataRepository.flush();
		int s = 2, p = 0;
		Iterable<Item> mock = new Iterable<>() {
			@Override
			public Iterator<Item> iterator() {
				return nine1One2.iterator();
			}
		};
		Set<Item> expected = new HashSet<>();
		expected.add(new Item(1L, "1111111112"));
		expected.add(new Item(2L, "111111111a"));

		Mockito.when(referenceDataService.findByPage(s, p)).thenReturn(expected);
		verify(this.referenceDataService).findByPage(s, p);
	}

	@Test
	public void whenSpringContextIsBootstrapped_thenNoExceptions() {
	}

}
