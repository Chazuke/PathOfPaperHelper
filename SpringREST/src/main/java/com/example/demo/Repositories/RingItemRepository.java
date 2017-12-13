package com.example.demo.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Items.RingItem;

public interface RingItemRepository extends CrudRepository<RingItem, Long> {
	@Query(value="SELECT * FROM ringitems WHERE item_id = ?1", nativeQuery=true)
	RingItem findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM ringitems WHERE item_id = ?1", nativeQuery=true)
	void deleteById(Long id);
}