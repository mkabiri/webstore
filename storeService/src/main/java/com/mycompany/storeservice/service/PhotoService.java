package com.mycompany.storeservice.service;

import com.mycompany.storeservice.domain.Photo;
import com.mycompany.storeservice.repository.PhotoRepository;
import com.mycompany.storeservice.service.dto.PhotoDTO;
import com.mycompany.storeservice.service.mapper.PhotoMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Photo}.
 */
@Service
@Transactional
public class PhotoService {

    private final Logger log = LoggerFactory.getLogger(PhotoService.class);

    private final PhotoRepository photoRepository;

    private final PhotoMapper photoMapper;

    public PhotoService(PhotoRepository photoRepository, PhotoMapper photoMapper) {
        this.photoRepository = photoRepository;
        this.photoMapper = photoMapper;
    }

    /**
     * Save a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO save(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Update a photo.
     *
     * @param photoDTO the entity to save.
     * @return the persisted entity.
     */
    public PhotoDTO update(PhotoDTO photoDTO) {
        log.debug("Request to save Photo : {}", photoDTO);
        Photo photo = photoMapper.toEntity(photoDTO);
        photo = photoRepository.save(photo);
        return photoMapper.toDto(photo);
    }

    /**
     * Partially update a photo.
     *
     * @param photoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PhotoDTO> partialUpdate(PhotoDTO photoDTO) {
        log.debug("Request to partially update Photo : {}", photoDTO);

        return photoRepository
            .findById(photoDTO.getId())
            .map(existingPhoto -> {
                photoMapper.partialUpdate(existingPhoto, photoDTO);

                return existingPhoto;
            })
            .map(photoRepository::save)
            .map(photoMapper::toDto);
    }

    /**
     * Get all the photos.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PhotoDTO> findAll() {
        log.debug("Request to get all Photos");
        return photoRepository
            .findAllWithEagerRelationships()
            .stream()
            .map(photoMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get all the photos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PhotoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return photoRepository.findAllWithEagerRelationships(pageable).map(photoMapper::toDto);
    }

    /**
     * Get one photo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PhotoDTO> findOne(Long id) {
        log.debug("Request to get Photo : {}", id);
        return photoRepository.findOneWithEagerRelationships(id).map(photoMapper::toDto);
    }

    /**
     * Delete the photo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Photo : {}", id);
        photoRepository.deleteById(id);
    }
}
