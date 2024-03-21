package com.william.curso.springboot.jpa.springbootjpa.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@Embeddable
public class Audit {

    @Column(name="create_at")
    public LocalDateTime createAt;

    @Column(name="updated_at")
    public LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        System.out.println("Evento del ciclo de vida de entity pre persist");
        this.createAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate(){
        System.out.println("Evento del ciclo de vida de entity pre update");
        this.updatedAt = LocalDateTime.now();
    }

    //de la misma manera existen los postPersist y postUpdate
    
    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
