package com.avanade.rpg.service;

import com.avanade.rpg.exception.InvalidInputException;
import com.avanade.rpg.exception.ResourceNotFoundException;
import com.avanade.rpg.model.EntityBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public class ServiceBase<TEntity extends EntityBase,  TRepository extends JpaRepository<TEntity, Long >> {

    @Autowired
    protected TRepository repository;

    public  TEntity create(TEntity entity ) {
        entity.setCreatedAt( LocalDateTime.now( ) );
        entity.setIsDeleted( false );
        return this.repository.save( entity );
    }

    public List< TEntity > findAll( ) {
        return repository.findAll();
    }

    public TEntity findById( Long id ) {
        return repository.findById( id )
                .orElseThrow( ( ) -> new ResourceNotFoundException(
                         "Entity not found with ID: " + id ) );
    }

    public void delete( Long id ) {
        var entity = this.findById(id);

        entity.setIsDeleted(true);
        entity.setDeleteAt(LocalDateTime.now());

        update(entity);
    }

    public TEntity update( TEntity entity ) {
        if ( entity.getId( ) == null ) {
            throw new InvalidInputException( "There is no ID" );
        }
        entity.setUpdateAt(LocalDateTime.now());

        return repository.save( entity );
    }

}
