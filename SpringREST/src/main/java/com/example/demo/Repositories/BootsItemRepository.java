package com.example.demo.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Items.BootsItem;

public interface BootsItemRepository extends CrudRepository<BootsItem, Long> {
	@Query(value="SELECT * FROM bootsitems WHERE item_id = ?1", nativeQuery=true)
	BootsItem findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM bootsitems WHERE item_id = ?1", nativeQuery=true)
	void deleteById(Long id);
}