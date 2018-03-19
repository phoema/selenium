
package com.ipph.service;


import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ipph.domain.T_Compare;


public interface T_CompareRepository extends PagingAndSortingRepository<T_Compare,Long>{

	List<T_Compare> findByBktitleNotNullAndBktitleNot(String bktitle);

}
