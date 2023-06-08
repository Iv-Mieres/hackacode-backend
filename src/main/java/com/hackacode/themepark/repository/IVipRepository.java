package com.hackacode.themepark.repository;

import com.hackacode.themepark.model.Vip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IVipRepository extends JpaRepository<Vip, UUID> {
}