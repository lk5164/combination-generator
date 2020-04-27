package com.hsu.backend.service;

import com.hsu.backend.model.Item;
import com.hsu.backend.repository.ReferenceDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class ReferenceDataService {

    private String[] mapping = new String[] {"0", "1", "2abc", "3def", "4ghi", "5jkl", "6mno", "7pqrs", "8tuv", "9wxyz"};
    private List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0)
            return new ArrayList<>();
        List<String> res = new LinkedList<String>();
        combination("", digits, 0, res);
        return res;
    }

    private void combination(String prefix, String digits, int offset, List<String> res) {
        if (offset >= digits.length()) {
            res.add(prefix);
            return;
        }
        int x = Character.getNumericValue(digits.charAt(offset));
        for (char c: this.mapping[x].toCharArray()) {
            combination(prefix + c, digits, offset + 1, res);
        }
    }
    @Autowired
    private ReferenceDataRepository referenceDataRepository;

    public Iterable<Item> findAllItems() {
        return this.referenceDataRepository.findAll();
    }

    public Iterable<Item> findByPage(Integer size, Integer page) {
        Pageable firstPageWithTwoElements = PageRequest.of(page, size);
        return this.referenceDataRepository.findAll(firstPageWithTwoElements);
    }

    public Iterable<Item> generateCombinations(String phoneNumber) {
        this.referenceDataRepository.deleteAll();
        this.referenceDataRepository.flush();
        List<Item> items = new ArrayList<>();
//        System.out.println("before inserting..." + this.referenceDataRepository.findAll().size());
        List<String> combinations = this.letterCombinations(phoneNumber);
        Long i = 0L;
        for (String text: combinations)
            items.add(new Item(++i, text));
        System.out.println("combinations size is: " + combinations.size() + " and current max index is: " + i);

//        System.out.println("after generating" + combinations.size());
        return this.referenceDataRepository.saveAll(items);
    }

    public void deleteAll() {
        this.referenceDataRepository.deleteAll();
        this.referenceDataRepository.flush();
    }
}
