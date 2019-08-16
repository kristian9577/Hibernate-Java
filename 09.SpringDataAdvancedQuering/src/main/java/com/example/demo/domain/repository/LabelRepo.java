package com.example.demo.domain.repository;

import com.example.demo.domain.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepo extends JpaRepository<Label,Long> {

    List<Label>findAllBySubtitle(String subtitle);

}
