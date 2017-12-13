package com.example.demo.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Items.ChestItem;

public interface ChestItemRepository extends CrudRepository<ChestItem, Long> {
	@Query(value="SELECT * FROM chestitems WHERE item_id = ?1", nativeQuery=true)
	ChestItem findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM chestitems WHERE item_id = ?1", nativeQuery=true)
	void deleteById(Long id);
}
