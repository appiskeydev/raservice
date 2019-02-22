package com.appiskey.raservice.service;

import com.appiskey.raservice.model.Skill;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by suraksha-pnc on 2/7/19.
 */
//@NoRepositoryBean
public interface BaseService<T> {
    public T insert(T item);
    public List<T> findAll();
    public List<T> findAllByDeleted();
    public T findById(UUID id);
    public T delete(UUID id);
    public T update(T item);
}
