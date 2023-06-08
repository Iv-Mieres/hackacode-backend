package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Normal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface INormalRepository extends JpaRepository<Normal, UUID> {

}