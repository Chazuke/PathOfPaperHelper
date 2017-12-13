package com.example.demo.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Items.HeadItem;

public interface HeadItemRepository extends CrudRepository<HeadItem, Long> {
	@Query(value="SELECT * FROM headitems WHERE item_id = ?1", nativeQuery=true)
	HeadItem findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM headitems WHERE item_id = ?1", nativeQuery=true)
	void deleteById(Long id);
}
