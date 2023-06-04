package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PersonRepository<T extends Person, ID> extends JpaRepository<T, ID> {
}