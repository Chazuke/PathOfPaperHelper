package com.example.demo.Repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.demo.Character;

public interface CharacterRepository extends CrudRepository<Character, Long>{
		@Transactional
		@Query(value="SELECT * FROM characters WHERE owner=?1", nativeQuery=true)
		List<Character> findAllByOwner(String name);
		
		@Query(value="SELECT * FROM characters WHERE char_id = ?1", nativeQuery=true)
		Character findById(Long id);
		
		@Transactional
		@Modifying
		@Query(value="DELETE CASCADE FROM characters WHERE char_id = ?1", nativeQuery=true)
		void deleteById(Long id);
}
