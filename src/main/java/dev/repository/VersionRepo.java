package dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.entites.utiles.Version;

public interface VersionRepo extends JpaRepository<Version, Integer> {
}
