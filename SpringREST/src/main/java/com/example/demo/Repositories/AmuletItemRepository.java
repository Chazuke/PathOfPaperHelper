package com.example.demo.Repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Items.AmuletItem;

public interface AmuletItemRepository extends CrudRepository<AmuletItem, Long> {
	@Query(value="SELECT * FROM amuletitems WHERE item_id = ?1", nativeQuery=true)
	AmuletItem findById(Long id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM amuletitems WHERE item_id = ?1", nativeQuery=true)
	void deleteById(Long id);
}